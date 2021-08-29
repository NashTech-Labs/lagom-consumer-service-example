name := "LagomService2"

version := "0.1"

ThisBuild / scalaVersion := "2.13.0"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.3" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.1.1" % Test

lazy val `LagomPractice` = (project in file("."))
  .aggregate(`userService2-api`,`userService2-impl` )

lazy val `userService2-api` = (project in file("userService2-api"))
  .settings(

    ThisBuild / lagomKafkaEnabled := false,
    ThisBuild / lagomCassandraEnabled := false,
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `userService2-impl` = (project in file("userService2-impl"))
  .enablePlugins(LagomScala)
  .settings(

    ThisBuild / lagomKafkaEnabled := false,
    ThisBuild / lagomCassandraEnabled := false,
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceJdbc,
      lagomScaladslKafkaBroker,
      lagomScaladslAkkaDiscovery,
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings)
  .dependsOn(`userService2-api`)
