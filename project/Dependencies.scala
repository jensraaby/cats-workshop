import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest"       % "3.0.1"
  lazy val cats      = "org.typelevel" %% "cats"            % "0.9.0"
  lazy val catbird   = "io.catbird"    %% "catbird-finagle" % "0.14.0"
  lazy val finagle   = "com.twitter"   %% "finagle-core"    % "6.44.0"

  lazy val circe = Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-parser"
  ).map(_ % "0.8.0")
}
