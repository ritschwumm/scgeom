package scgeom

import java.awt.geom.{ Point2D, Dimension2D }

object SgPoint {
	val zero	= SgPoint(0,0)
	val one		= SgPoint(1,1)
	
	def symmetric(value:Double)	= SgPoint(value, value)
	
	def fromOrientation(orientation:SgOrientation, master:Double, slave:Double):SgPoint	=
			orientation match {
				case SgHorizontal	=> SgPoint(master,	slave)
				case SgVertical		=> SgPoint(slave,	master)
			}
			
	def fromPair(value:Pair[Double,Double]):SgPoint	=
			SgPoint(value._1, value._2)
	
	def fromPoint2D(value:Point2D):SgPoint	= 
			SgPoint(value.getX, value.getY)
		
	def fromDimension2D(value:Dimension2D):SgPoint	=
			SgPoint(value.getWidth, value.getHeight)
}

case class SgPoint(x:Double, y:Double) {
	import scala.math._
	
	def unary_-():SgPoint	= SgPoint(-x, -y)
	def swap:SgPoint		= SgPoint(y,x)
	
	def addInverse:SgPoint	= SgPoint(-x, -y)
	def mulInverse:SgPoint	= SgPoint(1/x, 1/y)
	
	def +(that:SgPoint):SgPoint	= SgPoint(this.x+that.x, this.y+that.y)
	def -(that:SgPoint):SgPoint	= SgPoint(this.x-that.x, this.y-that.y)
	
	def *(value:Double):SgPoint	= SgPoint(x*value, y*value)
	def /(value:Double):SgPoint	= SgPoint(x/value, y/value)
	
	def scale(that:SgPoint):SgPoint		= SgPoint(this.x*that.x, this.y*that.y)
	def descale(that:SgPoint):SgPoint	= SgPoint(this.x/that.x, this.y/that.y)
	
	def signum:SgPoint	= SgPoint(scala.math.signum(this.x), scala.math.signum(this.y)) 
	
	def length:Double	= sqrt(lengthQ)
	def lengthQ:Double	= x*x + y*y
	
	def distance(that:SgPoint):Double	= (that - this).length
	def distanceQ(that:SgPoint):Double	= (that - this).lengthQ
	
	def angle:Double	= atan2(y, x)
	
	/*
	def normalize:SgPoint	= {
		val	len	= length
		if (abs(len) < 0.0001)	SgPoint.zero
		else					this / len
	}
	*/
	
	def rotate(angle:Double):SgPoint	= {
		val	s	= sin(angle)
		val	c	= cos(angle)
		val xx	= x * c - y * s
		val yy	= x * s + y * c
		SgPoint(xx, yy)
	}
	
	/*
	def shearX(value:Double)	= Kartesian(x + y * value, y)
	def shearY(value:Double)	= Kartesian(x, y + x * value)
	
	mirrorX:	function()			new Point(-this.x,  this.y),
	mirrorY:	function()			new Point( this.x, -this.y),
	
	// TODO extend
	// dot product
	dot:		function(that)		this.x * that.x + this.x * that.y + this.y * that.x + this.y * that.y,
	// cross product analog 1: magnitude (= z-coordinate, x and y will be 0) for the cross-product of two zero-z 3d vectors
	crossZ:		function(that)		this.x * that.y - this.y * that.x,
	// cross product analog 2: perpendicular vector
	crossG:		function()			Point(this.y, -this.x),
	*/
	
	def get(orientation:SgOrientation):Double	= 
			orientation match {
				case SgHorizontal	=> x
				case SgVertical		=> y
			}
	
	def set(orientation:SgOrientation, it:Double):SgPoint	=
			orientation match {
				case SgHorizontal	=> SgPoint(it, y)
				case SgVertical		=> SgPoint(x, it)
			}
	
	def modify(orientation:SgOrientation, it:Double=>Double):SgPoint	=
			orientation match {
				case SgHorizontal	=> SgPoint(it(x), y)
				case SgVertical		=> SgPoint(x, it(y))
			}
			
	def lineTo(that:SgPoint):SgLine				= SgLine(this, that)
	def lineBy(size:SgPoint):SgLine				= SgLine		fromStartSize	(this, size)
	def rectangleTo(that:SgPoint):SgRectangle	= SgRectangle	fromPosOther	(this, that)
	def rectangleBy(size:SgPoint):SgRectangle	= SgRectangle	fromPosSize		(this, size)
	
	def toPolar	= SgPolar(length, angle)
	
	def toPoint2D:Point2D			= new Point2D.Double(x,y)          
	def toDimension2D:Dimension2D	= new Dimension2D_Double(x,y)
	
	def toPair:Pair[Double,Double]	= Pair(x,y)
}
