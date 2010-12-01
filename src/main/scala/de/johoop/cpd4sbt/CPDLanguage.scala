/*
 * This file is part of cpd4sbt.
 * 
 * Copyright (c) 2010 Joachim Hofer
 * All rights reserved.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.johoop.cpd4sbt

object CPDLanguage extends Enumeration {
  type CPDLanguage = Value

  val Java = Value("java")
  val CPP = Value("cpp")
  val PHP = Value("php")
  val Ruby = Value("ruby")
  val Fortran = Value("fortran")
  val ECMAScript = Value("ecmascript")
  val JavaScript = Value("ecmascript")
  val Any = Value("any")
  val JSP = Value("jsp")
}
