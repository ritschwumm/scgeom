package scgeom

object SgSpan {
	//------------------------------------------------------------------------------
	//## simple values
	
	val zero	= SgSpan(0, 0)
	val one		= SgSpan(0, 1)
	
	//------------------------------------------------------------------------------
	//## component factory
	
	def startZeroBy(size:Double):SgSpan	=
			SgSpan(0, size)
		
	def endZeroBy(size:Double):SgSpan	=
			SgSpan(-size, 0)
		
	def startBy(start:Double, size:Double):SgSpan	=
			SgSpan(start, start+size)
		
	def endBy(end:Double, size:Double):SgSpan		=
			SgSpan(end-size, end)
		
	def centerBy(center:Double, size:Double):SgSpan	=
			SgSpan(center-size/2, center+size/2)
	
	//------------------------------------------------------------------------------
	//## extreme factory
	
	def extremeTo(extreme:SgExtreme, master:Double, slave:Double):SgSpan	=
			extreme match {
				case SgStart	=> SgSpan(master, slave)
				case SgEnd		=> SgSpan(slave, master)
			}
			
	def extremeBy(extreme:SgExtreme, point:Double, size:Double):SgSpan	=
			extreme match {
				case SgStart	=> startBy(point, size)
				case SgEnd		=> endBy(point, size)
			}
}

final case class SgSpan(start:Double, end:Double) {
	def empty:Boolean	= start == end
	def normal:Boolean	= start <= end
	def size:Double		= end - start
	def min:Double		= start min end
	def max:Double		= start max end
	def center:Double	= (start + end) / 2
	
	def swap:SgSpan	= SgSpan(end, start)
	
	def normalize:SgSpan	= if (normal) this else swap
	
	def union(that:SgSpan):SgSpan	=
			SgSpan(
				this.min min that.min,
				this.max max that.max
			)
			
	def intersect(that:SgSpan):Option[SgSpan]	=
				 if (this.empty || that.empty)							None
			else if (this.end	<= that.start)							None
			else if (this.start	>= that.end)							None
			else if (this.start	<= that.start && this.end >= that.end)	Some(that)
			else if (this.start	>= that.start && this.end <= that.end)	Some(this)
			else if (this.start	<= that.start && this.end <= that.end)	Some(SgSpan(that.start, this.end))
			else														Some(SgSpan(this.start, that.end))
			
	// TODO should this normalize?
	def containsValue(pos:Double) = {
		val	normal	= normalize
		pos >= normal.start && pos < normal.end
	}
	
	// TODO should this normalize?
	def contains(that:SgSpan):Boolean	= {
		val thisNormal	= this.normalize
		val thatNormal	= this.normalize
		thatNormal.start	>=	thisNormal.start	&&
		thatNormal.start	<	thisNormal.end		&&
		thatNormal.end		<=	thisNormal.end		&&
		thatNormal.end		>	thisNormal.start
	}
	
	// TODO should this normalize?
	def intersects(that:SgSpan):Boolean	= {
		if (this.empty || that.empty) {
			false
		}
		else {
			val thisNormal	= this.normalize
			val thatNormal	= that.normalize
			thatNormal.start < thisNormal.end && thatNormal.end > thisNormal.start
		}
	}		
		
	def inset(insets:SgSpanInsets):SgSpan	=
			SgSpan(
				start	+ insets.start,
				end		- insets.end
			)
			
	def move(offset:Double):SgSpan	=
			SgSpan(
				start	+ offset,
				end		+ offset
			)
			
	def splitAt(position:Double):(SgSpan, SgSpan)	=
			(
				SgSpan(start, position),
				SgSpan(position, end)
			)
			
	def splitStartBy(size:Double):(SgSpan, SgSpan)	=
			splitAt(start + size)
			
	def splitEndBy(size:Double):(SgSpan, SgSpan)	=
			splitAt(end - size)
			
	//------------------------------------------------------------------------------
	//## more span dsl
	
	def previous(size:Double):SgSpan	= SgSpan(start - size,	start)
	def next(size:Double):SgSpan		= SgSpan(end,			end   + size)
	def starting(size:Double):SgSpan	= SgSpan(start,			start + size)
	def ending(size:Double):SgSpan		= SgSpan(end   - size,	end)
	
	//------------------------------------------------------------------------------
	//## factory dsl
	
	def lineWith(that:SgSpan):SgLine	=
			SgLine(
				SgPoint(this.start, that.start),
				SgPoint(this.end, that.end)
			)
			
	def rectangleWith(that:SgSpan):SgRectangle	=
			SgRectangle(this, that)
		
	def spanTransformTo(that:SgSpan):SgSpanTransform	=
			SgSpanTransform fromSpans (this, that)
	
	//------------------------------------------------------------------------------
	//## extreme lens
	
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
