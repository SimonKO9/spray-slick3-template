package com.github.simonthecat

import akka.actor._
import akka.io.IO
import akka.pattern._
import akka.util.Timeout
import com.typesafe.config.{Config, ConfigFactory}
import spray.can.Http

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import scala.util.{Failure, Success, Try}


class Application {

  implicit val timeout = Timeout(5.seconds)

  implicit val system = ActorSystem("auction-house")

  implicit val cfg = ConfigFactory.load()

  val app = system.actorOf(Props(classOf[ApplicationActor]), "application")
  Await.ready(app ? Start, 5.seconds)

  system.registerOnTermination {
    app ! Shutdown
  }

}

trait HttpInterface {

  def cfg: Config

  implicit def system: ActorSystem

  val routeActor = system.actorOf(RouteActor.props, "router")

  {
    implicit val timeout = Timeout(15.seconds)

    val setupHttpServer = IO(Http) ? Http.Bind(routeActor, cfg.getString("application.server.host"), cfg.getInt("application.server.port"))
    Try(Await.result(setupHttpServer, 15.seconds)) match {
      case Failure(ex) =>
        println(s"Failed to setup HTTP server: $ex")
        system.shutdown()
      case Success(m) =>
        println(s"HTTP is listening: $m")
    }
  }

}


object Boot extends App {

  val app = new Application with HttpInterface
  sys.addShutdownHook {
    app.system.shutdown()
  }

  Db.createSchema()
}
