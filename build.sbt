name			:= "scgeom"

organization	:= "de.djini"

version			:= "0.23.0"

scalaVersion	:= "2.11.3"

resolvers		+= "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"

libraryDependencies	++= Seq(
	"org.specs2"	%%	"specs2"	% "2.4.6"	% "test"
)

dependencyOverrides	in ThisBuild	++= Set(
	"org.scala-lang"	% "scala-library"	% scalaVersion.value
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
