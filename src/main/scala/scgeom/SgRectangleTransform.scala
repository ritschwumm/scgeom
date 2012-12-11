package scgeom

object SgRectangleTransform {
	val identity	= SgRectangleTransform(SgPoint.one, SgPoint.zero)
	
	def fromRectangles(from:SgRectangle, to:SgRectangle):SgRectangleTransform	=
			fromSpanTransforms(
					SgSpanTransform fromSpans (from.x, to.x),
					SgSpanTransform fromSpans (from.y, to.y))
			
	def fromSpanTransforms(x:SgSpanTransform, y:SgSpanTransform):SgRectangleTransform	=
			SgRectangleTransform(
					SgPoint(x.factor,y.factor), 
					SgPoint(x.summand, y.summand))
}
	
case class SgRectangleTransform(factor:SgPoint, summand:SgPoint) {
	def x:SgSpanTransform	= SgSpanTransform(factor.x, summand.x)
	def y:SgSpanTransform	= SgSpanTransform(factor.y, summand.y)
	
	def inverse:SgRectangleTransform	= 
			SgRectangleTransform(
					factor.mulInverse, 
					-(summand descale factor))
					
	//------------------------------------------------------------------------------
	
	def apply(value:SgPoint):SgPoint	= transform(value)
	
	def transform(value:SgPoint):SgPoint	= (value scale factor) + summand
	def scale(value:SgPoint):SgPoint		= value scale factor
	def offset(value:SgPoint):SgPoint		= value + summand
	
	def transformLine(value:SgLine):SgLine	=
			SgLine(
					transform(value.start), 
					transform(value.end))
		
	def transformRectangle(value:SgRectangle):SgRectangle	=
			SgRectangle(
					x transformSpan value.x,
					y transformSpan value.y)
}
