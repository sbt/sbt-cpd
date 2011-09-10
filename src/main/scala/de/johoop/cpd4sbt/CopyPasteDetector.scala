/*
 * This file is part of cpd4sbt.
 * 
 * Copyright (c) 2010, 2011 Joachim Hofer
 * All rights reserved.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.johoop.cpd4sbt

import scala.xml._

import java.io.File

import sbt._
import Keys.TaskStreams

object CopyPasteDetector extends Plugin with Settings {
  def cpdAction(reportSettings: ReportSettings, sourceSettings: SourceSettings, maxMem: Int, streams: TaskStreams) {
    IO createDirectory reportSettings.path
    streams.log info "Hello, CPD!"
  }
}

//  override def cpdTask(commandLine: List[String], streams: TaskStreams): Unit = {
//    streams.log.debug("CPD command line to execute: \"%s\"" format (commandLine mkString " "))
    
//    executeCommandLine(commandLine, streams.log)

//  /** Override this in order to change the behaviour of the cpd task. */
//  protected def cpdAction = task {
//    val commandLine = cpdCommandLine() 
//    executeCPDCommandLine(commandLine)
//  }
//}

//  private[cpd4sbt] def cpdCommandLine() = 
//      cpdJavaCall ++ cpdCallOptions 
//  
//  private lazy val cpdJavaCall = {
//    val cpdLibPath = configurationPath(cpdConfig)
//    val cpdClasspath = (cpdLibPath ** "*.jar").relativeString
//
//    List("java", "-Xmx%dm".format(cpdMaxMemoryInMB),
//        "-Dfile.encoding=%s".format(cpdOutputEncoding),
//        "-cp", cpdClasspath, "net.sourceforge.pmd.cpd.CPD")
//  }
//
//  private lazy val cpdCallOptions = {
//    cpdSourcePaths.flatMap(path => List("--files", path.projectRelativePath)) ++
//    List("--minimum-tokens", cpdMinimumTokens.toString,
//        "--language", cpdLanguage.toString,
//  "--encoding", cpdSourceEncoding,
//  "--format", "net.sourceforge.pmd.cpd.%sRenderer".format(cpdReportType))
//  }

