import java.io.{File, FileWriter}

/**
 * Created by paulp on 2/3/15.
 */
object FileWrite {

  private[this] val outFileName = new File("data" + File.separator + "sample_output.csv")

  def write(persons: List[Person]): Unit =  {
    val fileWriter = new FileWriter(outFileName)

    persons.foreach { person =>
      val line = person.resource + "," + person.name + "," + person.surname + "," + person.givenName + "," + person.description + "," + person.birthDate + "," + person.deathDate
      fileWriter.write(line + "\n")
    }

    fileWriter.flush()
    fileWriter.close()
  }

  def write(data: Map[String, Map[String, String]]): Unit = {
    val fileWriter = new FileWriter(outFileName)

    data.foreach {
      entry =>
        val outLine = createOutputLine(entry)
        fileWriter.write(outLine)
    }

    fileWriter.flush()
    fileWriter.close()
  }

  private def createOutputLine(personData: (String, Map[String, String])): String = {
    val resource = personData._1.trim
    val personProperties = personData._2
    val name = personProperties.getOrElse("name", "").trim
    val givenName = personProperties.getOrElse("givenName", "").trim
    val surname = personProperties.getOrElse("surname", "").trim
    val description = personProperties.getOrElse("description", "").trim
    val birthDate = personProperties.getOrElse("birthDate", "").trim
    val deathDate = personProperties.getOrElse("deathDate", "").trim

    val outLine = resource + ", " + name + "," + surname + "," + givenName + "," + description + "," + birthDate + "," + deathDate + "\n"
    outLine
  }
}
