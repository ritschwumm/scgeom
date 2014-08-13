name			:= "scgeom"

organization	:= "de.djini"

version			:= "0.22.0"

scalaVersion	:= "2.11.2"

libraryDependencies	++= Seq(
	"org.specs2"	%%	"specs2"	% "2.3.13"	% "test"	exclude("org.scala-lang", "scala-library")
)

scalacOptions	++= Seq(
	"-deprecation",
	"-unchecked",
	// "-language:implicitConversions",
	// "-language:existentials",
	// "-language:higherKinds",
	// "-language:reflectiveCalls",
	// "-language:dynamics",
	"-language:postfixOps",
	// "-language:experimental.macros"
	"-feature"
)
