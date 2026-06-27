import com.peknight.build.gav.*
import com.peknight.build.sbt.*

commonSettings

ThisBuild / scalacOptions --= Seq("-Werror", "-Xfatal-warnings")

lazy val error = (project in file("."))
  .settings(name := "error")
  .aggregate(errorCore.projectRefs *)
  .aggregate(errorSpire.projectRefs *)

lazy val errorCore = (projectMatrix in file("error-core"))
  .settings(name := "error-core")
  .settings(libraryDependencies ++= dependencies(peknight.cats))
  .jvmPlatform(scalaVersions = Seq(scala.scala3.version))
  .jsPlatform(scalaVersions = Seq(scala.scala3.version))
  .nativePlatform(scalaVersions = Seq(scala.scala3.version))

lazy val errorSpire = (projectMatrix in file("error-spire"))
  .dependsOn(errorCore)
  .settings(name := "error-spire")
  .settings(libraryDependencies ++= dependencies(typelevel.spire))
  .jvmPlatform(scalaVersions = Seq(scala.scala3.version))
  .jsPlatform(scalaVersions = Seq(scala.scala3.version))
