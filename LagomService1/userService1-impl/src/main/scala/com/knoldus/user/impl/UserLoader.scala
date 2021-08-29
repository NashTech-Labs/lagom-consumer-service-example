package com.knoldus.user.impl

import akka.actor.ActorSystem
import com.knoldus.user.api.UserService
import com.knoldus.user.impl.consumeddescriptors.UserServiceProvider
import com.lightbend.lagom.scaladsl.akka.discovery.AkkaDiscoveryComponents
import com.lightbend.lagom.scaladsl.api.Descriptor
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.persistence.slick.SlickPersistenceComponents
import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}
import com.lightbend.lagom.scaladsl.server._
import com.softwaremill.macwire._
import play.api.db.HikariCPComponents
import play.api.libs.ws.ahc.AhcWSComponents

import scala.concurrent.ExecutionContext



class UserLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication = {
    new UserServiceApplication(context) with AkkaDiscoveryComponents
  }
  override def loadDevMode(context: LagomApplicationContext): LagomApplication = {
    new UserServiceApplication(context) with LagomDevModeComponents {
    }
  }
  override def describeService: Option[Descriptor] = Some(readDescriptor[UserService])

}


trait UserComponents
  extends LagomServerComponents
    with SlickPersistenceComponents
    with HikariCPComponents
    with AhcWSComponents {
  implicit def executionContext: ExecutionContext
  implicit def actorSystem: ActorSystem

  /** Register the JSON serializer registry */
  override lazy val jsonSerializerRegistry: UserSerializerRegistry.type = UserSerializerRegistry
}

abstract class UserServiceApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
  with UserComponents {
  lazy val userService = serviceClient.implement[UserServiceProvider]

  override lazy val lagomServer: LagomServer = serverFor[UserService](wire[UserServiceImpl])
}
object UserSerializerRegistry extends  JsonSerializerRegistry{
  override  def serializers:Seq[JsonSerializer[_]] = Seq.empty
}
