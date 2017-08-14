import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest"       % "3.0.1"
  lazy val catbird   = "io.catbird"    %% "catbird-finagle" % "0.16.0"
  lazy val finagle   = "com.twitter"   %% "finagle-core"    % "6.45.0"

  lazy val circe = Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-optics",
    "io.circe" %% "circe-parser"
  ).map(_ % "0.8.0")

  lazy val cats = Seq(
    "org.typelevel" %% "cats-core"
  ).map(_ % "1.0.0-MF")
}
