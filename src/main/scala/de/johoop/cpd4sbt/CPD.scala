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

import sbt._
import FileUtilities._

import scala.xml._

import java.io.File

trait CPD extends DefaultProject
    with CommandLineExecutor
    with CPDProperties
    with CPDCommandLine {

  override lazy val cpdOutputPath = outputPath / "cpd"
  override lazy val cpdSourcePaths = mainSources

  final lazy val cpd = cpdAction

  /** Override this in order to change the behaviour of the cpd task. */
  protected def cpdAction = task {
    createDirectory(cpdOutputPath, log)
    val commandLine = cpdCommandLine() 
    executeCPDCommandLine(commandLine)
  }
}
