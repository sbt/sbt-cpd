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

package com.github.sbt

import java.io.File

import com.github.sbt.cpd.settings.CpdLanguage.Language
import com.github.sbt.cpd.settings.CpdOutputType.OutputType
import com.github.sbt.cpd.settings.CpdReportType.ReportType

package object cpd {
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
