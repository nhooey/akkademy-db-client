name := "akkademy-db-client"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "com.akkademy-db" %% "akkademy-db" % "0.0.1-SNAPSHOT",
  "org.scalatest" %% "scalatest" % "3.0.4" % "test",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2"
)
