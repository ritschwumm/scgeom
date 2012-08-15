package scgeom

object SgSpan {
	val zero	= SgSpan(0,0)
	val one		= SgSpan(0,1)
	
	def fromStartSize(start:Double, size:Double):SgSpan	= SgSpan(start, start+size)
	def fromEndSize(end:Double, size:Double):SgSpan		= SgSpan(end-size, end)
	
	def fromExtreme(extreme:SgExtreme, master:Double, slave:Double):SgSpan	=
			extreme match {
				case SgStart	=> SgSpan(master, slave)
				case SgEnd		=> SgSpan(slave, master)
			}
	def fromExtremeSize(extreme:SgExtreme, point:Double, size:Double):SgSpan	=
			extreme match {
				case SgStart	=> fromStartSize(point, size)
				case SgEnd		=> fromEndSize(point, size)
			}
}

case class SgSpan(start:Double, end:Double) {
	def empty:Boolean	= start == end
	def normal:Boolean	= start <= end
	def size:Double		= end - start
	def min:Double		= start min end
	def max:Double		= start max end
	def center:Double	= (start + end) / 2
	
	def swap:SgSpan	= SgSpan(end, start)
	
	def normalize:SgSpan	= if (normal) this else swap
	
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
		
	def inset(insets:SgSpanInsets):SgSpan	= SgSpan(
			start	+ insets.start,
			end		- insets.end)
			
	def move(offset:Double):SgSpan	= SgSpan(
			start	+ offset, 
			end		+ offset)
			
	def rectangleTo(that:SgSpan):SgRectangle	= SgRectangle(this, that)
	
	def get(extreme:SgExtreme):Double	= 
			extreme match {
				case SgStart	=> start
				case SgEnd		=> end
			}
	
	def set(extreme:SgExtreme, it:Double):SgSpan	=
			extreme match {
				case SgStart	=> SgSpan(it, end)
				case SgEnd		=> SgSpan(start, it)
			}
	
	def modify(extreme:SgExtreme, it:Double=>Double):SgSpan	=
			extreme match {
				case SgStart	=> SgSpan(it(start), end)
				case SgEnd		=> SgSpan(start, it(end))
			}
}
