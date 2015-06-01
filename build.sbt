name := "spray-slick3-template"

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= {
  import Dependencies._
  logging ++ akka ++ spray ++ slick ++ testing
}


import spray.revolver.RevolverPlugin._

Revolver.settings
