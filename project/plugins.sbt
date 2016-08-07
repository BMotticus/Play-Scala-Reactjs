// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.5.4")

// web plugins

//> maybe use? addSbtPlugin("org.webjars" % "materializecss" % "0.97.5")

//Less.js plugin 
addSbtPlugin("com.typesafe.sbt" % "sbt-less" % "1.1.0")

//Allows JSHint to be used from within sbt.
//js-engine enables high performance linting given parallelism and native JS engine execution.
addSbtPlugin("com.typesafe.sbt" % "sbt-jshint" % "1.0.3")

//An SBT plugin to perform RequireJs optimization.
addSbtPlugin("com.typesafe.sbt" % "sbt-rjs" % "1.0.7")

//plugin for adding checksum files for web assets.
//plugin for adding checksum files for web assets.
addSbtPlugin("com.typesafe.sbt" % "sbt-digest" % "1.1.0")

//SBT plugin for running mocha JavaScript unit tests on node
addSbtPlugin("com.typesafe.sbt" % "sbt-mocha" % "1.1.0")

//An sbt plugin that enables you to use Sass in your sbt-web project.
addSbtPlugin("org.irundaia.sbt" % "sbt-sassify" % "1.4.2")

//ReactJS Plugin
addSbtPlugin("com.github.ddispaltro" % "sbt-reactjs" % "0.6.8")