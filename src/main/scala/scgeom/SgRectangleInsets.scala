package scgeom

import java.awt.Insets

import scala.math._

object SgRectangleInsets {
	val zero	= symmetric(SgSpanInsets.zero)
	val one		= symmetric(SgSpanInsets.one)
	
	def symmetric(size:SgSpanInsets):SgRectangleInsets	= SgRectangleInsets(size, size)
	
	def symmetric(size:Double):SgRectangleInsets		= SgRectangleInsets(
			SgSpanInsets symmetric size, 
			SgSpanInsets symmetric size)
			
	def fromOrientation(orientation:SgOrientation, master:SgSpanInsets, slave:SgSpanInsets):SgRectangleInsets	=
			orientation match {
				case SgHorizontal	=> SgRectangleInsets(master, slave)
				case SgVertical		=> SgRectangleInsets(slave, master)
			}
			
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
	
	def get(orientation:SgOrientation):SgSpanInsets	= 
			orientation match {
				case SgHorizontal	=> x
				case SgVertical		=> y
			}
	
	def set(orientation:SgOrientation, it:SgSpanInsets):SgRectangleInsets	=
			orientation match {
				case SgHorizontal	=> SgRectangleInsets(it, y)
				case SgVertical		=> SgRectangleInsets(x, it)
			}
	
	def modify(orientation:SgOrientation, it:SgSpanInsets=>SgSpanInsets):SgRectangleInsets	=
			orientation match {
				case SgHorizontal	=> SgRectangleInsets(it(x), y)
				case SgVertical		=> SgRectangleInsets(x, it(y))
			}
			
	def toInsets:Insets	= new Insets(
			round(top).toInt,
			round(left).toInt,
			round(bottom).toInt,
			round(right).toInt)
}
