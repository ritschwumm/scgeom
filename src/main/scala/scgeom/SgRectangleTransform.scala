package scgeom

object SgRectangleTransform {
	//------------------------------------------------------------------------------
	//## simple values
	
	val identity	= factorSummand(SgPoint.one, SgPoint.zero)
	
	//------------------------------------------------------------------------------
	//## new factory
	
	@deprecated("use factorSummand", "0.40.0")
	def apply(factor:SgPoint, summand:SgPoint):SgRectangleTransform	=
			factorSummand(factor, summand)
	
	//------------------------------------------------------------------------------
	//##compoment factory
	
	def factorSummand(factor:SgPoint, summand:SgPoint):SgRectangleTransform	=
			new SgRectangleTransform(factor, summand)
	
	//------------------------------------------------------------------------------
	
	def fromRectangles(from:SgRectangle, to:SgRectangle):SgRectangleTransform	=
			fromSpanTransforms(
				SgSpanTransform fromTo (from.x, to.x),
				SgSpanTransform fromTo (from.y, to.y)
			)
			
	def fromSpanTransforms(x:SgSpanTransform, y:SgSpanTransform):SgRectangleTransform	=
			factorSummand(
				SgPoint(x.factor,	y.factor),
				SgPoint(x.summand,	y.summand)
			)
}
	
final case class SgRectangleTransform private (factor:SgPoint, summand:SgPoint) {
	def x:SgSpanTransform	= SgSpanTransform factorSummand (factor.x, summand.x)
	def y:SgSpanTransform	= SgSpanTransform factorSummand (factor.y, summand.y)
	
	def inverse:SgRectangleTransform	=
			SgRectangleTransform factorSummand (
				factor.mulInverse,
				-(summand descale factor)
			)
					
	//------------------------------------------------------------------------------
	
	def apply(value:SgPoint):SgPoint		= transform(value)
	
	def transform(value:SgPoint):SgPoint	= (value scale factor) + summand
	def scale(value:SgPoint):SgPoint		= value scale factor
	def offset(value:SgPoint):SgPoint		= value + summand
	
	def transformLine(value:SgLine):SgLine	=
			SgLine startEnd (
				transform(value.start),
				transform(value.end)
			)
		
	def transformRectangle(value:SgRectangle):SgRectangle	=
			SgRectangle xy (
				x transformSpan value.x,
				y transformSpan value.y
			)
					
	//------------------------------------------------------------------------------
	//## internal conversion
	
	def toAffineTransform:SgAffineTransform	=
			SgAffineTransform.identity translate summand scale factor
}
