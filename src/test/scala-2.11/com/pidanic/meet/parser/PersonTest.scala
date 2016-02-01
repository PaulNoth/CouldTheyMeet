package com.pidanic.meet.parser

import org.scalatest.{Matchers, FlatSpec}

class PersonTest extends FlatSpec with Matchers {

  "Person" should "throw an exception if 'Resource' property is null" in {
    an [IllegalArgumentException] should be thrownBy
      Person(null, "name", "surname", "givenName", "desc", "birth", "death")
  }

  it should "not throw an exception if 'Resource' property is empty string" in {
    noException should be thrownBy
      Person("", "name", "surname", "givenName", "desc", "birth", "death")
  }

  it should "throw an exception if 'Name' property is null" in {
    an [IllegalArgumentException] should be thrownBy
      Person("resource", null, "surname", "givenName", "desc", "birth", "death")
  }

  it should "not throw an exception if 'Name' property is empty string" in {
    noException should be thrownBy
      Person("resource", "", "surname", "givenName", "desc", "birth", "death")
  }

  it should "throw an exception if 'Surname' property is null" in {
    an [IllegalArgumentException] should be thrownBy
      Person("resource", "name", null, "givenName", "desc", "birth", "death")
  }

  it should "not throw an exception if 'Surname' property is empty string" in {
    noException should be thrownBy
      Person("resource", "name", "", "givenName", "desc", "birth", "death")
  }

  it should "throw an exception if 'GivenName' property is null" in {
    an [IllegalArgumentException] should be thrownBy
      Person("resource", "name", "surname", null, "desc", "birth", "death")
  }

  it should "not throw an exception if 'GivenName' property is empty string" in {
    noException should be thrownBy
      Person("resource", "name", "surname", "", "desc", "birth", "death")
  }

  it should "throw an exception if 'Description' property is null" in {
    an [IllegalArgumentException] should be thrownBy
      Person("resource", "name", "surname", "given", null, "birth", "death")
  }

  it should "not throw an exception if 'Description' property is empty string" in {
    noException should be thrownBy
      Person("resource", "name", "surname", "given", "", "birth", "death")
  }

  it should "throw an exception if 'BirthDate' property is null" in {
    an [IllegalArgumentException] should be thrownBy
      Person("resource", "name", "surname", "given", "desc", null, "death")
  }

  it should "not throw an exception if 'BirthDate' property is empty string" in {
    noException should be thrownBy
      Person("resource", "name", "surname", "given", "desc", "", "death")
  }

  it should "throw an exception if 'DeathDate' property is null" in {
    an [IllegalArgumentException] should be thrownBy
      Person("resource", "name", "surname", "given", "desc", "birth", null)
  }

  it should "not throw an exception if 'DeathDate' property is empty string" in {
    noException should be thrownBy
      Person("resource", "name", "surname", "given", "desc", "birth", "")
  }

  it should "format all property values in arbitrary separator" in {
    val person = Person("resource", "name", "surname", "given", "desc", "birth", "death")
    person.toCsvString(";") should be ("resource;name;surname;given;desc;birth;death")
    person.toCsvString(",") should be ("resource,name,surname,given,desc,birth,death")
    person.toCsvString("|") should be ("resource|name|surname|given|desc|birth|death")
    person.toCsvString(":") should be ("resource:name:surname:given:desc:birth:death")
  }

  it should "format should be only separators if all properties are empty" in {
    val person = Person("", "", "", "", "", "", "")
    person.toCsvString(";") should be (";;;;;;")
    person.toCsvString(",") should be (",,,,,,")
    person.toCsvString("|") should be ("||||||")
    person.toCsvString(":") should be ("::::::")
  }
}
