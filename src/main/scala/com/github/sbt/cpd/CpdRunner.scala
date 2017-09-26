/*
 * This file is part of sbt-cpd
 *
 * Copyright (c) Joachim Hofer & contributors
 * All rights reserved.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package com.github.sbt.cpd

import java.io.{File, FileOutputStream}

import com.github.sbt.cpd.CpdPlugin.autoImport.CpdOutputType
import sbt.Keys._
import sbt._

import scala.util.control.NonFatal

object CpdRunner {
  private val CpdMainClass = "net.sourceforge.pmd.cpd.CPD"
  private val CpdExitOk = 0
  private val CpdExitDuplicatesFound = 4

  def runCpd(
      report: ReportSettings,
      source: SourceSettings,
      maxMem: Int,
      cpdClasspath: Classpath,
      javaHome: Option[File],
      streams: TaskStreams): Unit = {

    val cmd = buildCommandLine(cpdClasspath, report, source, maxMem)

    streams.log.debug("Executing CPD command line.")

    val dest = report.outputType match {
      case CpdOutputType.File =>
        IO.createDirectory(report.path)
        Some(report.path / report.name)
      case _ =>
        None
    }

    executeCommandLine(cmd, javaHome, streams.log, dest)
  }

  private[cpd] def buildCommandLine(
      cpdClasspath: Classpath,
      report: ReportSettings,
      source: SourceSettings,
      maxMem: Int): Seq[String] = {

    baseCommand(cpdClasspath, report, maxMem) ++
      sourceSettingArgs(source) ++
      reportSettingsArgs(report)
  }

  private def baseCommand(cpdClasspath: Classpath, report: ReportSettings, maxMem: Int): Seq[String] = {
    Seq(
      s"-Xmx${maxMem}m",
      "-cp",
      PathFinder(cpdClasspath.files).absString,
      s"-Dfile.encoding=${report.encoding}",
      CpdMainClass
    )
  }

  private def sourceSettingArgs(source: SourceSettings): Seq[String] = {
    Seq(
      "--minimum-tokens",
      source.minTokens.toString,
      "--language",
      source.language.name,
      "--encoding",
      source.encoding
    ) ++
      source.dirs.filter(_.isDirectory).flatMap(file => List("--files", file.absolutePath)) ++
      booleanArgs(
        "--skip-duplicate-files" -> source.skipDuplicateFiles,
        "--skip-lexical-errors" -> source.skipLexicalErrors,
        "--ignore-literals" -> source.ignoreLiterals,
        "--ignore-identifiers" -> source.ignoreIdentifiers,
        "--ignore-annotations" -> source.ignoreAnnotations
      )
  }

  def booleanArgs(options: (String, Boolean)*): Seq[String] = {
    options.filter(_._2).map(_._1)
  }

  private def reportSettingsArgs(report: ReportSettings): Seq[String] = {
    Seq(
      "--format",
      s"net.sourceforge.pmd.cpd.${report.format.name}Renderer"
    )
  }

  private def executeCommandLine(
      commandLine: Seq[String],
      javaHome: Option[File],
      log: Logger,
      destFile: Option[File]): Unit = {

    try {
      val output = destFile match {
        case Some(f) =>
          // TODO close
          Some(CustomOutput(new FileOutputStream(f)))
        case None =>
          Some(LoggedOutput(log))
      }

      val forkOptions = ForkOptions(
        javaHome,
        output,
        Vector.empty[File],
        None,
        Vector.empty[String],
        connectInput = false,
        Map.empty[String, String])

      val exitValue = Fork.java(forkOptions, commandLine)

      exitValue match {
        case CpdExitOk => // pass with no duplicates
        case CpdExitDuplicatesFound =>
          // duplicates found
          // TODO fail
        case e =>
          sys.error(s"Nonzero exit value when attempting to call CPD: $e")
      }
    } catch {
      case NonFatal(e) =>
        sys.error(s"Exception while executing CPD: ${e.getMessage}")
    }
  }
}
