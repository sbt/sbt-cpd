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

import sbt._
import Keys._

import Language._
import ReportType._

private[cpd4sbt] object Settings extends CpdKeys {

  val CpdConfig = config("cpd") hide

  val defaultSettings = Seq(
    ivyConfigurations += CpdConfig,
    libraryDependencies += "net.sourceforge.pmd" % "pmd-dist" % "5.4.2" % "cpd->default",

    cpdTargetPath := crossTarget.value / "cpd",
    cpdSourceDirectories in Compile := { (unmanagedSourceDirectories in Compile).value },

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
        cpdIgnoreAnnotations.value),

    cpdClasspath := Classpaths managedJars (CpdConfig, classpathTypes value, update value)
  )
}
