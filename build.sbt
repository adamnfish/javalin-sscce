ThisBuild / scalaVersion     := "2.13.1"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.adamnfish"
ThisBuild / organizationName := "adamnfish"

lazy val root = (project in file("."))
  .settings(
    name := "javalin-sscce",
    libraryDependencies ++= Seq(
      "io.javalin" % "javalin" % "3.8.0",
      "org.slf4j" % "slf4j-simple" % "1.8.0-beta4",
      "org.slf4j" % "slf4j-api" % "1.8.0-beta4"
    )
  )
