/*
 * This file is part of cpd4sbt.
 * 
 * Copyright (c) 2010 Joachim Hofer
 * All rights reserved.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
import sbt._

class Project(info: ProjectInfo) extends PluginProject(info) {
  val publishTo = "Nexus Scala Tools" at "http://nexus.scala-tools.org/content/repositories/releases/"
  Credentials(Path.userHome / ".ivy2" / ".credentials", log)

  lazy val specs = "org.scala-tools.testing" % "specs" % "1.6.2.1" % "test"
  lazy val mockito = "org.mockito" % "mockito-all" % "1.8.4" % "test"
}
