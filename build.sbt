organization := "de.johoop"

name := "cpd4sbt"

version := "1.1.0-SNAPSHOT"

sbtPlugin := true

publishMavenStyle := true

publishTo := Some("Scala Tools Nexus" at "http://nexus.scala-tools.org/content/repositories/releases/")

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

