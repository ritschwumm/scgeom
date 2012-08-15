package scgeom

import java.awt.Insets

import scala.math._

object SgSpanInsets {
	val zero	= SgSpanInsets(0,0)
	val one		= SgSpanInsets(1,1)
	
	def symmetric(size:Double):SgSpanInsets	= SgSpanInsets(size, size)
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
}
