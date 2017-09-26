# sbt-cpd - Copy & Paste Detection using PMD/CPD for sbt

[![Build Status](https://travis-ci.org/sbt/sbt-cpd.svg?branch=master)](https://travis-ci.org/sbt/sbt-cpd)
[![SBT 0.13 version](https://img.shields.io/badge/sbt_0.13-2.0.0--M2-blue.svg)](https://bintray.com/stringbean/sbt-plugins/sbt-cpd)
[![SBT 1.0 version](https://img.shields.io/badge/sbt_1.0-2.0.0--M2-blue.svg)](https://bintray.com/stringbean/sbt-plugins/sbt-cpd)

This is an [sbt](http://scala-sbt.org/) plugin for duplicate code detection using
[Copy/Paste Detector](https://pmd.github.io/latest/usage/cpd-usage.html) (CPD) from the [PMD](https://pmd.github.io)
project.

Install the plugin by adding the following to `project/plugins.sbt`:

```scala
addSbtPlugin("com.github.sbt" % "sbt-cpd" % "<version>")
```

And then execute the plugin with `sbt cpd`. This will scan your source code under `src/main/scala` and output a report
containing the duplicates to `target/scala-2.xx/cpd/cpd.xml`.

## Settings

(see also the [CPD documentation](https://pmd.github.io/pmd-5.4.2/usage/cpd-usage.html))

| Key                     | Type              | Default             | Description |
| ----------------------- | ----------------- | ------------------- | ----------- |
| `cpdReportType`         | `CpdReportType`   | `CpdReportType.XML` | Selects the output format for the CPD report. Valid types: `XML`, `Simple`, `CSV` & `VS` |
| `cpdLanguage`           | `CpdLanguage`     | `CpdLanguage.Scala` | Language to analyze.<sup>1</sup> |
| `cpdMaxMemoryInMB`      | Integer           | `512`               | Maximum amount of memory to allow for CPD (in MB). |
| `cpdMinimumTokens`      | Integer           | `100`               | Minimum number of tokens of potential duplicates. |
| `cpdSourceEncoding`     | String            | `utf-8`             | Source file encoding. |
| `cpdSourceDirectories`  | `Seq` (Paths)     | main source path(s) | Paths of the source files to analyze. |
| `cpdOutputType`         | `CpdOutputType`   | `CpdOutputType.File`| Selects the output type for the CPD report.<sup>2</sup> Valid types: `File`, `Console` |
| `cpdTargetPath`         | File Path         | `<cross-target>/cpd`| Output path for CPD reports. |
| `cpdReportName`         | String (filename) | `cpd.xml`           | Name of the report file to generate (under `cpdTargetPath`). |
| `cpdReportFileEncoding` | String            | `utf-8`             | Report file encoding. |
| `cpdSkipDuplicateFiles` | Boolean           | `false`             | Ignore multiple copies of files of the same name and length in comparison. |
| `cpdSkipLexicalErrors`  | Boolean           | `false`             | Skip files which can't be tokenized due to invalid characters instead of aborting. |
| `cpdIgnoreLiterals`     | Boolean           | `false`             | Ignore literal value differences when evaluating a duplicate block. |
| `cpdIgnoreIdentifiers`  | Boolean           | `false`             | Ignore identifier name differences when evaluating a duplicate block. |
| `cpdIgnoreAnnotations`  | Boolean           | `false`             | Ignore language annotations when evaluating a duplicate block. |
| `cpdFailOnDuplicates`   | Boolean           | `false`             | Fail the build if duplicates are detected. |

1. Valid languages:
    * `Scala`
    * `Java`
    * `C`
    * `CPP`
    * `CS`
    * `CSharp`
    * `ECMAScript`
    * `Fortran`
    * `Go`
    * `JSP`
    * `JavaScript`
    * `Matlab`
    * `ObjectiveC`
    * `PHP`
    * `PLSQ`
    * `Ruby`
    * `Swift`
1. If set to `CpdOutputType.Console` then `cpdTargetPath` and `cpdReportName` are ignored.

## Contributors

Many thanks to

* [corux](https://github.com/corux)
* [Jentsch](https://github.com/Jentsch)
* [Martin Mauch](https://github.com/nightscape)
* [Matic Potočnik](https://github.com/HairyFotr)

for their awesome contributions!

## License

This program and the accompanying materials are made available under the terms of the **Eclipse Public License v1.0** which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
