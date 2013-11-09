package controllers

import play.api.Play.current
import play.api._
import play.api.mvc._
import play.api.db._
import anorm._

object Application extends Controller {
  
  def index = Action {
    DB.withConnection { implicit conn =>
      val row = SQL("SELECT COUNT(*) AS c FROM Users").apply().head

      Ok(row[Long]("c").toString)
    }
  }
  
}