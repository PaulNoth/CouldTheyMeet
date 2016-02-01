package com.pidanic.meet.parser

private[parser] sealed case class Person
  (resource: String, name: String, surname: String,
   givenName: String, description: String,
   birthDate: String, deathDate: String) {

  require(resource != null)
  require(name != null)
  require(surname != null)
  require(givenName != null)
  require(description != null)
  require(birthDate != null)
  require(deathDate != null)

  def toCsvString(separator: Char): String = {
    resource + "" + separator + "" +
      name + "" + separator + "" +
      surname + "" + separator + "" +
      givenName + "" + separator + "" +
      description + "" + separator + "" +
      birthDate + "" + separator + "" +
      deathDate
  }
}
