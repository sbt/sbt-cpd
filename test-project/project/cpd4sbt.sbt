lazy val root = Project("plugins", file(".")) dependsOn file("..").getAbsoluteFile.toURI
