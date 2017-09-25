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

package de.johoop.cpd4sbt

import java.io.File

import de.johoop.cpd4sbt.Language.Language
import de.johoop.cpd4sbt.OutputType.OutputType
import de.johoop.cpd4sbt.ReportType.ReportType
import sbt.Keys._
import sbt._

object CpdKeys extends CpdKeys

trait CpdKeys {
  private[cpd4sbt] case class ReportSettings(
      path: File,
      name: String,
      encoding: String,
      format: ReportType,
      outputType: OutputType)

  private[cpd4sbt] case class SourceSettings(
      dirs: Seq[File],
      encoding: String,
      language: Language,
      minTokens: Int,
      skipDuplicateFiles: Boolean,
      skipLexicalErrors: Boolean,
      ignoreLiterals: Boolean,
      ignoreIdentifiers: Boolean,
      ignoreAnnotations: Boolean)

  val cpd = taskKey[Unit]("Run CPD analysis")

  val cpdSourceDirectories =
    settingKey[Seq[File]]("Source files to analyze.")
  val cpdSourceEncoding =
    settingKey[String]("Source file encoding.")

  val cpdTargetPath =
    settingKey[File]("Output path for CPD reports.")
  val cpdReportName =
    settingKey[String]("Name of the report file to generate.")
  val cpdReportFileEncoding =
    settingKey[String]("Report file encoding.")
  val cpdReportType =
    settingKey[ReportType]("Type of CPD report.")
  val cpdOutputType =
    settingKey[OutputType]("Type of CPD output.")

  val cpdLanguage =
    settingKey[Language]("Language to analyze.")
  val cpdMaxMemoryInMB =
    settingKey[Int]("Maximum amount of memory to allow for CPD (in MB).")
  val cpdMinimumTokens =
    settingKey[Int]("Minimum number of tokens of potential duplicates.")

  val cpdSkipDuplicateFiles =
    settingKey[Boolean]("Ignore multiple copies of files of the same name and length in comparison.")
  val cpdSkipLexicalErrors =
    settingKey[Boolean]("Skip files which can't be tokenized due to invalid characters instead of aborting.")
  val cpdIgnoreLiterals =
    settingKey[Boolean]("Ignore literal value differences when evaluating a duplicate block.")
  val cpdIgnoreIdentifiers =
    settingKey[Boolean]("Ignore identifier name differences when evaluating a duplicate block.")
  val cpdIgnoreAnnotations =
    settingKey[Boolean]("Ignore language annotations when evaluating a duplicate block.")

  // TODO remove
  val cpdClasspath = taskKey[Classpath]("cpd-classpath")

  // TODO remove
  val cpdSourceSettings = taskKey[SourceSettings]("cpd-source-settings")

  // TODO remove
  val cpdReportSettings = taskKey[ReportSettings]("cpd-report-settings")

}
