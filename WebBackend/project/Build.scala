import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "FlockWebBackend"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    javaCore,
    javaEbean,
    "mysql" % "mysql-connector-java" % "5.1.21"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
