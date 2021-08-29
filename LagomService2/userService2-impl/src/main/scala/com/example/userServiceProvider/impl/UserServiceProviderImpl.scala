package com.example.userServiceProvider.impl

import akka.NotUsed
import com.example.userServiceProvider.api.{User, UserServiceProvider}
import com.lightbend.lagom.scaladsl.api.ServiceCall

import scala.concurrent.{ExecutionContext, Future}

class UserServiceProviderImpl()(implicit ec : ExecutionContext) extends UserServiceProvider{
  override def addRemark(): ServiceCall[User, User] = {
    user =>
      Future{
        if(user.credScore < 600 && user.credScore >= 0) User(user.credScore, user.name+" comes in 'Immediate Action Required Grade' ")
        else if(user.credScore >=600 && user.credScore < 650) User(user.credScore, user.name+" comes in 'Doubtful Grade' ")
        else if(user.credScore >= 650 && user.credScore < 700) User(user.credScore, user.name+" comes in 'Fair Grade' ")
        else if(user.credScore >= 700 && user.credScore < 750) User(user.credScore, user.name+" comes in 'Good Grade' ")
        else if(user.credScore >= 750 && user.credScore <= 900) User(user.credScore, user.name+" comes in 'Excellent Grade' ")
        else User(user.credScore, user.name+" your cred score is not according to the table")
      }
  }

  override def sayHello(): ServiceCall[NotUsed, String] = {
    _ =>
      Future.successful(
        "Hello from the current micro-service(Consumed)"
      )
  }
}
