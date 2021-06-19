package object scgeom extends extensions {
	@deprecated("use SgLinearTransform1D", "0.50.0")
	val SgSpanTransform		= SgLinearTransform1D
	@deprecated("use SgLinearTransform1D", "0.50.0")
	type SgSpanTransform	= SgLinearTransform1D

	@deprecated("use SgLinearTransform2D", "0.50.0")
	val SgRectangleTransform		= SgLinearTransform2D
	@deprecated("use SgLinearTransform2D", "0.50.0")
	type SgRectangleTransform		= SgLinearTransform2D
}
