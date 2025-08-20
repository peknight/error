import com.peknight.build.gav.*
import com.peknight.build.sbt.*

commonSettings

lazy val error = (project in file("."))
  .settings(name := "error")
  .aggregate(
    errorCore.jvm,
    errorCore.js,
    errorCore.native,
    errorSpire.jvm,
    errorSpire.js,
  )

lazy val errorCore = (crossProject(JVMPlatform, JSPlatform, NativePlatform) in file("error-core"))
  .settings(name := "error-core")
  .settings(crossDependencies(peknight.instances.cats.clazz))

lazy val errorSpire = (crossProject(JVMPlatform, JSPlatform) in file("error-spire"))
  .dependsOn(errorCore)
  .settings(name := "error-spire")
  .settings(crossDependencies(typelevel.spire))
