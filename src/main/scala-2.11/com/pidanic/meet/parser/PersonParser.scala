package com.pidanic.meet.parser

import java.io.File

import com.pidanic.meet.util.FileUtil

import scala.io.Source

trait PersonParser {
  def parsePersons(): Iterator[Person]
}

object PersonParser {

  private[parser] val relevantProperties = List("name", "surname", "givenName", "description", "birthDate", "deathDate")

  private[parser] class NqParser(private val file: File) extends PersonParser {

    require(FileUtil.getExtension(file) == "nq")

    val nqfile: File = file
    lazy val lines: Iterator[String] = Source.fromFile(nqfile).getLines.drop(1)

    def this(fileName: String) {
      this(new File(fileName))
    }

    def filterRelevantLines(): Iterator[String] = {
      lines.filter(line => relevantProperties.exists(line.contains(_)))
    }

    override def parsePersons(): Iterator[Person] = {
      val relevantLines = filterRelevantLines()
      val withoutBrackets = relevantLines.map(_.replaceAll("[<>]", ""))
      val properties = withoutBrackets.map(parseProperties)
      val groups = properties.toStream
        .groupBy(_._1)
      groups.map(toPerson).toIterator
    }

    def parseProperties(line: String) = {
      val splitsByFirstSpace = line.splitAt(line.indexOf(' '))
      val key = splitsByFirstSpace._1.trim
      val part2 = splitsByFirstSpace._2.trim
      val propertyKeyValue = ignoreEverythingAfterLastQuote(part2).splitAt(part2.indexOf(' '))
      val propertyKey = parseKey(propertyKeyValue._1.trim)
      val propertyValue = removeQuotes(parseValue(propertyKeyValue._2.trim))
      Tuple2(key, Tuple2(propertyKey, propertyValue))
    }

    def toPerson(map: (String, Stream[(String, (String, String))])): Person = {
      val resource = map._1
      val propertyMap = Map[String, String](map._2.map(_._2):_*)
      val name = propertyMap.getOrElse("name", "")
      val surname = propertyMap.getOrElse("surname", "")
      val givenName = propertyMap.getOrElse("givenName", "")
      val description = propertyMap.getOrElse("description", "")
      val birthDate = propertyMap.getOrElse("birthDate", "")
      val deathDate = propertyMap.getOrElse("deathDate", "")
      Person(resource, name, surname, givenName, description, birthDate, deathDate)
    }

    def parseKey(text: String): String = {
      text.substring(text.lastIndexOf('/') + 1).trim
    }

    def parseValue(text: String): String = {
      removeQuotes(text.substring(text.indexOf('"') + 1, text.lastIndexOf('"'))).trim
    }

    def ignoreEverythingAfterLastQuote(text: String): String = {
      text.substring(0, text.lastIndexOf('"') + 1)
    }

    def removeQuotes(text: String): String = {
      text.replaceAll("[\"]", "")
    }
  }

  def fromNqFile(filename: String): PersonParser = {
    new NqParser(filename)
  }

  def fromNqFile(file: File): PersonParser = {
    new NqParser(file)
  }
}