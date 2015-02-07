import java.io.{File, FileWriter}

import eu.pidanic.couldmeet.search.Person

/**
 * Created by paulp on 2/3/15.
 */
object FileWrite {

  private[this] val outFileName = new File("data" + File.separator + "sample_output.csv")

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
    val name = personProperties.getOrElse("name", "")
    val givenName = personProperties.getOrElse("givenName", "")
    val surname = personProperties.getOrElse("surname", "")
    val description = personProperties.getOrElse("description", "")
    val birthDate = personProperties.getOrElse("birthDate", "")
    val deathDate = personProperties.getOrElse("deathDate", "")

    val outLine = resource + "," + name + "," + surname + "," + givenName + "," + description + "," + birthDate + "," + deathDate + ",\n"
    outLine
  }
}
