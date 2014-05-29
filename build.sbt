name			:= "scgeom"

organization	:= "de.djini"

version			:= "0.19.0"

scalaVersion	:= "2.10.4"

libraryDependencies	++= Seq(
	"org.specs2"	%%	"specs2"	% "2.3.10"	% "test"	exclude("org.scala-lang", "scala-library")
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
