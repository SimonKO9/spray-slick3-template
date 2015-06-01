import sbt._

object Versions {
  val akka = "2.3.9"
  val spray = "1.3.3"
}

object Dependencies {

  val spray = Seq(
    "io.spray" %% "spray-can" % Versions.spray,
    "io.spray" %% "spray-routing" % Versions.spray,
    "io.spray" %% "spray-json" % "1.3.2",
    "io.spray" %% "spray-testkit" % Versions.spray % "test"
  )

  val akka = Seq(
    "com.typesafe.akka" %% "akka-actor" % Versions.akka,
    "com.typesafe.akka" %% "akka-testkit" % Versions.akka % "test"
  )

  val slick = Seq(
    "com.typesafe.slick" %% "slick" % "3.0.0",
    "org.slf4j" % "slf4j-nop" % "1.6.4",
    "com.h2database" % "h2" % "1.3.175"
  )

  val logging = Seq(
    "ch.qos.logback" % "logback-classic" % "1.1.2"
  )
  val testing = Seq(
    "org.scalatest" %% "scalatest" % "2.2.4"
  )

}