name := "LagomService1"

version := "0.1"

ThisBuild / scalaVersion := "2.13.0"


val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.3" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.1.1" % Test

lazy val `LagomService1` = (project in file("."))
  .aggregate(`userService1-api`, `userService1-impl` )

lazy val `userService1-api` = (project in file("userService1-api"))
  .settings(


    ThisBuild / lagomKafkaEnabled := false,
    ThisBuild / lagomCassandraEnabled := false,
    libraryDependencies ++= Seq(
      lagomScaladslApi,
      "com.wix" %% "accord-core" % "0.7.4"
    )
  )

lazy val `userService1-impl` = (project in file("userService1-impl"))
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
      scalaTest,
      "com.wix" %% "accord-core" % "0.7.4"
    )
  )
  .settings(lagomForkedTestSettings)
  .dependsOn(`userService1-api`)
