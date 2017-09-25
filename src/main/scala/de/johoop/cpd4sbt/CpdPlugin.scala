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

import sbt._
import Keys._
import Compat.Process
import sbt.plugins.JvmPlugin

object CpdPlugin extends AutoPlugin {

  object autoImport extends CpdKeys

  import autoImport._

  override def trigger: PluginTrigger = allRequirements
  override def requires: Plugins = JvmPlugin

  val CpdConfig = config("cpd") hide
  override lazy val projectConfigurations = Seq(CpdConfig)

  override lazy val projectSettings =
    Seq(
      ivyConfigurations += CpdConfig,
      libraryDependencies += "net.sourceforge.pmd" % "pmd-dist" % "5.4.2" % "cpd->default",
      cpdTargetPath := crossTarget.value / "cpd",
      cpdSourceDirectories in Compile := { (unmanagedSourceDirectories in Compile).value },
      cpdReportName := "cpd.xml",
      cpdMaxMemoryInMB := 512,
      cpdMinimumTokens := 100,
      cpdSourceEncoding := "utf-8",
      cpdReportFileEncoding := "utf-8",
      cpdLanguage := CpdLanguage.Scala,
      cpdReportType := CpdReportType.XML,
      cpdOutputType := CpdOutputType.File,
      cpdSkipDuplicateFiles := false,
      cpdSkipLexicalErrors := false,
      cpdIgnoreLiterals := false,
      cpdIgnoreIdentifiers := false,
      cpdIgnoreAnnotations := false,
      cpdSourceSettings := { cpdSourceSettings.dependsOn(compile in Compile).value },
      cpdReportSettings :=
        ReportSettings(
          cpdTargetPath.value,
          cpdReportName.value,
          cpdReportFileEncoding.value,
          cpdReportType.value,
          cpdOutputType.value),
      cpdSourceSettings :=
        SourceSettings(
          (cpdSourceDirectories in Compile).value,
          cpdSourceEncoding.value,
          cpdLanguage.value,
          cpdMinimumTokens.value,
          cpdSkipDuplicateFiles.value,
          cpdSkipLexicalErrors.value,
          cpdIgnoreLiterals.value,
          cpdIgnoreIdentifiers.value,
          cpdIgnoreAnnotations.value
        ),
      cpdClasspath := Classpaths managedJars (CpdConfig, classpathTypes value, update value),
      cpd := cpdAction(
        cpdReportSettings.value,
        cpdSourceSettings.value,
        cpdMaxMemoryInMB.value,
        cpdClasspath.value,
        streams.value)
    )

  private def cpdAction(
      reportSettings: ReportSettings,
      sourceSettings: SourceSettings,
      maxMem: Int,
      classpath: Classpath,
      streams: TaskStreams) {
    IO createDirectory reportSettings.path

    def booleanOptions(options: (String, Boolean)*): List[String] = options.filter(_._2).map(_._1).toList

    val commandLine = List(
      "java",
      "-Xmx%dm" format maxMem,
      "-Dfile.encoding=%s" format reportSettings.encoding,
      "-cp",
      PathFinder(classpath.files).absString,
      "net.sourceforge.pmd.cpd.CPD",
      "--minimum-tokens",
      sourceSettings.minTokens.toString,
      "--language",
      sourceSettings.language.name,
      "--encoding",
      sourceSettings.encoding,
      "--format",
      "net.sourceforge.pmd.cpd.%sRenderer" format reportSettings.format.name
    ) ++
      sourceSettings.dirs.filter(_.isDirectory).flatMap(file => List("--files", file.getPath)) ++
      booleanOptions(
        ("--skip-duplicate-files", sourceSettings.skipDuplicateFiles),
        ("--skip-lexical-errors", sourceSettings.skipLexicalErrors),
        ("--ignore-literals", sourceSettings.ignoreLiterals),
        ("--ignore-identifiers", sourceSettings.ignoreIdentifiers),
        ("--ignore-annotations", sourceSettings.ignoreAnnotations)
      )

    streams.log debug "Executing: %s".format(commandLine mkString "\n")

    reportSettings.outputType match {
      case CpdOutputType.File =>
        Process(commandLine) #> (reportSettings.path / reportSettings.name) ! streams.log
      case CpdOutputType.Console =>
        Process(commandLine) ! streams.log
    }
  }
}
