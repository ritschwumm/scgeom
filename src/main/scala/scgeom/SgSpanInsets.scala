package scgeom

object SgSpanInsets {
	//------------------------------------------------------------------------------
	//## simple values
	
	val zero	= symmetric(0)
	val one		= symmetric(1)
	
	//------------------------------------------------------------------------------
	//## component factory
	
	def symmetric(size:Double):SgSpanInsets	=
			SgSpanInsets(size, size)
	
	//------------------------------------------------------------------------------
	//## extreme factory
	
	def extremeBy(extreme:SgExtreme, master:Double, slave:Double):SgSpan	=
			extreme match {
				case SgStart	=> SgSpan(master, slave)
				case SgEnd		=> SgSpan(slave, master)
			}
}

final case class SgSpanInsets(start:Double, end:Double) {
	def empty:Boolean	= start == 0 && end == 0
	def size:Double		= start + end
	
	def inverse:SgSpanInsets	= SgSpanInsets(-start, -end)
	def swap:SgSpanInsets		= SgSpanInsets(end, start)
			
	def +(that:SgSpanInsets):SgSpanInsets	=
			SgSpanInsets(
				this.start	+ that.start,
				this.end	+ that.end
			)
	
	def -(that:SgSpanInsets):SgSpanInsets	=
			SgSpanInsets(
				this.start	- that.start,
				this.end	- that.end
			)
			
	def *(scale:Double):SgSpanInsets	=
			SgSpanInsets(
				start	* scale,
				end		* scale
			)
			
	def /(scale:Double):SgSpanInsets	=
			SgSpanInsets(
				start	/ scale,
				end		/ scale
			)
			
	//------------------------------------------------------------------------------
	//## factory dsl
	
	def rectangleInsetsWith(that:SgSpanInsets):SgRectangleInsets	=
			SgRectangleInsets(this, that)
	
	//------------------------------------------------------------------------------
	//## extreme lens
	
	def get(extreme:SgExtreme):Double	=
			extreme match {
				case SgStart	=> start
				case SgEnd		=> end
			}
	
	def set(extreme:SgExtreme, it:Double):SgSpanInsets	=
			extreme match {
				case SgStart	=> SgSpanInsets(it, end)
				case SgEnd		=> SgSpanInsets(start, it)
			}
	
	def modify(extreme:SgExtreme, it:Double=>Double):SgSpanInsets	=
			extreme match {
				case SgStart	=> SgSpanInsets(it(start), end)
				case SgEnd		=> SgSpanInsets(start, it(end))
			}
}
