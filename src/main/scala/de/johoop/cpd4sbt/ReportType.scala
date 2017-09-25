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

object ReportType extends Enumeration {
  sealed trait ReportType {
    val name: String
  }

  case object Simple extends ReportType { val name = "Simple" }
  case object VS extends ReportType { val name = "VS" }
  case object XML extends ReportType { val name = "XML" }
  case object CSV extends ReportType { val name = "CSV" }
}
