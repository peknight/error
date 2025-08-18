import com.peknight.build.gav.*
import com.peknight.build.sbt.*

commonSettings

lazy val error = (project in file("."))
  .aggregate(
    errorCore.jvm,
    errorCore.js,
    errorCore.native,
    errorSpire.jvm,
    errorSpire.js,
  )
  .settings(
    name := "error",
  )

lazy val errorCore = (crossProject(JVMPlatform, JSPlatform, NativePlatform) in file("error-core"))
  .settings(crossDependencies(peknight.instances.cats.clazz))
  .settings(
    name := "error-core",
  )

lazy val errorSpire = (crossProject(JVMPlatform, JSPlatform) in file("error-spire"))
  .dependsOn(errorCore)
  .settings(crossDependencies(typelevel.spire))
  .settings(
    name := "error-spire",
  )
