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

object OutputType extends Enumeration {
  sealed trait OutputType

  case object File extends OutputType
  case object Console extends OutputType
}
