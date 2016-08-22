import sbt._
import sbt.Keys._
import PlayKeys._

name := """bm-play-scala-reactjs"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala,SbtWeb)

scalaVersion := "2.11.7"

offline := true

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.typelevel"              %% "cats" % "0.6.1",
  "com.typesafe.play"          %% "play-slick" % "2.0.0",
  "com.typesafe.play"          %% "play-slick-evolutions" % "2.0.0",
  "org.scalatestplus.play"     %% "scalatestplus-play" % "1.5.1" % Test,
  "org.webjars"                %% "webjars-play" % "2.4.0-1",
  "org.webjars"                 % "react" % "0.13.3",
  "org.webjars"                 % "lodash" % "2.4.1"
)

resolvers ++= Seq(
  "typesafe" at "http://repo.typesafe.com/typesafe/releases/",
  Resolver.url("typesafe2", url("http://dl.bintray.com/typesafe/ivy-releases"))(Resolver.ivyStylePatterns),
  "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
)

ReactJsKeys.harmony := true
ReactJsKeys.es6module := false