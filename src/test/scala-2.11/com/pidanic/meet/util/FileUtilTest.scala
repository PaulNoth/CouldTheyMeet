package com.pidanic.meet.util

import java.io.File

import org.scalatest.{FlatSpec, Matchers}

class FileUtilTest extends FlatSpec with Matchers {

  "FileUtil" should "throw an exception if no file" in {
    a [IllegalArgumentException] should be thrownBy {
      FileUtil.getExtension(null)
    }
  }

  it should "throw an exception if file name is empty" in {
    a [IllegalArgumentException] should be thrownBy {
      FileUtil.getExtension(new File(""))
    }
  }

  it should "return correct extension if file has an extension" in {
    FileUtil.getExtension(new File("sample.txt")) should be ("txt")
  }

  it should "return correct extension if file has an more than one '.' in its name" in {
    FileUtil.getExtension(new File("sample.en.txt")) should be ("txt")
  }

  it should "return empty string if file does not have extension" in {
    FileUtil.getExtension(new File("sample")) should be ("")
  }

  "File extension" should "be in lowercase" in {
    FileUtil.getExtension(new File("sample.TxT")) should be ("txt")
  }
}
