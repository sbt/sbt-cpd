/*
 * This file is part of cpd4sbt.
 * 
 * Copyright (c) 2010-2013 Joachim Hofer
 * All rights reserved.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.johoop.cpd4sbt

import sbt._
import Keys._
import Project.Initialize

import Language._
import ReportType._
import OutputType._

import java.io.File

private[cpd4sbt] trait Settings extends Plugin {
  private val cpdConfig = config("cpd") hide  

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

  private[cpd4sbt] case class ReportSettings(path: File, name: String, encoding: String, format: ReportType, outputType: OutputType)
  val cpdReportSettings = TaskKey[ReportSettings]("cpd-report-settings")

  private[cpd4sbt] case class SourceSettings(dirs: Seq[File], encoding: String, language: Language, minTokens: Int,
      skipDuplicateFiles: Boolean, skipLexicalErrors: Boolean, ignoreLiterals: Boolean, ignoreIdentifiers: Boolean, ignoreAnnotations: Boolean)
  val cpdSourceSettings = TaskKey[SourceSettings]("cpd-source-settings")
  
  val cpdClasspath = TaskKey[Classpath]("cpd-classpath")
  
  val cpd = TaskKey[Unit]("cpd")
  
  protected def cpdAction(reportSettings: ReportSettings, sourceSettings: SourceSettings, 
      maxMem: Int, classpath: Classpath, streams: TaskStreams)

  val cpdSettings = Seq(
      ivyConfigurations += cpdConfig,
      libraryDependencies += "net.sourceforge.pmd" % "pmd" % "5.1.1" % "cpd->default",
      
      cpdTargetPath := crossTarget.value / "cpd",
      cpdSourceDirectories in Compile <<= unmanagedSourceDirectories in Compile,
      
      cpdReportName := "cpd.xml",
      cpdMaxMemoryInMB := 512,
      cpdMinimumTokens := 100,
      cpdSourceEncoding := "utf-8",
      cpdReportFileEncoding := "utf-8",
      cpdLanguage := Scala,
      cpdReportType := XML,
      cpdOutputType := OutputType.File,
      cpdSkipDuplicateFiles := false,
      cpdSkipLexicalErrors := false,
      cpdIgnoreLiterals := false,
      cpdIgnoreIdentifiers := false,
      cpdIgnoreAnnotations := false,
  
      cpdSourceSettings <<= cpdSourceSettings.dependsOn(compile in Compile),
      
      cpdReportSettings <<= (cpdTargetPath, cpdReportName, cpdReportFileEncoding, cpdReportType, cpdOutputType) map ReportSettings,
      cpdSourceSettings <<= (cpdSourceDirectories in Compile, cpdSourceEncoding, cpdLanguage, cpdMinimumTokens, cpdSkipDuplicateFiles, cpdSkipLexicalErrors, cpdIgnoreLiterals, cpdIgnoreIdentifiers, cpdIgnoreAnnotations) map SourceSettings,
  
      cpdClasspath := Classpaths managedJars (cpdConfig, classpathTypes value, update value),
      
      cpd <<= (cpdReportSettings, cpdSourceSettings, cpdMaxMemoryInMB, cpdClasspath, streams) map cpdAction)
}
