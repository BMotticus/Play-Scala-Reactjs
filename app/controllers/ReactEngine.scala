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

  def render (view: String, title: String)(props: (String,Json.JsValueWrapper)*): Html = {
    jsEngine.loadScript("public/javascripts/app.js")
    jsEngine.loadScript("public/javascripts/components.js")
    jsEngine.loadScript(s"public/javascripts/views/$view.js")



    val data = Json.obj(props:_*)
    val rendered = jsEngine.eval("React.renderToString(React.createElement(View, " + Json.stringify(data) + "));").asInstanceOf[String]

    views.html.react.main(title, view, rendered, props = data)
  }
}

object ReactEngineOps {

  /**
    * the script engine with poly-fill configurations and the required libraries loaded automatically 
    */
  lazy val  jsEngine = {
    val engine = new javax.script.ScriptEngineManager(null).getEngineByName("nashorn")

    val polyfill = engine.eval("""
    var global = this; 
    var window = global; 
    window.matchMedia = function (qs) {
      return {
        matches: qs !== '(max-width: 700px)', 
        addListener: function (mq) {}
      }
    }; 
    var console = {};
    console.debug = print;
    console.warn = print;
    console.log = print; 
                               """)

    List(
      "public/lib/lodash/lodash.min.js",
      "public/lib/react/react.js",
      "public/javascripts/app.js",
      "public/javascripts/components.js"
    ).foreach (engine.loadScript)

    engine
  }

  /**
    * Pimp for loading files  
    * @param engine
    */
  implicit class ScriptEngineOps (engine: javax.script.ScriptEngine) {
    def loadScript (script: String) = {      // Replace ` Play.application ` with ` Environment.simple() `
      val code = scala.io.Source.fromInputStream(play.api.Play.application.resourceAsStream(script).get).mkString

      val _ = engine.put("code", code)
      val x = engine.eval(s"load({name: '${script.replace("\\/", "")}', script: code});")
    }
  }

}
