package models

import play.api.Play.current
import play.api.db._
import anorm._
import scala.collection.mutable.ListBuffer

/**
 * Create a new user.
 *
 * @param id                ID of the user in the database. -1 If not in database.
 * @param username          Username in database.
 * @param firstname         Firstname in database.
 * @param lastname          Lastname in database.
 * @param saltedPassword    Salted Password in database.
 * @param salt              Salt for the password.
 * @param email             The email of the user.
 */
class User(val id:Int, val username:String, val firstname:String, val lastname:String,
           val saltedPassword:String, val salt:String, val email:String) {

  /**
   * Create a new user that isn't in the database.
   *
   * @param username        Desired username.
   * @param firstname       Desired firstname.
   * @param lastname        Desired lastname.
   * @param saltedPassword  Desired salted password.
   * @param salt            Salt for the salted password.
   * @param email           Desired email.
   * @return                A new user object.
   */
  def this(username:String, firstname:String, lastname:String,
           saltedPassword:String, salt:String, email:String) = this(-1, username, firstname, lastname,
                                                                    saltedPassword, salt, email)

  /**
   * @return A human readable version of the User's name.
   */
  def name():String = firstname + " " + lastname

  /**
   * @return True if the User is saved in the database.
   */
  def isInDatabase():Boolean = id > 0

  /**
   * Save the current user to the database.
   * @return The user that was saved with updated fields (e.g. ID).
   */
  def save():User = {
    return null;
  }
}

object User {
  /**
   * Convert a database SqlRow to a User object.
   *
   * @param row The row to be converted.
   * @return The user object generated from that row.
   */
  protected def rowToUser(row:SqlRow): User = {
    return new User(row[Int]("ID"), row[String]("Username"), row[String]("Firstname"), row[String]("Lastname"),
                    row[String]("SaltedPassword"), row[String]("Salt"), row[String]("Email"))
  }

  /**
   * Query the databse and return the user objects.
   * @param filter The where clause to execute to filter results
   * @return
   */
  protected def queryDatabaseForUser(filter: String): List[User] = {
    val query =
      """
        SELECT ID, Username, Firstname, Lastname, SaltedPassword, Salt, Email
        FROM Users
      """

    var userList = new ListBuffer[User] // List of returned users

    // Query database for users
    DB.withConnection { implicit conn =>
      SQL(query + " " + (if(filter != null) filter else ""))().foreach { row =>
        userList += rowToUser(row)
      }
    }

    return userList.toList
  }

  /**
   * @return A list of all Users in the database
   */
  def all(): List[User] = {
    return queryDatabaseForUser(null)
  }
}
