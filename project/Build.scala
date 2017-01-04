import sbt._
import Keys._
import spray.revolver.RevolverPlugin.autoImport.Revolver

object Resolvers {
}

object BuildSettings {

  val buildOrganization = "com.yimei180"
  val appName = "sbt-publish-example"
  val buildVersion = "0.0.1-SNAPSHOT"
  val buildScalaVersion = "2.11.8"
  val buildScalaOptions = Seq("-unchecked", "-deprecation", "-encoding", "utf8")

  import Dependencies._

  val buildSettings = Defaults.coreDefaultSettings ++ Seq(
    organization := buildOrganization,
    version := buildVersion,
    scalaVersion := buildScalaVersion,
    libraryDependencies ++= appDependencies,    // dependencies
    scalacOptions := buildScalaOptions
  ) ++ Revolver.settings
}

object PublishSettings {

  // publish settings
  val publishSettings = Seq(
    credentials += Credentials("Sonatype Nexus Repository Manager", "maven.yimei180.com", "admin", "admin123"),
    publishTo := Some("Sonatype Nexus Repository Manager" at "http://maven.yimei180.com/content/repositories/snapshots"),
    publishMavenStyle := true,
    isSnapshot := true,
    publishArtifact in Test := false,
    pomIncludeRepository := { (repo: MavenRepository) => false },
    pomExtra := pomXml
  )

  lazy val pomXml = {
    <url>https://github.com/epiphyllum/sbt-publish-example</url>
      <licenses>
        <license>
          <name>Apache License 2.0</name>
          <url>http://www.apache.org/licenses/</url>
          <distribution>repo</distribution>
        </license>
      </licenses>
      <scm>
        <url>git@github.com:epiphyllum/sbt-publish-example.git</url>
        <connection>scm:git:git@github.com:epiphyllum/sbt-publish-example.git</connection>
      </scm>
      <developers>
        <developer>
          <id>hary</id>
          <name>hary</name>
          <url>http://github.com/epiphyllum</url>
        </developer>
      </developers>
  }
}

object ApplicationBuild extends Build {

  import BuildSettings._
  import PublishSettings._

  lazy val main = Project(
    appName,
    file("."),
    settings = buildSettings ++ publishSettings)
}
