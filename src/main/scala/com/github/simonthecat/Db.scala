package com.github.simonthecat

import com.github.simonthecat.domain.Users
import slick.driver.H2Driver.api._

object Db {

  implicit val db: Database = Database.forConfig("application.database.h2mem")

  val usersTable = TableQuery[Users]

  def createSchema(): Unit = db.run(usersTable.schema.create)

}
