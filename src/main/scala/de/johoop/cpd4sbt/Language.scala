/*
 * This file is part of cpd4sbt.
 * 
 * Copyright (c) 2010-2013 Joachim Hofer
 * All rights reserved.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.johoop.cpd4sbt

object Language {
  sealed trait Language {
    val name: String
  }

  case object C extends Language { val name = "c" }
  case object CPP extends Language { val name = "cpp" }
  case object CS extends Language { val name = "cs" }
  case object Java extends Language { val name = "java" }
  case object JSP extends Language { val name = "jsp" }
  case object PHP extends Language { val name = "php" }
  case object Ruby extends Language { val name = "ruby" }
  case object Fortran extends Language { val name = "fortran" }
  case object ECMAScript extends Language { val name = "ecmascript" }
  case object JavaScript extends Language { val name = "ecmascript" }
  case object PLSQL extends Language { val name = "plsql" }
  
  case object Scala extends Language { val name = "scala" }
}
