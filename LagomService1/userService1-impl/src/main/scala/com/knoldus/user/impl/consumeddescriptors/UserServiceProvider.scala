

package com.knoldus.user.impl.consumeddescriptors

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.transport.Method.{GET, POST}
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import play.api.libs.json.{Format, Json}
trait UserServiceProvider extends Service{

  def addRemark() : ServiceCall[User,User]

  def sayHello() : ServiceCall[NotUsed,String]


  override def descriptor: Descriptor = {
    import Service._
    named("ExtraService")
      .withCalls(
        restCall(POST,"/api/user/incRollNo",addRemark _),
        restCall(GET,"/api/user/hello",sayHello _)

      )
  }
}

case class User(credScore :Int,name :String)

object User{
  implicit val format : Format[User] = Json.format[User]
}