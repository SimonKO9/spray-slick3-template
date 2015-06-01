package com.github.simonthecat

import akka.actor.Actor
import com.github.simonthecat.service.UserActor

case object Start

case object Shutdown

class ApplicationActor extends Actor {

  override def receive: Receive = {
    case Start =>
      context.actorOf(UserActor.props, "user-service")
      sender ! true
    case Shutdown =>
      context.children.foreach(context.stop)
  }

}