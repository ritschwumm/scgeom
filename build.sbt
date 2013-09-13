name			:= "scgeom"

organization	:= "de.djini"

version			:= "0.15.0"

scalaVersion	:= "2.10.2"

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
