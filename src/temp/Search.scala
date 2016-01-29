import scala.io.Source
/**
 * Created by paulp on 2/7/15.
 */
class Search {

  private[this] val PATH = "data/sample_output.csv"

  private val analyzer = new StandardAnalyzer
  private val directory = new RAMDirectory

  initializeDictionaryData()

  def initializeDictionaryData() = {
    val file = Source.fromFile(PATH)

    val indexConfig = new IndexWriterConfig(Version.LATEST, analyzer)
    val indexWriter = new IndexWriter(directory, indexConfig)

    val lines = file.getLines()

    lines.foreach {
      line =>
        val words = line.split(",")
        val document = new Document

        document.add(new Field("name", words(1), TextField.TYPE_STORED))
        document.add(new Field("surname", words(2), TextField.TYPE_STORED))
        document.add(new Field("givenname", words(3), TextField.TYPE_STORED))
        document.add(new Field("description", words(4), TextField.TYPE_STORED))
        document.add(new Field("birthdate", words(5), TextField.TYPE_STORED))
        document.add(new Field("deathdate", words(6), TextField.TYPE_STORED))

        indexWriter.addDocument(document)
    }

    indexWriter.close()
  }

  def search(name: String)= {
    val dirReader = DirectoryReader.open(directory)
    val indexSearcher = new IndexSearcher(dirReader)

    val queryParser = new QueryParser("surname", analyzer)
    val query = queryParser.parse(name)

    val topDocs = indexSearcher.search(query, 50)
    val hits = topDocs.scoreDocs

    val persons = hits.map(scoreDoc => documentToPerson(indexSearcher.doc(scoreDoc.doc))).toList
    dirReader.close()
    persons
  }

  private def documentToPerson(doc: Document): Person = {
    val name = doc.get("name")
    val surname = doc.get("surname")
    val givenname = doc.get("givenname")
    val desc = doc.get("description")
    val birthDate = doc.get("birthdate")
    val deathdate = doc.get("deathdate")
    Person(name, surname, givenname, desc, birthDate, deathdate)
  }
}
