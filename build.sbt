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
      "com.peknight" %%% "generic-core" % pekGenericVersion,
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

val pekGenericVersion = "0.1.0-SNAPSHOT"
val spireVersion = "0.18.0"
