name := "BBC Programme Dispatch"

organization := "com.omidaladini"

version := "0.0.1"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.10" % "2.0" % "test" withSources() withJavadoc(),
  "org.scalacheck" %% "scalacheck" % "1.10.0" % "test" withSources() withJavadoc(),
  "com.typesafe.play" %% "play-json" % "2.4.0-M1"
)

initialCommands := "import com.omidaladini.bbcprogrammedispatch._"

