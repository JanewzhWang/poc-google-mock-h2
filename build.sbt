name := """google-mock-h2"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  evolutions,
  "org.squeryl" % "squeryl_2.11" % "0.9.5-7",
  "com.h2database" % "h2" % "1.4.180"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

unmanagedSourceDirectories in Compile <+= target.zipWith(scalaBinaryVersion) { (b,v) => b / s"scala -$v/twirl/main" }

