package scgeom

import java.awt.Insets

import scala.math._

object SgSpanInsets {
	val zero	= SgSpanInsets(0,0)
}
case class SgSpanInsets(start:Double, end:Double) {
	def empty:Boolean	= start == 0 && end == 0
	
	def size:Double	= start + end
	def inverse:SgSpanInsets	= SgSpanInsets(
			-start,
			-end)
			
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
	/*
	def inflate(both:Double):SgSpanInsets	= SgSpanInsets(
			start	+ both,
			end	+ both)
			
	def inflate(start:Double, end:Double):SgSpanInsets	= SgSpanInsets(
			this.start	+ start,
			this.end	+ end)
	*/
}
