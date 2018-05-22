# SWATT Selenium Sample Project
This is a sample project to show how to use SWATT for Selenium testing of a Web App (WUI).

> _(Developed with [IntelliJ](https://www.jetbrains.com/idea/).)_

# Execution
To run, you can have **IntelliJ** Run/Debug the `RegressionTestSuite.xml` file.

Or you can run the **maven** command: `mvn dependency:purge-local-repository clean test "-Dtest-ng-file=RegressionTestSuite.xml"`.
(The `purge` and `clean` flags are optional.) 