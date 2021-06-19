Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / versionScheme := Some("early-semver")

name			:= "scgeom"
organization	:= "de.djini"
version			:= "0.50.0"

scalaVersion	:= "2.13.5"
scalacOptions	++= Seq(
	"-feature",
	"-deprecation",
	"-unchecked",
	"-Werror",
	"-Xlint",
)

conflictManager	:= ConflictManager.strict withOrganization "^(?!(org\\.scala-lang|org\\.scala-js)(\\..*)?)$"
libraryDependencies	++= Seq(
	"io.monix"	%% "minitest"	% "2.9.3"	% "test"
)

testFrameworks	+= new TestFramework("minitest.runner.Framework")

wartremoverErrors ++= Seq(
	Wart.AsInstanceOf,
	Wart.IsInstanceOf,
	Wart.StringPlusAny,
	Wart.ToString,
	Wart.EitherProjectionPartial,
	Wart.OptionPartial,
	Wart.TryPartial,
	Wart.Enumeration,
	Wart.FinalCaseClass,
	Wart.JavaConversions,
	Wart.Option2Iterable,
	Wart.JavaSerializable,
	//Wart.Any,
	Wart.AnyVal,
	//Wart.Nothing,
	Wart.ArrayEquals,
	Wart.ImplicitParameter,
	Wart.ExplicitImplicitTypes,
	Wart.LeakingSealed,
	Wart.DefaultArguments,
	Wart.Overloading,
	//Wart.PublicInference,
	Wart.TraversableOps,
)
