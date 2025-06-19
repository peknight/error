ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.7.1"

ThisBuild / organization := "com.peknight"

ThisBuild / publishTo := {
  val nexus = "https://nexus.peknight.com/repository"
  if (isSnapshot.value)
    Some("snapshot" at s"$nexus/maven-snapshots/")
  else
    Some("releases" at s"$nexus/maven-releases/")
}

ThisBuild / credentials ++= Seq(
  Credentials(Path.userHome / ".sbt" / ".credentials")
)

ThisBuild / resolvers ++= Seq(
  "Pek Nexus" at "https://nexus.peknight.com/repository/maven-public/",
)

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
  .settings(commonSettings)
  .settings(
    name := "error",
  )

lazy val errorCore = (crossProject(JSPlatform, JVMPlatform) in file("error-core"))
  .settings(commonSettings)
  .settings(
    name := "error-core",
    libraryDependencies ++= Seq(
      "com.peknight" %%% "cats-instances-class" % pekInstancesVersion
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

val spireVersion = "0.18.0"
val pekVersion = "0.1.0-SNAPSHOT"
val pekInstancesVersion = pekVersion
