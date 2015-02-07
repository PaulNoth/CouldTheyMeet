/**
 * Created by paulp on 2/7/15.
 */
trait PersonParser {

  def getPersons: List[Person]

  def getPersonData: Map[String, Map[String, String]]
}

object PersonParser {

  private[this] class PersonParserImpl(private val fileName: String) extends PersonParser {

    override def getPersons: List[Person] = {
      // TODO
      List.empty
    }

    override def getPersonData: Map[String, Map[String, String]] = {
      val file = io.Source.fromFile(fileName)
      val lines = file.getLines().drop(1)

      val linesWithPersonData = lines.filter(filterPersonData)
      val withoutBrackets = linesWithPersonData.map(_.replaceAll("[<>]", ""))
      val data = withoutBrackets.map(mapKeyValues).toStream.groupBy(_._1).mapValues(_.map(_._2).toMap)
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
      val tuple = line.splitAt(line.indexOf(' '))
      val resource = tuple._1
      val res2 = tuple._2.trim
      val tuple2 = res2.trim.splitAt(res2.indexOf(' '))
      val keyValue1 = tuple2._1.substring(tuple2._1.lastIndexOf('/') + 1, tuple2._1.length)
      val keyValue2 = tuple2._2.substring(tuple2._2.indexOf('"') + 1, tuple2._2.lastIndexOf('"'))
      Tuple2(resource, Tuple2(keyValue1, keyValue2))
    }
  }

  def fromFile(fileName: String): PersonParser = {
    new PersonParserImpl(fileName)
  }
}
