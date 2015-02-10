name := "couldtheymeet"

version := "1.0"

lazy val `couldtheymeet` = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq( javaJdbc , javaEbean , cache , javaWs,
  "org.apache.lucene" % "lucene-analyzers-common" % "4.10.3",
  "org.apache.lucene" % "lucene-core" % "4.10.3",
  "org.apache.lucene" % "lucene-queryparser" % "4.10.3"
)

// libraryDependencies += "org.scala-tools" % "scala-stm_2.11.1" % "0.3"
unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  