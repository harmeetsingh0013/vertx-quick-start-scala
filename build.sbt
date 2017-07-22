name := "vertex-quick-start"

version := "1.0"

scalaVersion := "2.12.2"

resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"

val scalaTestVersion = "3.0.3"
libraryDependencies ++= Seq(
  "io.vertx" %% "vertx-lang-scala" % "3.4.2",
  "org.scalactic" %% "scalactic" % scalaTestVersion,
  "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
)

