package controllers.api

import play.api.mvc._
import models.User

/**
 * Created by chriskellendonk on 11/10/2013.
 */
object Users extends Controller {
  def register() = Action { implicit request =>
    Ok(request.body.asJson)
  }

  def authenticate = Action {
    var u = new User("test3","test","test","test","test","et")

    u.save()

    u.username = "TEST_EDIT_3"
    u.save()

    Ok(u.username)
  }
}
