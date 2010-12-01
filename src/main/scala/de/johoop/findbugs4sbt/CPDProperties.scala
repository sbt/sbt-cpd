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

import java.io.File
import scala.xml.Node

import sbt.Path
import sbt.PathFinder

trait CPDProperties {

  /** Output path for CPD reports. Defaults to <code>outputPath / "cpd"</code>. */
  protected val cpdOutputPath: Path

  // TODO
}
