package controllers

import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json._
import play.twirl.api._
import play.api.mvc._
import play.api.Play.current

/**
  * Compiles React on the server.
  */
trait ReactEngine {self: Controller =>
  import ReactEngineOps._

  def render (view: String, title: String)(props: (String,Json.JsValueWrapper)*)(implicit r: RequestHeader): Html = {
    scriptEngine.load("public/javascripts/components/app.js")
    scriptEngine.load("public/javascripts/components/components.js")
    scriptEngine.load(s"public/javascripts/pages/$view.js")



    val data = Json.obj(props:_*)
    val rendered = scriptEngine.eval("React.renderToString(React.createElement(View, " + Json.stringify(data) + "));").asInstanceOf[String]

    views.html.react.main(title, view, rendered, props = data)
  }
}

object ReactEngineOps {

  /**
    * the script engine with poly-fill configurations and the required libraries loaded automatically
    */
  lazy val  scriptEngine = {
    val engine = new javax.script.ScriptEngineManager(null).getEngineByName("nashorn")

    //required polyfill
    val _ = engine.eval("""
      var global = this;
      var window = global;
      var console = {error: print, log: print, warn: print};
      """)

    List(
      "public/lib/lodash/lodash.min.js",
      "public/lib/react/react.js",
      "public/javascripts/components/app.js",
      "public/javascripts/components/components.js"
    ).foreach (engine.load)

    engine
  }

  /**
    * Using the `Pimp my library` pattern for loading scripts
    * @param engine
    */
  implicit class ScriptEngineOps (engine: javax.script.ScriptEngine) {

    def load (script: String) = {
      val code = scala.io.Source.fromInputStream(play.api.Play.application.resourceAsStream(script).get).mkString

      val _ = engine.put("code", code)
      val x = engine.eval(s"load({name: '${script.replace("\\/", "")}', script: code});")
    }
  }

}
