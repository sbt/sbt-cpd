organization := "de.johoop"

name := "cpd4sbt"

version := "1.1.3"

sbtPlugin := true

resolvers += "Sonatype Release" at "https://oss.sonatype.org/content/repositories/releases"

scalaVersion := "2.10.2"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-language:_")

