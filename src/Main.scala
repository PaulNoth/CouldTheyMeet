/**
 * Created by paulp on 1/31/15.
 */
object Main extends App {
  val file = io.Source.fromFile("data/sample_persondata_en.ttl")
  val lines = file.getLines.toList.drop(1)
  val lines2 = lines.map(_.replaceAll("[<>]", "")).groupBy(_.split("\\s+").apply(0))
  lines2.foreach {
    line => println(line)
  }
  val lines3 = lines2.mapValues(_.filter(filterPersonData(_)).map(_.split("\\s+").toList.slice(1, 3)).flatten )

  lines3.foreach {
    line => println(line)
  }

  val lines4 = lines2.mapValues(_.filter(filterPersonData(_)).map(_.split("[^\\s]+").toList).flatten )

  lines4.foreach {
    line => println(line)
  }

  file.close

  def filterPersonData(s: String) =
  {
    val lower = s.toLowerCase
    lower.contains("birthdate") || lower.contains("deathdate") || lower.contains("name") || lower.contains("surname") || lower.contains("givenname") || lower.contains("description") || lower.contains("birthplace")
  }
}
