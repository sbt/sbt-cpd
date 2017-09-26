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

package com.github.sbt.cpd

import com.github.sbt.cpd.settings.{CpdLanguage, CpdOutputType, CpdReportType}
import org.scalatest.{Matchers, WordSpec}
import sbt._

// scalastyle:off magic.number
class CpdRunnerSpec extends WordSpec with Matchers {

  private val cpdClasspath = Seq(
    Attributed.blank(file("pmd-core.jar")),
    Attributed.blank(file("pmd-scala.jar"))
  )

  private val defaultReport = ReportSettings(
    file("target") / "cpd",
    "cpd.xml",
    "UTF-8",
    CpdReportType.XML,
    CpdOutputType.File
  )

  private val srcDir1 = file("src") / "main" / "scala"
  private val srcDir2 = file("src") / "test" / "scala"
  // srcDir3 should get ignored
  private val srcDir3 = file("src") / "main" / "scala-extra"

  private val defaultSource = SourceSettings(
    Seq(srcDir1),
    "UTF-8",
    CpdLanguage.Scala,
    100,
    skipDuplicateFiles = false,
    skipLexicalErrors = false,
    ignoreLiterals = false,
    ignoreIdentifiers = false,
    ignoreAnnotations = false
  )

  "CpdRunner.buildCommandLine" which {
    "with default options" should {
      val args = checkBaseArgs(defaultReport, defaultSource)

      "have args" in {
        args should contain theSameElementsAs Seq(
          "--minimum-tokens",
          "100",
          "--language",
          "scala",
          "--encoding",
          "UTF-8",
          "--format",
          "net.sourceforge.pmd.cpd.XMLRenderer",
          "--files",
          srcDir1.absolutePath
        )
      }
    }

    "with source settings" should {
      val source = SourceSettings(
        Seq(srcDir1, srcDir2, srcDir3),
        "ISO-8559-1",
        CpdLanguage.Java,
        50,
        skipDuplicateFiles = true,
        skipLexicalErrors = true,
        ignoreLiterals = true,
        ignoreIdentifiers = true,
        ignoreAnnotations = true
      )
      val args = checkBaseArgs(defaultReport, source)

      "have args" in {
        args should contain theSameElementsAs Seq(
          "--minimum-tokens",
          "50",
          "--language",
          "java",
          "--encoding",
          "ISO-8559-1",
          "--format",
          "net.sourceforge.pmd.cpd.XMLRenderer",
          "--skip-duplicate-files",
          "--skip-lexical-errors",
          "--ignore-literals",
          "--ignore-identifiers",
          "--ignore-annotations",
          "--files",
          srcDir1.absolutePath,
          "--files",
          srcDir2.absolutePath
        )
      }
    }

    "with report settings" should {
      val report = ReportSettings(
        file("target") / "cpd-report",
        "cpd.csv",
        "ISO-8559-1",
        CpdReportType.CSV,
        CpdOutputType.File
      )
      val args = checkBaseArgs(report, defaultSource)

      "have args" in {
        args should contain theSameElementsAs Seq(
          "--minimum-tokens",
          "100",
          "--language",
          "scala",
          "--encoding",
          "UTF-8",
          "--format",
          "net.sourceforge.pmd.cpd.CSVRenderer",
          "--files",
          srcDir1.absolutePath
        )
      }
    }
  }

  def checkBaseArgs(report: ReportSettings, source: SourceSettings, maxMemory: Int = 1024): Seq[String] = {
    val cmd = CpdRunner.buildCommandLine(cpdClasspath, report, source, maxMemory)

    "have base args" in {
      cmd.take(5) shouldBe Seq(
        s"-Xmx${maxMemory}m",
        "-cp",
        cpdClasspath.map(_.data.absolutePath).mkString(":"),
        s"-Dfile.encoding=${report.encoding}",
        "net.sourceforge.pmd.cpd.CPD"
      )
    }

    cmd.drop(5)
  }
}
