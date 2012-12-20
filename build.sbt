organization := "de.johoop"

name := "cpd4sbt"

version := "1.1.2"

sbtPlugin := true

resolvers += "Sonatype Release" at "https://oss.sonatype.org/content/repositories/releases"

scalaVersion := "2.10.0"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-language:_")

