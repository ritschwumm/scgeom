package scgeom

object SgSpan {
	val zero	= SgSpan(0,0)
	
	def fromStartSize(start:Double, size:Double):SgSpan	= SgSpan(start, start+size)
	def fromEndSize(end:Double, size:Double):SgSpan		= SgSpan(end-size, end)
}
case class SgSpan(start:Double, end:Double) {
	def empty:Boolean	= start == end
	def size:Double		= end - start
	def min:Double		= start min end
	def max:Double		= start max end
	def center:Double	= (start + end) / 2
	
	def normalize:SgSpan	= 
			if (start > end)	SgSpan(end, start) else this
			//SgSpan(min, max)
	
	def union(that:SgSpan):SgSpan	= SgSpan(
			this.min min that.min, 
			this.max max that.max)
			
	def intersect(that:SgSpan):SgSpan	= SgSpan(
			this.min max that.min,
			this.max min that.max)
	
	def contains(pos:Double) = {
		val	normal	= normalize
		pos >= normal.start && pos < normal.end
	}
	
	def contains(that:SgSpan):Boolean	= {
		val thisNormal	= this.normalize
		val thatNormal	= this.normalize
		thatNormal.start	>=	thisNormal.start	&&
		thatNormal.start	<	thisNormal.end		&&
		thatNormal.end		<=	thisNormal.end		&&
		thatNormal.end		>	thisNormal.start
	}
		
	def intersects(that:SgSpan):Boolean	= {
		val thisNormal	= this.normalize
		val thatNormal	= this.normalize
		thatNormal.start < thisNormal.end && thatNormal.end > thatNormal.start
	}		
		
	def inset(start:Double, end:Double):SgSpan	= SgSpan(
			this.start + start,
			this.end   - end)
			
	def move(offset:Double):SgSpan	= SgSpan(
			start	+ offset, 
			end		+ offset)
			
	def inflate(offset:Double):SgSpan	= SgSpan(
			start	- offset, 
			end		+ offset)
			
	def inset(insets:SgSpanInsets):SgSpan	= SgSpan(
			start	+ insets.start,
			end		- insets.end)
}
