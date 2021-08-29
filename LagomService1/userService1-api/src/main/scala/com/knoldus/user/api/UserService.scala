package com.knoldus.user.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.transport.Method.{GET, POST}
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import com.wix.accord.Validator
import com.wix.accord.dsl.{Contextualizer, between, notEmpty, validator}
import play.api.libs.json.{Format, Json}

trait UserService extends Service{

  def sayHello : ServiceCall[NotUsed,String]

  def printUserRemark : ServiceCall[User, String]

  override def descriptor: Descriptor = {
    import Service._
    named("Service1")
      .withCalls(
        restCall(GET,"/api/hello", sayHello _),
        restCall(POST,"/api/user/addRemark",printUserRemark _)
      )
      .withAutoAcl(true)

  }
}
case class User(credScore :Int, name : String)

object User{

  implicit val format : Format[User] = Json.format[User]

}

