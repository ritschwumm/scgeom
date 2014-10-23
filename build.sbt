name			:= "scgeom"

organization	:= "de.djini"

version			:= "0.24.0"

scalaVersion	:= "2.11.2"

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

conflictManager	:= ConflictManager.strict

resolvers		+= "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"

libraryDependencies	++= Seq(
	"org.specs2"	%%	"specs2"	% "2.4.6"	% "test"
)

dependencyOverrides	++= Set(
	"org.scala-lang"	% "scala-library"	% scalaVersion.value,
	"org.scala-lang"	% "scala-reflect"	% scalaVersion.value
)
