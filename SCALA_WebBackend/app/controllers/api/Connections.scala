package controllers.api

import play.api.mvc._

/**
 * Created by chriskellendonk on 11/10/2013.
 */
object Connections extends Controller {
  def list = Action {
    Ok("list")
  }

  def add = Action {
    Ok("add")
  }
}
