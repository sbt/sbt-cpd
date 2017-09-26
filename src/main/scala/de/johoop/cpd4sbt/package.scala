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

package de.johoop

import java.io.File

import de.johoop.cpd4sbt.settings.CpdLanguage.Language
import de.johoop.cpd4sbt.settings.CpdOutputType.OutputType
import de.johoop.cpd4sbt.settings.CpdReportType.ReportType

package object cpd4sbt {
  case class ReportSettings(path: File, name: String, encoding: String, format: ReportType, outputType: OutputType)

  case class SourceSettings(
      dirs: Seq[File],
      encoding: String,
      language: Language,
      minTokens: Int,
      skipDuplicateFiles: Boolean,
      skipLexicalErrors: Boolean,
      ignoreLiterals: Boolean,
      ignoreIdentifiers: Boolean,
      ignoreAnnotations: Boolean)
}
