package models

import play.api.Play.current
import play.api.db._
import anorm._
import scala.collection.mutable.ListBuffer

/**
 * Created by chriskellendonk on 11/10/2013.
 */
class User {
  // Columns
  var id:Int = 0
  var username:String = null
  var firstname:String = null
  var lastname:String = null
  var saltedPassword:String = null
  var salt:String = null
  var email:String = null

  def name = firstname + " " + lastname
}

object User {
  protected def rowToUser(row:SqlRow): User = {
    val user = new User()

    user.id = row[Int]("ID")
    user.firstname = row[String]("Firstname")
    user.lastname = row[String]("Lastname")
    user.saltedPassword = row[String]("SaltedPassword")
    user.salt = row[String]("Salt")
    user.email = row[String]("Email")

    return user
  }

  def all(): List[User] = {
    val query =
      """
        SELECT ID, Username, Firstname, Lastname, SaltedPassword, Salt, Email
        FROM Users
      """

    var userList = new ListBuffer[User] // List of returned users

    DB.withConnection { implicit conn =>
      SQL(query)().foreach { row =>
        userList += rowToUser(row)
      }
    }

    return userList.toList
  }
}
