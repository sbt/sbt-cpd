/*
 * This file is part of cpd4sbt.
 *
 * Copyright (c) Joachim Hofer
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
import sbt.{TaskKey, SettingKey}

trait CpdKeys {
  private[cpd4sbt] case class ReportSettings(
    path: File, name: String, encoding: String,
    format: ReportType, outputType: OutputType)

  private[cpd4sbt] case class SourceSettings(
    dirs: Seq[File], encoding: String, language: Language, minTokens: Int,
    skipDuplicateFiles: Boolean, skipLexicalErrors: Boolean,
    ignoreLiterals: Boolean, ignoreIdentifiers: Boolean, ignoreAnnotations: Boolean)

  /** Source files to analyze. Defaults to <code>unmanagedSourceDirectories</code>. */
  val cpdSourceDirectories = SettingKey[Seq[File]]("cpd-source-directories")

  /** Output path for CPD reports. Defaults to <code>target / "cpd"</code>. */
  val cpdTargetPath = SettingKey[File]("cpd-target-path")

  /** Name of the report file to generate. Defaults to <code>"cpd.xml"</code> */
  val cpdReportName = SettingKey[String]("cpd-report-name")

  /** Report file encoding. Defaults to <code>"utf-8"</code>. */
  val cpdReportFileEncoding = SettingKey[String]("cpd-report-file-encoding")

  /** Maximum amount of memory to allow for CPD (in MB). Defaults to <code>512</code>.*/
  val cpdMaxMemoryInMB = SettingKey[Int]("cpd-max-memory-in-mb")

  /** Minimum number of tokens of potential duplicates. Defaults to <code>100</code>.*/
  val cpdMinimumTokens = SettingKey[Int]("cpd-minimum-tokens")

  /** Source file encoding. Defaults to <code>"utf-8"</code>. */
  val cpdSourceEncoding = SettingKey[String]("cpd-source-encoding")

  /** Language to analyze. Defaults to <code>Scala</code>.
    * Note: There's currently no specific Scala tokenizer implemented in CPD. Using Scala
    * as language will default to the "AnyLanguage" tokenizer. If you want Scala specifically,
    * extend the CPD tokenizers! */
  val cpdLanguage = SettingKey[Language]("cpd-language")

  /** Type of CPD report. Defaults to <code>XML</code>. */
  val cpdReportType = SettingKey[ReportType]("cpd-report-type")

  /** Type of CPD output. Defaults to <code>File</code>. */
  val cpdOutputType = SettingKey[OutputType]("cpd-output-type")

  /** Ignore multiple copies of files of the same name and length in comparison. */
  val cpdSkipDuplicateFiles = SettingKey[Boolean]("cpd-skip-duplicate-files")

  /** Skip files which can't be tokenized due to invalid characters instead of aborting. */
  val cpdSkipLexicalErrors = SettingKey[Boolean]("cpd-skip-lexical-errors")

  /** Ignore literal value differences when evaluating a duplicate block. */
  val cpdIgnoreLiterals = SettingKey[Boolean]("cpd-ignore-literals")

  /** Ignore identifier name differences when evaluating a duplicate block. */
  val cpdIgnoreIdentifiers = SettingKey[Boolean]("cpd-ignore-identifiers")

  /** Ignore language annotations when evaluating a duplicate block. */
  val cpdIgnoreAnnotations = SettingKey[Boolean]("cpd-ignore-annotations")

  val cpdClasspath = TaskKey[Classpath]("cpd-classpath")

  val cpdSourceSettings = TaskKey[SourceSettings]("cpd-source-settings")

  val cpdReportSettings = TaskKey[ReportSettings]("cpd-report-settings")

  val cpd = TaskKey[Unit]("cpd")
}
