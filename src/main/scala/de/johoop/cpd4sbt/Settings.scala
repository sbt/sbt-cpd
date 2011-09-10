/*
 * This file is part of cpd4sbt.
 * 
 * Copyright (c) 2010, 2011 Joachim Hofer
 * All rights reserved.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.johoop.cpd4sbt

import sbt._
import Keys._
import Project.Initialize

private[cpd4sbt] trait Settings extends Plugin {
  val cpd = TaskKey[Unit]("cpd")
  
  protected def cpdAction

  private val cpdConfig = config("cpd") hide  
  
  val cpdSettings = Seq(
      ivyConfigurations += cpdConfig,
      libraryDependencies += "pmd" % "pmd" % "4.2.5" % "cpd->default" intransitive(),
      
      cpd := cpdAction)
}

//  /** Output path for CPD reports. Defaults to <code>outputPath / "cpd"</code>. */
//  protected val cpdOutputPath: Path
//
//  /** Name of the report file to generate. Defaults to <code>"cpd.xml"</code> */
//  protected lazy val cpdReportName = "cpd.xml"
//
//  /** Paths of the source files to analyze. Defaults to <code>List(mainSourcePath)</code>. */
//  protected val cpdSourcePaths: List[Path]
//
//  /** Maximum amount of memory to allow for CPD (in MB). Defaults to <code>512</code>.*/
//  protected lazy val cpdMaxMemoryInMB = 512
//
//  /** Minimum number of tokens of potential duplicates. Defaults to <code>100</code>.*/
//  protected lazy val cpdMinimumTokens = 100
//
//  /** Source file encoding. Defaults to <code>"utf-8"</code>. */
//  protected lazy val cpdSourceEncoding = "utf-8"
//
//  /** Output file encoding. Defaults to <code>"utf-8"</code>. */
//  protected lazy val cpdOutputEncoding = "utf-8"
//
//  /** Language to analyze. Defaults to <code>Java</code>. 
//    * If you want Scala, extend the CPD tokenizers (or try <code>Any</code>)! */
//  protected lazy val cpdLanguage = CPDLanguage.Java
//
//  /** Type of CPD report. Defaults to <code>XML</code>. */
//  protected lazy val cpdReportType = CPDReportType.XML


//private[findbugs4sbt] case class PathSettings(targetPath: File, reportName: String, analyzedPath: File)
//
//private[findbugs4sbt] case class FilterSettings(includeFilters: Option[Node], excludeFilters: Option[Node])
//
//private[findbugs4sbt] case class MiscSettings(
//  reportType: ReportType, effort: Effort, 
//  onlyAnalyze: Option[Seq[String]], maxMemory: Int, 
//  analyzeNestedArchives: Boolean, sortReportByClassNames: Boolean)
//
//private[findbugs4sbt] trait Settings extends Plugin {
//
//  val findbugs = TaskKey[Unit]("findbugs")
//
//  val findbugsCommandLine = TaskKey[List[String]]("findbugs-command-line")
//  
//  val findbugsClasspath = TaskKey[Classpath]("findbugs-classpath")
//  val findbugsPathSettings = TaskKey[PathSettings]("findbugs-path-settings")
//  val findbugsFilterSettings = TaskKey[FilterSettings]("findbugs-filter-settings")
//  val findbugsMiscSettings = TaskKey[MiscSettings]("findbugs-misc-settings")
//  
//  /** Output path for FindBugs reports. Defaults to <code>target / "findBugs"</code>. */
//  val findbugsTargetPath = SettingKey[File]("findbugs-target-path")
//  
//  /** Name of the report file to generate. Defaults to <code>"findbugs.xml"</code> */
//  val findbugsReportName = SettingKey[String]("findbugs-report-name")
//
//  /** The path to the classes to be analyzed. Defaults to <code>mainCompilePath</code>. */
//  val findbugsAnalyzedPath = SettingKey[File]("findbugs-analyzed-path")
//
//  /** Type of report to create. Defaults to <code>ReportType.Xml</code>. */
//  val findbugsReportType = SettingKey[ReportType]("findbugs-report-type")
//  
//  /** Effort to put into the static analysis. Defaults to <code>ReportType.Medium</code>.*/
//  val findbugsEffort = SettingKey[Effort]("findbugs-effort")
//  
//  /** Optionally, define which packages/classes should be analyzed (<code>None</code> by default) */
//  val findbugsOnlyAnalyze = SettingKey[Option[Seq[String]]]("findbugs-only-analyze")
//
//  /** Maximum amount of memory to allow for FindBugs (in MB). */
//  val findbugsMaxMemory = SettingKey[Int]("findbugs-max-memory")
//
//  /** Whether FindBugs should analyze nested archives or not. Defaults to <code>true</code>. */
//  val findbugsAnalyzeNestedArchives = SettingKey[Boolean]("findbugs-analyze-nested-archives")
//
//  /** Whether the reported bug instances should be sorted by class name or not. Defaults to <code>false</code>.*/
//  val findbugsSortReportByClassNames = SettingKey[Boolean]("findbugs-sort-report-by-class-names")
//
//  /** Optional filter file XML content defining which bug instances to include in the static analysis. 
//    * <code>None</code> by default. */ 
//  val findbugsIncludeFilters = SettingKey[Option[Node]]("findbugs-include-filter")
//
//  /** Optional filter file XML content defining which bug instances to exclude in the static analysis. 
//    * <code>None</code> by default. */ 
//  val findbugsExcludeFilters = SettingKey[Option[Node]]("findbugs-exclude-filter")
//
//  protected def findbugsTask(commandLine: List[String], streams: TaskStreams): Unit
//
//  protected def findbugsCommandLineTask(findbugsClasspath: Classpath, compileClasspath: Classpath, 
//    paths: PathSettings, filters: FilterSettings, misc: MiscSettings, streams: TaskStreams): List[String]
//  
//  private def filterSettingsTask: Initialize[Task[FilterSettings]] = (findbugsIncludeFilters, findbugsExcludeFilters) map {
//    (include, exclude) => FilterSettings(include, exclude)
//  }
//
//  private def pathSettingsTask: Initialize[Task[PathSettings]] = (findbugsTargetPath, findbugsReportName, findbugsAnalyzedPath) map {
//    (targetPath, reportName, analyzedPath) => PathSettings(targetPath, reportName, analyzedPath)
//  }
//
//  private def miscSettingsTask: Initialize[Task[MiscSettings]] = (findbugsReportType, findbugsEffort, findbugsOnlyAnalyze, findbugsMaxMemory, findbugsAnalyzeNestedArchives, findbugsSortReportByClassNames) map { 
//    (p1, p2, p3, p4, p5, p6) => MiscSettings(p1, p2, p3, p4, p5, p6)
//  }
//
//  private val findbugsConfig = config("findbugs") hide
//  
//  val findbugsSettings = Seq(
//    ivyConfigurations += findbugsConfig,
//    libraryDependencies ++= Seq(
//      "com.google.code.findbugs" % "findbugs" % "1.3.9" % "findbugs->default",
//      "com.google.code.findbugs" % "jsr305" % "1.3.9" % "findbugs->default"
//    ),
//      
//    findbugs <<= (findbugsCommandLine, streams) map findbugsTask,
//    
//    findbugsCommandLine <<= (managedClasspath in findbugsCommandLine, managedClasspath in Compile, 
//      findbugsPathSettings, findbugsFilterSettings, findbugsMiscSettings, streams) map findbugsCommandLineTask,
//
//    findbugsPathSettings <<= pathSettingsTask,
//    findbugsFilterSettings <<= filterSettingsTask,
//    findbugsMiscSettings <<= miscSettingsTask,
//
//    findbugsPathSettings <<= findbugsPathSettings.dependsOn(compile in Compile),
//
//    managedClasspath in findbugsCommandLine <<= (classpathTypes, update) map { 
//      (ct, updateReport) => Classpaths.managedJars(findbugsConfig, ct, updateReport) },
//
//    findbugsTargetPath <<= (target) { _ / "findbugs" },
//    findbugsReportType := ReportType.Xml,
//    findbugsEffort := Effort.Medium,
//    findbugsReportName := "findbugs.xml",
//    findbugsMaxMemory := 1024,
//    findbugsAnalyzeNestedArchives := true,
//    findbugsSortReportByClassNames := false,
//    findbugsAnalyzedPath <<= (classDirectory in Compile) { identity[File] },
//    findbugsOnlyAnalyze := None,
//    findbugsIncludeFilters := None,
//    findbugsExcludeFilters := None
//  )
//}
//
