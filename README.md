# Code Status
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/ga.rugal.maven/commitlinter-maven-plugin/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/ga.rugal.maven/commitlinter-maven-plugin)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Javadocs](https://javadoc.io/badge/ga.rugal.maven/commitlinter-maven-plugin.svg)](https://javadoc.io/doc/ga.rugal.maven/commitlinter-maven-plugin)
[![CircleCI](https://circleci.com/gh/Rugal/commitlinter-maven-plugin.svg?style=svg)](https://circleci.com/gh/Rugal/commitlinter-maven-plugin)
[![codecov](https://codecov.io/gh/Rugal/commitlinter-maven-plugin/branch/master/graph/badge.svg)](https://codecov.io/gh/Rugal/commitlinter-maven-plugin)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/943c0bb74b1a47a6be8579d50e608dba)](https://www.codacy.com/app/ryujinwrath/commitlinter-maven-plugin?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Rugal/commitlinter-maven-plugin&amp;utm_campaign=Badge_Grade)

# Usage
This plugin lints your git commit message according to the rules you defined.  
It basically reads commit message from git repository, matches it with the Regex you provided, before linting each capture group according to your rules.  

```xml
<plugin>
  <groupId>ga.rugal.maven</groupId>
  <artifactId>commitlinter-maven-plugin</artifactId>
  <version>THE-VERSION-YOU-LIKE</version>
</plugin>
```

Then run command:
```bash
mvn commitlinter:validate
```
This will report nothing as we haven't configure any linting rules.

## Show case
[![asciicast](https://asciinema.org/a/MkGawonrwNZsrq6gRjzpNS9Cd.png)](https://asciinema.org/a/MkGawonrwNZsrq6gRjzpNS9Cd)

# Parameters

Parameter | Type | Description | Default
---|---|---|---
captureGroups| CaptureGroup[] | List of CaptureGroups | []
captureGroup.caseFormat | enum | The case format we want to lint | NONE
captureGroup.max | Integer | The maximum length of this capture group | Integer.MAX
captureGroup.min | Integer | The minimum length of this capture group | 0
captureGroup.tense | enum | The tense of the initial word of this capture group | NONE
failOnError|Boolean | Whether to fail maven build on linting error | false
gitFolder|String|The git repository folder| .git
head|String | The pointer of git | HEAD
matchPattern |Regex|The regex to match commit message|(.*)
skip|Boolean|Whether to skip linting| false
testCommitMessage|String|The commit message to test with|""

## caseFormat

case | sample
---|---
UPPERCASE | THIS IS UPPER CASE/THIS_IS_UPPER_CASE_TOO
LOWERCASE | this is lower case/this_is_lower_case_too
UPPERCAMELCASE | ThisIsUpperCamelCase
LOWERCAMELCASE | thisIsLowerCamelCase
KEBABCASE | this-is-kebab-case
SNAKECASE | this_is_snake_case
SENTENCECASE | This is sentence case
NONE | ANY_case-you Like

## tense

case | sample
---|---
PRESENT | add new feature/create a function  
PAST |  added new feature/created a function  
THIRD_PARTY | adds new feature/creates a function
NONE |  any format you like

# Simple Example
Please always make sure to wrap the capture group with `()` so the Regex matcher can capture it.  

## With Basic Configuration

```xml
<plugin>
  <groupId>ga.rugal.maven</groupId>
  <artifactId>commitlinter-maven-plugin</artifactId>
  <version>THE-VERSION-YOU-LIKE</version>
  <configuration>
    <matchPattern>([\w\s]+-\d+:\s)(.*)</matchPattern>
    <failOnError>true</failOnError>
    <captureGroups>
      <captureGroup>
        <max>10</max>
        <min>2</min>
        <caseFormat>LOWERCASE</caseFormat>
      </captureGroup>
      <captureGroup>
        <max>20</max>
        <tense>PRESENT</tense>
        <caseFormat>LOWERCASE</caseFormat>
      </captureGroup>
    </captureGroups>
  </configuration>
</plugin>
```

This configuration will match the git commit message with Regex, then lint them with the rules defined above.

## Bind With Lifecycle

This will bind `validate` goal in validate phase of Maven lifecycle.
```xml
<plugin>
  <groupId>ga.rugal.maven</groupId>
  <artifactId>commitlinter-maven-plugin</artifactId>
  <version>THE-VERSION-YOU-LIKE</version>
  <executions>
    <execution>
      <id>validate</id>
      <phase>validate</phase>
      <configuration>
        <matchPattern>([\w\s]+-\d+:\s)(.*)</matchPattern>
        <failOnError>true</failOnError>
        <captureGroups>
          <captureGroup>
            <caseFormat>LOWERCASE</caseFormat>
          </captureGroup>
          <captureGroup>
            <caseFormat>LOWERCASE</caseFormat>
          </captureGroup>
        </captureGroups>
      </configuration>
      <goals>
        <goal>validate</goal>
      </goals>
    </execution>
  </executions>
</plugin>
```

# Credit
* The creation of this plugin is inspired by [commitlint](https://github.com/marionebl/commitlint)
