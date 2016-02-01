package com.pidanic.meet.parser

import java.io.File

import com.pidanic.meet.parser.PersonParser.NqParser
import org.scalatest.{FlatSpec, Matchers}

class PersonParserTest extends FlatSpec with Matchers {

  private val testFile = new File(getClass.getResource("/person_data_en.testsample.nq").toURI)

  "NqParser" should "throw an exception if other file type is given" in {
    a [IllegalArgumentException] should be thrownBy new NqParser("dummy.txt")
  }

  it should "return the same number of lines as Relevant properties count" in {
    val personParser = new NqParser(testFile)
    personParser.filterRelevantLines().toList.length should be(PersonParser.relevantProperties.length)
  }

  it should "ignore irrelevant characters" in {
    val personParser = new NqParser(testFile)
    val ignored = personParser.ignoreEverythingAfterLastQuote(
      """http://xmlns.com/foaf/0.1/name "Abraham Lincoln"@en <http://en.wikipedia.org/wiki/""")
    ignored should be("""http://xmlns.com/foaf/0.1/name "Abraham Lincoln"""")
  }

  it should "remove quotes" in {
    val personParser = new NqParser(testFile)
    personParser.removeQuotes("""asd"a"sda""") should be ("asdasda")
  }

  it should "parseKey" in {
    val personParser = new NqParser(testFile)
    personParser.parseKey("""http://xmlns.com/foaf/0.1/name""") should be ("name")
  }

  it should "parseValue" in {
    val personParser = new NqParser(testFile)
    personParser.parseValue(""""Abraham Lincoln"""") should be ("Abraham Lincoln")
  }

  it should "parse properties" in {
    val personParser = new NqParser(testFile)
    personParser.parseProperties(
      """http://dbpedia.org/resource/Abraham_Lincoln
        | http://xmlns.com/foaf/0.1/name "Abraham Lincoln"@en """.stripMargin) should be(
      ("http://dbpedia.org/resource/Abraham_Lincoln", ("name", "Abraham Lincoln")))
  }

  it should "parse raw data to Person object" in {
    val personParser = new NqParser(testFile)
    personParser.toPerson(
      ("http://dbpedia.org/resource/Abraham_Lincoln",
        Stream(("http://dbpedia.org/resource/Abraham_Lincoln", ("name", "Abraham Lincoln"))))) should be(
      Person("http://dbpedia.org/resource/Abraham_Lincoln", "Abraham Lincoln", "", "", "", "", ""))
  }

  it should "parse all person data" in {
    val personParser = new NqParser(testFile)
    val parsedPerson = Person("http://dbpedia.org/resource/Abraham_Lincoln",
      "Abraham Lincoln", "Lincoln", "Abraham", "16th President of the United States", "1809-02-12", "1865-04-15")
    personParser.parsePersons().toList should be (List(parsedPerson))
  }
}
