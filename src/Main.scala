import java.util.{Calendar, GregorianCalendar, Date}

/**
 * Created by paulp on 1/31/15.
 */
object Main extends App {
  val file = io.Source.fromFile("data/sample_input_persondata_en.ttl")
  val lines = file.getLines.toList.drop(1)
  val lines2 = lines.map(_.replaceAll("[<>]", "")).groupBy(_.split("\\s+").apply(0))
  lines2.foreach {
    line => println(line)
  }
  val lines3 = lines2.mapValues(_.filter(filterPersonData(_)).map(_.split("\\s+").toList.slice(1, 3)).flatten )

  lines3.foreach {
    line => println(line)
  }

  val lines4 = lines2.mapValues(_.filter(filterPersonData(_)).map(s => s.substring(0, s.lastIndexOf("\"") + 1).split("\\s+", 2).toList.drop(1) ).flatten )

  lines4.foreach {
    line => println(line)
  }

  val lines5 = lines4.mapValues(_.map(s => s.splitAt(s.indexOf(" ")) ).map(parseProperties(_)) ).mapValues(_.toMap)

  lines5.foreach {
    line => println(line)
  }

  file.close

  val persons = lines5.map(toPerson(_))
  persons.foreach {
    line => println(line)
  }



  def filterPersonData(s: String) =
  {
    val lower = s.toLowerCase
    lower.contains("birthdate") || lower.contains("deathdate") || lower.contains("name") || lower.contains("surname") || lower.contains("givenname") || lower.contains("description") // || lower.contains("birthplace")
  }

  /**
   * ignores first http resource from dbpedia
   * @param s
   * @return
   */
  def ignoreResource(s: String) =
  {
    s.dropWhile(!_.isWhitespace).trim
  }

  def parseProperties(a: (String, String))=
  {
    val el1 = a._1.substring(a._1.lastIndexOf("/") + 1, a._1.length)
    val el2 = a._2.replaceAll("\"", "")
    Tuple2(el1, el2)
  }

  def toPerson(a: (String, Map[String, String])) = {
    val elem1 = a._1
    val elem2 = a._2
    val name = elem2.getOrElse("name", "")
    val surname = elem2.getOrElse("surname", "")
    val givenName = elem2.getOrElse("givenName", "")
    val birthDate = elem2.getOrElse("birthDate", "0001-01-01")
    val deathDate = elem2.getOrElse("deathDate", "9999-12-31")
    val description = elem2.getOrElse("description", "")
    Person(elem1, name, surname, givenName, description, birthDate, deathDate)
  }

  private val DefaultDeathDate: Date =  new GregorianCalendar(9999, Calendar.JANUARY, 1).getTime

  private[this] val XmlDateYMDRegex = "\\-?\\d+\\-\\d{1,2}\\-\\d{1,2}"

  private[this] val XmlDateYRegex = "\\-?\\d+"

  def optimizeBirthDate(xmlDate: String) =
  {
    if(!xmlDate.matches(XmlDateYMDRegex))
    {
      xmlDate + "01-01"
    }
  }

  def optimizeDeathDate(xmlDate: String) =
  {
    if(!xmlDate.matches(XmlDateYMDRegex))
    {
      xmlDate + "12-31"
    }
  }
}
