import java.text.{DateFormat, ParseException, SimpleDateFormat}
import java.util.Date

/**
 * Created by paulp on 2/1/15.
 */
object DateUtil {

  private[this] val XML_DATE_PATTERN = "yyyy-MM-dd"
  private[this] val dateFormatter: DateFormat = new SimpleDateFormat(XML_DATE_PATTERN)

  def parseDate(s: String): Date = {
    try {
      dateFormatter.parse(s)
    } catch {
      case parseEx: ParseException =>
        println("unknown format: " + s)
        new Date
    }
  }

  def format(date: Date) =
  {
    new SimpleDateFormat("dd.MM.yyyy GG").format(date)
  }
}
