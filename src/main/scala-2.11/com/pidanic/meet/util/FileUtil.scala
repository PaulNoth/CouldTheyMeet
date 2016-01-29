package com.pidanic.meet.util

import java.io.File

object FileUtil {

  def getExtension(file: File): String = {
    if(file == null) {
      throw new IllegalArgumentException("file is null")
    }
    val fileName = file.getName
    if(fileName.isEmpty) {
      throw new IllegalArgumentException("file does not have name")
    }
    val lastDotIndex = fileName.lastIndexOf('.')
    val extension = if(lastDotIndex < 0) {
                      ""
                    } else {
                      fileName.substring(lastDotIndex + 1).toLowerCase
                    }
    extension
  }
}
