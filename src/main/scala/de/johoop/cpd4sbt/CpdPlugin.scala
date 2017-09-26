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

import sbt.Keys._
import sbt._
import sbt.plugins.JvmPlugin

object CpdPlugin extends AutoPlugin {

  object autoImport extends CpdKeys

  import autoImport._

  override def trigger: PluginTrigger = allRequirements
  override def requires: Plugins = JvmPlugin

  val CpdConfig = config("cpd").hide
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
      cpdClasspath := Classpaths.managedJars(CpdConfig, classpathTypes.value, update.value),
      cpd := CpdRunner.runCpd(
        cpdReportSettings.value,
        cpdSourceSettings.value,
        cpdMaxMemoryInMB.value,
        cpdClasspath.value,
        javaHome.value,
        streams.value)
    )
}
