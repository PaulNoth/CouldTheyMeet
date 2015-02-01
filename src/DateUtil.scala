import java.text.{SimpleDateFormat, DateFormat}
import java.util.Date

/**
 * Created by paulp on 2/1/15.
 */
object DateUtil {
  private[this] val XML_DATE_PATTERN = "GGyyyy-MM-dd"
  private[this] val dateFormatter: DateFormat = new SimpleDateFormat(XML_DATE_PATTERN)
  private[this] val XmlDateRegex = "\\-?\\d+\\-\\d{1,2}\\-\\d{1,2}"

  def parseDate(s: String): Date =
  {
    // TODO
    //if(isRequiredXmlDateFormat(s))
      dateFormatter.parse(s)
    //new Date
  }

  def isRequiredXmlDateFormat(s: String) =
  {
    s.matches(XmlDateRegex)
  }

  private[this] def isBc(xmlDate: String) =
    xmlDate.startsWith("-")

  private[this] def updateToFormatterPattern(s: String): String =
  {
    var result = s

    result = prefixWithEra(result)
    result
  }

  private[this] def prefixWithEra(date: String): String = {
    if(isBc(date))
      date.replace("-", "BC")
    else
      "AD" + date
  }
}
