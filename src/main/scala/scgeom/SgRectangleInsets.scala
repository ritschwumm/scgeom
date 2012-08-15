package scgeom

import java.awt.Insets

import scala.math._

object SgRectangleInsets {
	val zero	= SgRectangleInsets(SgSpanInsets.zero,	SgSpanInsets.zero)
	val one		= SgRectangleInsets(SgSpanInsets.one,	SgSpanInsets.one)
	
	def fromInsets(value:Insets):SgRectangleInsets	= SgRectangleInsets(
			SgSpanInsets(value.left, value.right),
			SgSpanInsets(value.top, value.bottom))
			
	def symmetric(size:SgSpanInsets):SgRectangleInsets	= SgRectangleInsets(size, size)
	
	def symmetric(size:Double):SgRectangleInsets		= SgRectangleInsets(
			SgSpanInsets symmetric size, 
			SgSpanInsets symmetric size)
}

case class SgRectangleInsets(x:SgSpanInsets, y:SgSpanInsets) {
	def top:Double		= x.start
	def bottom:Double	= x.end
	def left:Double		= y.start
	def right:Double	= y.end
	
	def empty:Boolean	= x.empty && y.empty
	def size:SgPoint	= SgPoint(
			x.size,
			y.size)
		
	def swap:SgRectangleInsets	= SgRectangleInsets(y, x)
	
	def inverse:SgRectangleInsets	= SgRectangleInsets(
			x.inverse, 
			y.inverse)
			
	def +(that:SgRectangleInsets):SgRectangleInsets	= SgRectangleInsets(
			this.x	+ that.x,
			this.y	+ that.y)
	
	def -(that:SgRectangleInsets):SgRectangleInsets	= SgRectangleInsets(
			this.x	- that.x,
			this.y	- that.y)
			
	def *(scale:Double):SgRectangleInsets	= SgRectangleInsets(
			x	* scale,
			y	* scale)
			
	def /(scale:Double):SgRectangleInsets	= SgRectangleInsets(
			x	/ scale,
			y	/ scale)
	
	def toInsets:Insets	= new Insets(
			round(top).toInt,
			round(left).toInt,
			round(bottom).toInt,
			round(right).toInt)
}
