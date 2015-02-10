package controllers

import play.api._
import play.api.mvc._
import src.Search

object Application extends Controller {

  private[this] val sea = new Search

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def default = Action {
    Ok(views.html.default("text"))
  }

  def search = Action { request =>
    val name1 = request.getQueryString("person1").getOrElse("")
    val names1 = sea.search(name1).map(_.name)
    val name2 = request.getQueryString("person2").getOrElse("")
    val names2 = sea.search(name2).map(_.name)
    Ok(views.html.result(names1(0), names2(0)))
  }
}