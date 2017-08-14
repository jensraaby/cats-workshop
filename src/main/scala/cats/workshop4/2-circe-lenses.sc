import cats.syntax.either._
import io.circe._, io.circe.parser._

/*
  This is a quick exercise in using Circe's optics module.

  Under the hood this uses the Monocle library, which is pretty damn powerful.

 */

// here's some json with some nested objects
val json: Json = parse("""
{
  "order": {
    "customer": {
      "name": "Custy McCustomer",
      "contactDetails": {
        "address": "1 Fake Street, London, England",
        "phone": "0123-456-789"
      }
    },
    "items": [{
      "id": 123,
      "description": "banana",
      "quantity": 1
    }, {
      "id": 456,
      "description": "apple",
      "quantity": 2
    }],
    "total": 123.45
  }
}
""").getOrElse(Json.Null) // in production code, we'd want to map or flatMap on this Either rather than default


// Exercise 1: Get the phone number using the circe cursor:

json.hcursor





import io.circe.optics.JsonPath._
// Exercise 2: use the optics method to get the phone number
// note that the path through the json uses dynamic methods!

root.order.string.getOption(json) // this is just a starting point


// Exercise 3: use optics to get all the item descriptions

// Exercise 4: what's the problem with this method?