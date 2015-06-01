package com.github.simonthecat.api

import com.github.simonthecat.domain.User
import spray.httpx.SprayJsonSupport
import spray.httpx.marshalling.MetaMarshallers
import spray.httpx.unmarshalling.BasicUnmarshallers
import spray.json.DefaultJsonProtocol
import scala.concurrent.ExecutionContext.Implicits.global

trait Marshalling extends DefaultJsonProtocol with SprayJsonSupport with MetaMarshallers with BasicUnmarshallers {

  implicit val userFormat = jsonFormat2(User.apply)

}
