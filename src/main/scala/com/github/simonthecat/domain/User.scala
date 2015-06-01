package com.github.simonthecat.domain

import slick.driver.H2Driver.api._

case class User(id: Option[Int], username: String)

class Users(tag: Tag) extends Table[User](tag, "users") {

  def id = column[Int]("id", O.AutoInc, O.PrimaryKey)

  def username = column[String]("username")

  def * = (id.?, username) <>(User.tupled, User.unapply)

}

object Users {
  val query: TableQuery[Users] = TableQuery[Users]
}