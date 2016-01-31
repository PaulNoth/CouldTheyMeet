package com.pidanic.meet.parser

private[parser] sealed case class Person
  (resource: String, name: String, surname: String,
   givenName: String, description: String,
   birthDate: String, deathDate: String)
