package com.github.simonthecat

import akka.actor.{Actor, Props}
import com.github.simonthecat.api.UserApi
import spray.routing.HttpServiceActor

class RouteActor extends HttpServiceActor {

  implicit val system = context.system

  override def receive: Actor.Receive = runRoute(new UserApi().routes)

}

object RouteActor {
  val props: Props = Props(classOf[RouteActor])
}