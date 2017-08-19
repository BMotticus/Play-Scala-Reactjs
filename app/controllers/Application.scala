package controllers

import javax.inject._
import play.api.mvc._
import scala.concurrent.ExecutionContext
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json._
/**
  * @param exec We need an `ExecutionContext` to execute our
  * asynchronous code.
  */
@Singleton
class Application @Inject() (implicit exec: ExecutionContext) extends Controller with ReactEngine {
  //val stringF = implicitly[Format[String]]

  def index = Action {implicit r =>
    Ok {
      render("home", "Home Page")(
        "message" -> "Hello from the Server"
      )
    }
  }
}
