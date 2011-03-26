package scgeom

import java.awt.Insets

import scala.math._

object SgRectangleInsets {
	val zero	= SgRectangleInsets(SgSpanInsets.zero, SgSpanInsets.zero)
	
	def fromInsets(value:Insets):SgRectangleInsets	= SgRectangleInsets(
			SgSpanInsets(value.left, value.right),
			SgSpanInsets(value.top, value.bottom))
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
	
	/*
	def inflate(x:SgSpanInsets, y:SgSpanInsets):SgRectangleInsets	= SgRectangleInsets(
			this.x inflate x,
			this.y inflate y)
			
	def inflate(offset:Double):SgRectangleInsets	= SgRectangleInsets(
			top		+ offset, 
			left	+ offset, 
			bottom	+ offset, 
			right	+ offset)
	*/
	
	def toInsets:Insets	= new Insets(
			round(top).toInt,
			round(left).toInt,
			round(bottom).toInt,
			round(right).toInt)
}
