package eu.pidanic.couldmeet.search

import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.document.{TextField, Field, Document}
import org.apache.lucene.index.{DirectoryReader, IndexWriter, IndexWriterConfig}
import org.apache.lucene.queryparser.classic.QueryParser
import org.apache.lucene.search.IndexSearcher
import org.apache.lucene.store.RAMDirectory
import org.apache.lucene.util.Version

import scala.collection.mutable.ListBuffer

/**
 * Created by paulp on 2/7/15.
 */
class Search {

  private[this] val PATH = "data/sample_output.csv"

  private val analyzer = new StandardAnalyzer
  private val directory = new RAMDirectory

  initializeDictionaryData()

  def initializeDictionaryData() = {
    val file = io.Source.fromFile(PATH)

    val indexConfig = new IndexWriterConfig(Version.LATEST, analyzer)
    val indexWriter = new IndexWriter(directory, indexConfig)

    val lines = file.getLines()
    lines.foreach {
      line =>
        println(line)
        val words = line.split(",")
        println(words.toList.size)
        println(words.toList)
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

    val result: ListBuffer[Person] = scala.collection.mutable.ListBuffer.empty
    hits.foreach {
      hit =>
        val hitDoc = indexSearcher.doc(hit.doc)
        val name = hitDoc.get("name")
        val surname = hitDoc.get("surname")
        val givenname = hitDoc.get("givenname")
        val desc = hitDoc.get("description")
        val birthDate = hitDoc.get("birthdate")
        val deathdate = hitDoc.get("deathdate")
        val person = Person(name, surname, givenname, desc, birthDate, deathdate)

        result.+=(person)

    }

    dirReader.close()

    result
  }
}
