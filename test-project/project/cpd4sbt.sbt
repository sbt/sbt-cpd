lazy val root = Project("plugins", file(".")) dependsOn ProjectRef(file("..").getAbsoluteFile.toURI, "cpd4sbt")
