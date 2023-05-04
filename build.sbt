ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.2.1"

ThisBuild / organization := "com.peknight"

lazy val commonSettings = Seq(
  scalacOptions ++= Seq(
    "-feature",
    "-deprecation",
    "-unchecked",
    "-Xfatal-warnings",
    "-language:strictEquality",
    "-Xmax-inlines:64"
  ),
)

lazy val error = (project in file("."))
  .aggregate(
    errorCore.jvm,
    errorCore.js,
    errorSpire.jvm,
    errorSpire.js,
    errorParse.jvm,
    errorParse.js,
  )
  .enablePlugins(JavaAppPackaging)
  .settings(commonSettings)
  .settings(
    name := "error",
  )

lazy val errorCore = (crossProject(JSPlatform, JVMPlatform) in file("error-core"))
  .settings(commonSettings)
  .settings(
    name := "error-core",
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "cats-core" % catsVersion,
    ),
  )

lazy val errorSpire = (crossProject(JSPlatform, JVMPlatform) in file("error-spire"))
  .dependsOn(errorCore)
  .settings(commonSettings)
  .settings(
    name := "error-spire",
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "spire" % spireVersion,
    ),
  )

lazy val errorParse = (crossProject(JSPlatform, JVMPlatform) in file("error-parse"))
  .dependsOn(errorCore)
  .settings(commonSettings)
  .settings(
    name := "error-parse",
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "cats-parse" % catsParseVersion,
    ),
  )

val catsVersion = "2.9.0"
val catsParseVersion = "0.3.9"
val spireVersion = "0.18.0"
