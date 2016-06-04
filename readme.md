# cpd4sbt - Static code analysis via PMD/CPD from within SBT

This SBT plug-in enables you to analyze your code with the help of the great **[PMD Copy/Paste Detector](https://pmd.github.io/pmd-5.4.2/usage/cpd-usage.html)** tool. It defines a `cpd` sbt action for that purpose.

For SBT 0.13, you've basically got to add the following to your project's `build.sbt` file:

```scala
import de.johoop.cpd4sbt.CopyPasteDetector._

cpdSettings
```

Also, you have to add the plugin dependency to your project's `./project/plugins.sbt` or the global `.sbt/project/build.sbt`:

```scala
addSbtPlugin("de.johoop" % "cpd4sbt" % "1.1.5")
```

The settings specified below are still mostly valid, but they're now specified using the new settings system of SBT 0.13.

## Change Log

* *1.1.5*
    * Added settings for the CPD command line switches `ignore-annotations`, `ignore-literals`, `ignore-identifiers`, `skip-duplicate-files` and `skip-lexical-errors`
    * Added `cs` and `plsql` languages (and fixed a typo)
    * Added option to print the CPD report to the console

## Settings

(see also the [CPD documentation](http://pmd.sourceforge.net/cpd-usage.html))

### `cpdReportType`

* *Description:* Selects the output format for the CPD report. Requires to import `import de.johoop.cpd4sbt._`.
* *Accepts:* `ReportType.{Simple, XML, CSV, VS}`
* *Default:* `ReportType.Xml`

### `cpdTargetPath`

* *Description:* Output path for CPD reports.
* *Accepts:* any `sbt.Path`
* *Default:* `outputPath / "cpd"`

### `cpdReportName`

* *Description:* Name of the report file to generate.
* *Accepts:* any legal filename
* *Default:* `"cpd.xml"`

### `cpdOutputType`

* *Description:* Selects the output type for the CPD report. Requires to import `import de.johoop.cpd4sbt._`.
* *Accepts:* `OutputType.{File, Console}`
* *Default:* `OutputType.File`

### `cpdLanguage`

* *Description:* Language to analyze. - If you want Scala specifically, you still need to extend the CPD tokenizers! Otherwise, choosing Scala will default to the "AnyLanguage" tokenizer
* *Accepts:* `Language.{Java, C, CPP, CS, PHP, Ruby, Fortran, ECMAScript, JavaScript, JSP, PLSQL, Scala}`
* *Default:* `Language.Scala`

### `cpdMaxMemoryInMB`

* *Description:* Maximum amount of memory to allow for CPD (in MB).
* *Accepts:* any reasonable amount of memory as an integer value
* *Default:* `512`

### `cpdMinimumTokens`

* *Description:* Minimum number of tokens of potential duplicates.
* *Accepts:* A positive `Int`
* *Default:* `100`

### `cpdSourceEncoding`

* *Description:* Source file encoding.
* *Accepts:* String representations of valid encodings.
* *Default:* `"utf-8"`

### `cpdOutputEncoding`

* *Description:* Output file encoding.
* *Accepts:* String representations of valid encodings.
* *Default:* `"utf-8"`

### `cpdSourceDirectories`

* *Description:* Paths of the source files to analyze.
* *Accepts:* A `List` of `Path`s
* *Default:* `List(mainSourcePath)`.

### `cpdSkipDuplicateFiles`

* *Description:* Ignore multiple copies of files of the same name and length in comparison.
* *Accepts:* `Boolean`
* *Default:* `false`

### `cpdSkipLexicalErrors`

* *Description:* Skip files which can't be tokenized due to invalid characters instead of aborting.
* *Accepts:* `Boolean`
* *Default:* `false`

### `cpdIgnoreLiterals`

* *Description:* Ignore literal value differences when evaluating a duplicate block.
* *Accepts:* `Boolean`
* *Default:* `false`

### `cpdIgnoreIdentifiers`

* *Description:* Ignore identifier name differences when evaluating a duplicate block.
* *Accepts:* `Boolean`
* *Default:* `false`

### `cpdIgnoreAnnotations`

* *Description:* Ignore language annotations when evaluating a duplicate block.
* *Accepts:* `Boolean`
* *Default:* `false`

## Contributors

Many thanks to

* [corux](https://github.com/corux)
* [Jentsch](https://github.com/Jentsch)
* [Martin Mauch](https://github.com/nightscape)
* [Matic Potočnik](https://github.com/HairyFotr)

for their awesome contributions!

## License

This program and the accompanying materials are made available under the terms of the **Eclipse Public License v1.0** which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
