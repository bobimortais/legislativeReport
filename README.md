# legislativeReport

A command-line tool that reads legislative data from four CSV files and produces two summary reports: how each legislator voted across bills and how much support or opposition each bill received.

Built with **Java 21** and **Maven**.

## Table of Contents

- [What it does](#what-it-does)
- [Input files](#input-files)
- [Output files](#output-files)
- [Environment prerequisites](#environment-prerequisites)
- [Build](#build)
- [How to run](#how-to-run)
- [Running the tests](#running-the-tests)
- [Troubleshooting](#troubleshooting)

## What it does

`legislativeReport` takes four CSV files as command-line arguments, combines them, and writes two CSV reports to an `out/` folder:

1. A per-legislator report with the number of bills each legislator supported and opposed.
2. A per-bill report with the number of supporters and opposers each bill received.

## Input files

The jar expects exactly **four** CSV files, passed in this order:

| # | File          | Description                                  |
|---|---------------|----------------------------------------------|
| 1 | `person`        | Legislator data                              |
| 2 | `bills`         | Bills that were created                      |
| 3 | `votes`         | Votes cast for each bill                     |
| 4 | `vote_results`  | Result of each vote, per bill                |

Each file must be a valid CSV with a header row. Quoted values containing commas (for example `"Silva, Ana"`) are supported.

## Output files

Both files are written to the `out/` folder, which is created automatically if it does not exist. Existing files with the same names are overwritten.

| File                                   | Content                                                                 |
|----------------------------------------|-------------------------------------------------------------------------|
| `out/legislators-support-oppose-count.csv` | For each legislator, how many bills they supported and how many they opposed |
| `out/bills.csv`                            | For each bill, how many supporters and how many opposers it had         |

## Environment prerequisites

| Requirement | Version        | Notes                                              |
|-------------|----------------|----------------------------------------------------|
| JDK         | **21** or newer | Needed to both build and run the project           |
| Maven       | 3.9 or newer   | Needed only to build the jar                       |
| Git         | any            | Only if you are cloning the repository             |

Verify your setup:

```bash
java -version    # should report version 21.x
mvn -version     # should report Maven 3.9+ and Java version 21.x
```

If `mvn -version` shows an older Java version, set `JAVA_HOME` to a JDK 21 installation:

```bash
# Linux / macOS
export JAVA_HOME=/path/to/jdk-21

# Windows (PowerShell)
$env:JAVA_HOME = "C:\path\to\jdk-21"
```

## Build

From the project root:

```bash
mvn clean package
```

This compiles the code, runs the tests, and produces an executable jar in `target/`, for example:

```
target/legislativeReport-1.0-SNAPSHOT.jar
```

The exact file name depends on the `artifactId` and `version` in `pom.xml`. To skip the tests during the build:

```bash
mvn clean package -DskipTests
```

## How to run

Run the jar and pass the four CSV files as arguments, in this order: `person`, `bills`, `votes`, `vote_results`.

```bash
java -jar target/legislativeReport-1.0-SNAPSHOT.jar \
  person.csv \
  bills.csv \
  votes.csv \
  vote_results.csv
```

On Windows (PowerShell), put it on one line:

```powershell
java -jar target\legislativeReport-1.0-SNAPSHOT.jar person.csv bills.csv votes.csv vote_results.csv
```

You can pass full or relative paths. If a path contains spaces, wrap it in quotes:

```bash
java -jar target/legislativeReport-1.0-SNAPSHOT.jar "data/person.csv" "data/bills.csv" "data/votes.csv" "data/vote_results.csv"
```

When it finishes, the reports are available at:

```
out/legislators-support-oppose-count.csv
out/bills.csv
```

## Running the tests

```bash
mvn test
```

## Troubleshooting

| Problem | Likely cause and fix |
|---------|----------------------|
| `UnsupportedClassVersionError` | You are running with a Java version older than 21. Check `java -version`. |
| `no main manifest attribute` | The jar was not built as an executable jar. Rebuild with `mvn clean package` and confirm the `Main-Class` is configured in `pom.xml`. |
| `NoClassDefFoundError: org/apache/commons/csv/...` | The dependencies were not bundled into the jar. Use the shaded jar produced by `mvn clean package`. |
| `Expected 4 file paths but got N` | Pass exactly four CSV files. |
| `File not found: ...` | Check the path. Relative paths are resolved from the directory where you run the command. |
| `NoSuchFileException` for the output file | Run from a directory where you have write permission so `out/` can be created. |
