package com.pidanic.meet

import java.io.File

import com.pidanic.meet.util.FileUtil

import scala.io.Source

trait PersonParser {
  def writeToCsv(fileName: String): Unit
  def writeToCsv(fileName: String, headerColumns: List[String]): Unit
}

object PersonParser {

  private class NqParser(private val file: File) extends PersonParser {

    require(FileUtil.getExtension(file) == "nq")

    private val nqfile: File = file
    private lazy val lines: Iterator[String] = Source.fromFile(nqfile).getLines.drop(1)


    def this(fileName: String) {
      this(new File(fileName))
    }

    override def writeToCsv(fileName: String): Unit = {

    }

    override def writeToCsv(fileName: String, headerColumns: List[String]): Unit = {

    }
  }

  def fromNqFile(filename: String): PersonParser = {
    new NqParser(filename)
  }

  def fromNqFile(file: File): PersonParser = {
    new NqParser(file)
  }

  private[this] class PersonParserImpl(private val fileName: String) {



    def getPersonData: Map[String, Map[String, String]] = {
      val file = scala.io.Source.fromFile(fileName)
      val lines = file.getLines().drop(1)

      val linesWithPersonData = lines.filter(filterPersonData)
      val withoutBrackets = linesWithPersonData.map(_.replaceAll("[<>]", ""))
      val data = withoutBrackets.map(mapKeyValues).toStream.view.groupBy(_._1).mapValues(_.map(_._2).toMap)
      data
    }

    private def filterPersonData(line: String) = {
      val lower = line.toLowerCase
      // fix for: http://dbpedia.org/resource/Emiliano_Zapata http://dbpedia.org/ontology/deathPlace http://dbpedia.org/resource/Chinameca,_Morelos .
      lower.contains("\"") && (lower.contains("birthdate") || lower.contains("deathdate")
        || lower.contains("name") || lower.contains("surname") || lower.contains("givenname")
        || lower.contains("description")) // || lower.contains("birthplace")
    }

    private def mapKeyValues(line: String): (String, (String, String)) = {
      val tuple = line.replaceAll("[,;]", "\\|").splitAt(line.indexOf(' '))
      val resource = tuple._1
      val res2 = tuple._2.trim
      val tuple2 = res2.trim.splitAt(res2.indexOf(' '))
      val keyValue1 = tuple2._1.substring(tuple2._1.lastIndexOf('/') + 1, tuple2._1.length).intern()
      val keyValue2 = tuple2._2.substring(tuple2._2.indexOf('"') + 1, tuple2._2.lastIndexOf('"'))
      Tuple2(resource, Tuple2(keyValue1, keyValue2))
    }
  }

}