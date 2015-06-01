package com.github.simonthecat.api

import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import akka.pattern._
import akka.util.Timeout
import com.github.simonthecat.domain.User
import com.github.simonthecat.service._
import spray.http.MediaTypes
import spray.routing.Directives._

import scala.concurrent.ExecutionContext.Implicits.global

class UserApi(implicit val actorSystem: ActorSystem) extends Marshalling {

  implicit val userService = actorSystem.actorSelection("/user/application/user-service")

  implicit val timeout = Timeout(5, TimeUnit.SECONDS)

  val routes = path("users") {
    get { ctx =>
      ctx.complete {
        (userService ? GetUsers()).mapTo[Seq[User]]
      }
    } ~
      post {
        entity(as[User]) { user: User =>
          complete {
            (userService ? CreateUser(user)).mapTo[User]
          }
        }
      }
  } ~ path("users" / IntNumber) { id =>
    get {
      complete {
        (userService ? GetUser(id)).mapTo[Option[User]]
      }
    } ~ delete {
      complete {
        (userService ? DeleteUser(id)).mapTo[Option[User]]
      }
    }
  }


}
