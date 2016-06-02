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

  val cpdConfig = config("cpd") hide

  val defaultSettings = Seq(
    ivyConfigurations += cpdConfig,
    libraryDependencies += "net.sourceforge.pmd" % "pmd-dist" % "5.4.2" % "cpd->default",

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

    cpdClasspath := Classpaths managedJars (cpdConfig, classpathTypes value, update value)
  )
}
