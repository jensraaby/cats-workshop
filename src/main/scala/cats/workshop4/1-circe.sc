import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

/*

  This is a consolidation exercise to check you've understood the general principles.

  Circe is a library based on type-classes. Primarily, Decoder[A] and Encoder[A].

  Similarly to cats, there are syntax imports and typeclass instance imports.
  Take a look above and try to work out what each of the imports is for

 */


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



val allTheJsonStrings = List(catJson, catJson2, catJson3)

// Exercise 1: extract a cat from "catJson"
// Exercise 2: extract cats from 'allTheJsonStrings'. we want a List[Result[Cat]]
// Exercise 3: combine the results from exercise 2. We want a Result[List[Cat]]

// Exercise 4: now we'll introduce some failures.
// How can we separate the good results from the bad so that we can log the bad ones and return the good ones?

val badJson =
  """
    notJson
  """.stripMargin

val notCatJson =
  """
     {
        "name": "TopCat",
        "attribute": "The best cat"
     }
  """

val jsonStringsPlusBadOnes = allTheJsonStrings ++ List(badJson, notCatJson)

