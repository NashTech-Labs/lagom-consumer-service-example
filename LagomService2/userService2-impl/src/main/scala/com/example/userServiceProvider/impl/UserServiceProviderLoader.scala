package com.example.userServiceProvider.impl

import akka.actor.ActorSystem
import com.example.userServiceProvider.api.UserServiceProvider
import com.lightbend.lagom.scaladsl.akka.discovery.AkkaDiscoveryComponents
import com.lightbend.lagom.scaladsl.api.Descriptor
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.persistence.slick.SlickPersistenceComponents
import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}
import com.lightbend.lagom.scaladsl.server.{LagomApplication, LagomApplicationContext, LagomApplicationLoader, LagomServer, LagomServerComponents}
import com.softwaremill.macwire.wire
import play.api.db.HikariCPComponents
import play.api.libs.ws.ahc.AhcWSComponents
import scala.concurrent.ExecutionContext

class UserServiceProviderLoader extends LagomApplicationLoader{
  override def load(context: LagomApplicationContext): LagomApplication = {
    new UserServiceApplication(context) with AkkaDiscoveryComponents
  }
  override def loadDevMode(context: LagomApplicationContext): LagomApplication = {
    new UserServiceApplication(context) with LagomDevModeComponents {
    }
  }
  override def describeService: Option[Descriptor] = Some(readDescriptor[UserServiceProvider])

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
  override lazy val lagomServer: LagomServer =
    serverFor[UserServiceProvider](wire[UserServiceProviderImpl])
}
object UserSerializerRegistry extends  JsonSerializerRegistry{
  override  def serializers:Seq[JsonSerializer[_]] = Seq.empty
}
