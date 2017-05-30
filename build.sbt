import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.jensraaby",
      scalaVersion := "2.12.1",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "cats-workshop",
    libraryDependencies += scalaTest % Test,
    libraryDependencies ++= Seq(cats, finagle, catbird),
    initialCommands in console :=
      """
        |import cats._
        |import cats.implicits._
        |import cats.syntax.all._
        |""".stripMargin
  )
