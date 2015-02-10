package src

/**
 * Created by paulp on 1/31/15.
 */
object Main { //extends App {

  val parser = PersonParser.fromFile("data/sample_input_persondata_en.ttl")
 // val parser = PersonParser.fromFile("data/persondata_en.ttl")

  val personData = parser.getPersonData

  FileWrite.write(personData)

  val result = (new Search).search("Einstein")

  println(result)
}
