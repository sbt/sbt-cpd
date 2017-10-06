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

// scalastyle:off number.of.methods number.of.types
object CpdLanguage {
  sealed abstract class Language(val name: String)

  case object Scala extends Language("scala")
  case object Java extends Language("java")

  case object C extends Language("c")
  case object CPP extends Language("cpp")
  case object CS extends Language("cs")
  case object CSharp extends Language("cs")
  case object ECMAScript extends Language("ecmascript")
  case object Fortran extends Language("fortran")
  case object Go extends Language("go")
  case object JSP extends Language("jsp")
  case object JavaScript extends Language("ecmascript")
  case object Matlab extends Language("matlab")
  case object ObjectiveC extends Language("objectivec")
  case object PHP extends Language("php")
  case object PLSQL extends Language("plsql")
  case object Ruby extends Language("ruby")
  case object Swift extends Language("swift")
}
