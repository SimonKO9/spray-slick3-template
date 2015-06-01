package com.github.simonthecat.service

import akka.actor.{Actor, Props}
import com.github.simonthecat.Db
import Db.db
import com.github.simonthecat.domain.{User, Users}
import slick.dbio.Effect.Write
import slick.driver.H2Driver.api._
import akka.pattern._
import slick.profile.FixedSqlAction
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


case class CreateUser(user: User)

case class GetUsers()

case class GetUser(id: Int)

case class DeleteUser(id: Int)

trait UserService {

  def findAll(): Future[Seq[User]] = {
    db.run(Users.query.result)
  }

  def createUser(user: User): Future[User] = {
    val insert = (Users.query returning Users.query.map(_.id)) += user
    db.run(insert).map(newId => user.copy(id = Some(newId)))
  }

  def find(id: Int): Future[Option[User]] = {
    db.run(Users.query.filter(_.id === id).result.headOption)
  }

  def deleteUser(id: Int): Future[Option[User]] = {
    find(id).flatMap {
      case Some(user) =>
        val delete = Users.query.filter(_.id === id).delete
        db.run(delete).map(_ => Some(user))
      case None =>
        Future.failed(new Exception("User not found."))
    }
  }

}

class UserActor extends Actor with UserService {

  override def receive: Receive = {
    case CreateUser(user) =>
      createUser(user) pipeTo sender
    case GetUsers() =>
      findAll() pipeTo sender
    case GetUser(id) =>
      find(id) pipeTo sender
    case DeleteUser(id) =>
      deleteUser(id) pipeTo sender
  }

}

object UserActor {
  def props = Props(classOf[UserActor])
}