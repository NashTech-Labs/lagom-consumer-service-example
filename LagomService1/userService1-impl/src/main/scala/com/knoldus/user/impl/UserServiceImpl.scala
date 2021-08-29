package com.knoldus.user.impl

import akka.NotUsed
import com.knoldus.user.api.{User, UserService}
import com.knoldus.user.impl.consumeddescriptors.{UserServiceProvider, User => NewUser}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import scala.concurrent.{ExecutionContext, Future}

class UserServiceImpl(userServiceProvider: UserServiceProvider)(implicit ec: ExecutionContext)
  extends UserService {


  override def sayHello: ServiceCall[NotUsed, String] = ServiceCall{
    _ =>
      Future.successful(
        "Hello from current micro-service(Consumer)"
      )
  }

  override def printUserRemark: ServiceCall[User, String] = {
    request =>
        val newUser = NewUser(request.credScore, request.name)
        val result = userServiceProvider.addRemark().invoke(newUser)
        result.map(user => user.name)
  }
}

