package scgeom

import java.awt.Insets

import scala.math._

object SgSpanInsets {
	val zero	= symmetric(0)
	val one		= symmetric(1)
	
	def symmetric(size:Double):SgSpanInsets	= SgSpanInsets(size, size)
	
	def fromExtreme(extreme:SgExtreme, master:Double, slave:Double):SgSpan	=
			extreme match {
				case SgStart	=> SgSpan(master, slave)
				case SgEnd		=> SgSpan(slave, master)
			}
}

case class SgSpanInsets(start:Double, end:Double) {
	def empty:Boolean	= start == 0 && end == 0
	
	def size:Double	= start + end
	def inverse:SgSpanInsets	= SgSpanInsets(
			-start,
			-end)
	
	def swap:SgSpanInsets	= SgSpanInsets(end, start)
			
	def +(that:SgSpanInsets):SgSpanInsets	= SgSpanInsets(
			this.start	+ that.start,
			this.end	+ that.end)
	
	def -(that:SgSpanInsets):SgSpanInsets	= SgSpanInsets(
			this.start	- that.start,
			this.end	- that.end)
			
	def *(scale:Double):SgSpanInsets	= SgSpanInsets(
			start	* scale,
			end		* scale)
			
	def /(scale:Double):SgSpanInsets	= SgSpanInsets(
			start	/ scale,
			end		/ scale)
			
	def rectangleInsetsTo(that:SgSpanInsets):SgRectangleInsets	= SgRectangleInsets(this, that)
	
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
