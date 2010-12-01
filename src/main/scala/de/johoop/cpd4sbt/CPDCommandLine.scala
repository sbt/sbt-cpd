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

trait CPDCommandLine extends DefaultProject 
    with CPDProperties
    with CPDDependencies {

  private[cpd4sbt] def cpdCommandLine() = 
      cpdJavaCall ++ cpdCallOptions

  private lazy val cpdJavaCall = {
    val cpdLibPath = configurationPath(cpdConfig)
    val cpdClasspath = (cpdLibPath ** "*.jar").absString

    List("java", "-Xmx%dm".format(cpdMaxMemoryInMB),
        "-cp", cpdClasspath)
  }

  private lazy val cpdCallOptions = {
    List("--minimum-tokens", cpdMinimumTokens.toString,
        "--language", cpdLanguage.toString)
  }
}
