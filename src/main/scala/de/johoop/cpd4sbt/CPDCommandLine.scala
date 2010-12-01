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
package de.johoop.cpd4sbt

import sbt.DefaultProject
import sbt.Path

trait CPDCommandLine extends DefaultProject 
    with CPDProperties
    with CPDDependencies {

  private[cpd4sbt] def cpdCommandLine() = 
      cpdJavaCall ++ cpdCallOptions ++ List(">%s".format(cpdOutputPath / cpdReportFilename))

  private lazy val cpdJavaCall = {
    val cpdLibPath = configurationPath(cpdConfig)
    val cpdClasspath = (cpdLibPath ** "*.jar").relativeString

    List("java", "-Xmx%dm".format(cpdMaxMemoryInMB),
        "-Dfile.encoding=%s".format(cpdOutputEncoding),
        "-cp", cpdClasspath, "net.sourceforge.pmd.cpd.CPD")
  }

  private lazy val cpdCallOptions = {
    cpdSourcePaths.flatMap(path => List("--files", path.projectRelativePath)) ++
    List("--minimum-tokens", cpdMinimumTokens.toString,
        "--language", cpdLanguage.toString,
	"--encoding", cpdSourceEncoding,
	"--format", "net.sourceforge.pmd.cpd.%sRenderer".format(cpdReportType))
  }
}
