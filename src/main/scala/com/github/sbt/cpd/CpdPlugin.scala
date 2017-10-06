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

import sbt.Keys._
import sbt._
import sbt.plugins.JvmPlugin

object CpdPlugin extends AutoPlugin {

  object autoImport extends CpdKeys

  import autoImport._ // scalastyle:ignore import.grouping

  override def trigger: PluginTrigger = allRequirements
  override def requires: Plugins = JvmPlugin

  override lazy val projectSettings =
    Seq(
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
      cpdFailOnDuplicates := false,
      cpd := CpdRunner.runCpd(
        cpdReportSettings.value,
        cpdSourceSettings.value,
        cpdMaxMemoryInMB.value,
        cpdFailOnDuplicates.value,
        cpdClasspath.value,
        javaHome.value,
        streams.value)
    )

  private lazy val cpdClasspath = Def.task {
    // TODO is this the best way?
    Project.extract(state.value).currentUnit.unit.plugins.fullClasspath
  }

  private lazy val cpdSourceSettings = Def.task {
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
    )
  }

  private lazy val cpdReportSettings = Def.task {
    ReportSettings(
      cpdTargetPath.value,
      cpdReportName.value,
      cpdReportFileEncoding.value,
      cpdReportType.value,
      cpdOutputType.value)
  }
}
