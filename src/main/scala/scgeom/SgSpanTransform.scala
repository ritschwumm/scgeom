package scgeom

object SgSpanTransform {
	val identity	= SgSpanTransform(SgSpan.one, SgSpan.one)
}
	
case class SgSpanTransform(from:SgSpan, to:SgSpan) {
	def apply(span:SgSpan):SgSpan	= SgSpan(
			apply(span.start),
			apply(span.end))
	
	def apply(value:Double):Double	= scale(value - from.start) + to.start
	def scale(value:Double):Double	= value * to.size / from.size
	
	def inverse:SgSpanTransform	= SgSpanTransform(to, from)
	
	def swap:SgSpanTransform	= SgSpanTransform(from.swap, to)
}
