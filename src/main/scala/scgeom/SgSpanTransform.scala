package scgeom

object SgSpanTransform {
	val identity	= factorSummand(1.0, 0.0)

	//------------------------------------------------------------------------------
	//## component factory

	def factorSummand(factor:Double, summand:Double):SgSpanTransform	= new SgSpanTransform(factor, summand)

	def fromTo(from:SgSpan, to:SgSpan):SgSpanTransform	= {
		val factor	= to.size / from.size
		val summand	= to.start - from.start * factor
		factorSummand(factor, summand)
	}
}

final case class SgSpanTransform private (factor:Double, summand:Double) {
	def inverse:SgSpanTransform	=
		SgSpanTransform.factorSummand(
			1			/ factor,
			-summand	/ factor
		)

	//------------------------------------------------------------------------------

	def apply(value:Double):Double		= transform(value)

	def transform(value:Double):Double	= value * factor + summand
	def scale(value:Double):Double		= value * factor
	def offset(value:Double):Double		= value + summand

	def transformSpan(value:SgSpan):SgSpan	=
		SgSpan.startEnd(
			transform(value.start),
			transform(value.end)
		)

	//------------------------------------------------------------------------------
	//## factory dsl

	def rectangleTransformWith(y:SgSpanTransform):SgRectangleTransform	=
		SgRectangleTransform.fromSpanTransforms(this, y)
}
