package com.pidanic.meet.parser

import java.io.{FileWriter, BufferedWriter, File}


object Parser extends App {
  val parser = PersonParser.fromNqFile("data" + File.separator + "persondata_en.sample.nq")
  val persons = parser.parsePersons()
  val separator = ";"
  val personLines = persons.map(_.toCsvString(separator))
  val header = List("resource", "name", "surname", "givenName", "description", "birthDate", "deathDate")
    .mkString(separator)
  val lines = header :: personLines.toList

  writeToCsv("data" + File.separator + "output.csv", lines)

  def writeToCsv(path: String, lines: List[String]): Unit = {
    println("starting to write parsed persons to CSV")
    val writer = new BufferedWriter(new FileWriter(path))
    lines.foreach {
      line => {
        writer.write(line)
        writer.newLine()
      }
    }
    writer.flush()
    println("end of writing to CSV file")
  }
}
