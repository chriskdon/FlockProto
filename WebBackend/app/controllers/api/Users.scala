package controllers.api

import play.api.mvc._
import models.User

/**
 * Created by chriskellendonk on 11/10/2013.
 */
object Users extends Controller {
  def register(firstname: String, lastname: String,) = Action {
    Ok("register")
  }

  def authenticate = Action {
    Ok(User.all()(0).name)
  }
}
