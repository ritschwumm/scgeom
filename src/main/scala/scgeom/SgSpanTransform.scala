package scgeom

object SgSpanTransform {
	val identity	= SgSpanTransform(1.0, 0.0)
	
	def fromSpans(from:SgSpan, to:SgSpan):SgSpanTransform	= {
		val factor	= to.size / from.size
		val summand	= to.start - from.start * factor
		SgSpanTransform(factor, summand)
	}
}
	
final case class SgSpanTransform(factor:Double, summand:Double) {
	def inverse:SgSpanTransform	=
			SgSpanTransform(
					1/factor,
					-summand/factor)
		
	//------------------------------------------------------------------------------
	
	def apply(value:Double):Double		= transform(value)

	def transform(value:Double):Double	= value * factor + summand
	def scale(value:Double):Double		= value * factor
	def offset(value:Double):Double		= value + summand
	
	def transformSpan(value:SgSpan):SgSpan	=
			SgSpan(
					transform(value.start),
					transform(value.end))
					
	//------------------------------------------------------------------------------
	//## factory dsl
	
	def rectangleTransformWith(y:SgSpanTransform):SgRectangleTransform	=
			SgRectangleTransform fromSpanTransforms (this, y)
}
