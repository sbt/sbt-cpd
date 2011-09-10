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

import java.io.File

import sbt._
import Keys._

object CopyPasteDetector extends Plugin with Settings {
  def cpdAction(reportSettings: ReportSettings, sourceSettings: SourceSettings, maxMem: Int, 
      classpath: Classpath, streams: TaskStreams) {
    
    IO createDirectory reportSettings.path
    
    val cpdClasspath = PathFinder(classpath.files).absString

    val commandLine = List("java", 
        "-Xmx%dm" format maxMem, 
        "-Dfile.encoding=%s" format reportSettings.encoding,
        "-cp", cpdClasspath, 
        "net.sourceforge.pmd.cpd.CPD",
        "--minimum-tokens", sourceSettings.minTokens,
        "--language", sourceSettings.language.toString,
        "--encoding", sourceSettings.encoding,
        "--format", "net.sourceforge.pmd.cpd.%sRenderer" format reportSettings.format) ++
        sourceSettings.dirs flatMap (List("--files", _))
    
    streams.log debug "Executing: %s".format(commandLine mkString "\n")
  }
}
