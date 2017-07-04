import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

case class Cat(name: String, age: Int, occupation: Option[String])

val catJson =
  """{
    |  "name": "Garfield",
    |  "age": 35
    |}
  """.stripMargin

val catJson2 =
  """{ "name": "scratchy"}""".stripMargin

val catJson3 =
  """{ "name": "Jess", "age": 16, "occupation": "Postal cat"}"""

val allTheJsons = List(catJson, catJson2, catJson3)

// Exercise 1: extract a cat from "catJson"
// Exercise 2: extract cats from the list. we want a List[Result[Cat]]
// Exercise 3: combine the results from exercise 2. We want a Result[List[Cat]]