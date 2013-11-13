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
class User(var id:Long, var username:String, var firstname:String, var lastname:String,
           var saltedPassword:String, var salt:String, var email:String) {

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
    val queryUpdate =
      """
        UPDATE Users SET
          Username = {username},
          Firstname = {firstname},
          Lastname = {lastname},
          SaltedPassword = {saltedPassword},
          Salt = {salt},
          Email = {email}
        WHERE ID = {id};
    """.stripMargin

    val queryInsert =
      """
        INSERT INTO Users (Username, Firstname, Lastname, SaltedPassword, Salt, Email)
        VALUES ({username}, {firstname}, {lastname}, {saltedPassword}, {salt}, {email})
      """.stripMargin

    DB.withConnection { implicit conn =>
      if(!this.isInDatabase) {
        val id:Option[Long] = SQL(queryInsert).on('username -> username,
                                            'firstname -> firstname,
                                            'lastname -> lastname,
                                            'saltedPassword -> saltedPassword,
                                            'salt -> salt,
                                            'email -> email).executeInsert()

        if(id.isDefined) {
          this.id = id.get // Update object ID
        }
      } else {
          SQL(queryUpdate).on('username -> username,
                              'firstname -> firstname,
                              'lastname -> lastname,
                              'saltedPassword -> saltedPassword,
                              'salt -> salt,
                              'email -> email,
                              'id -> this.id).executeUpdate()
      }

    }

    return this;
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
    return new User(row[Long]("ID"), row[String]("Username"), row[String]("Firstname"), row[String]("Lastname"),
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
      """.stripMargin

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
