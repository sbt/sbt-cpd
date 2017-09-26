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

package com.github.sbt.cpd.settings

object CpdReportType {
  sealed abstract class ReportType(val name: String)

  case object Simple extends ReportType("Simple")
  case object VS extends ReportType("VS")
  case object XML extends ReportType("XML")
  case object CSV extends ReportType("CSV")
}
