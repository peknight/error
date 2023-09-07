ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.0"

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
    errorHttp4s,
  )
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

lazy val errorHttp4s = (project in file("error-http4s"))
  .aggregate(
    errorHttp4sClient.jvm,
    errorHttp4sClient.js,
  )
  .settings(commonSettings)
  .settings(
    name := "error-http4s",
  )

lazy val errorHttp4sClient = (crossProject(JSPlatform, JVMPlatform) in file("error-http4s/http4s-client"))
  .dependsOn(errorCore)
  .settings(commonSettings)
  .settings(
    name := "error-http4s-client",
    libraryDependencies ++= Seq(
      "org.http4s" %%% "http4s-client" % http4sVersion,
    ),
  )

val catsVersion = "2.10.0"
val catsParseVersion = "0.3.10"
val spireVersion = "0.18.0"
val http4sVersion = "1.0.0-M34"
