package scgeom

import java.awt.geom.{ Rectangle2D }

object SgRectangle {
	val zero	= SgRectangle(SgSpan.zero,	SgSpan.zero)
	val one		= SgRectangle(SgSpan.one,	SgSpan.one)
	
	def fromOrientation(orientation:SgOrientation, master:SgSpan, slave:SgSpan):SgRectangle	=
			orientation match {
				case SgHorizontal	=> SgRectangle(master, slave)
				case SgVertical		=> SgRectangle(slave, master)
			}
	
	def fromPosSize(pos:SgPoint, size:SgPoint):SgRectangle	= SgRectangle(
			SgSpan(pos.x, pos.x+size.x),
			SgSpan(pos.y, pos.y+size.y))
			
	def fromRectangle2D(value:Rectangle2D):SgRectangle		= fromPosSize(
			SgPoint(value.getX, value.getY), 
			SgPoint(value.getWidth, value.getHeight))
}

case class SgRectangle(x:SgSpan, y:SgSpan) {
	def topLeft:SgPoint		= SgPoint(x.start, y.start)
	def topRight:SgPoint	= SgPoint(x.end, y.start)
	def bottomLeft:SgPoint	= SgPoint(x.start, y.end)
	def bottomRight:SgPoint	= SgPoint(x.end, y.end)
	def center:SgPoint		= SgPoint(x.center, y.center)
	
	def empty:Boolean		= x.empty || y.empty
	def normal:Boolean		= x.normal && y.normal
	def size:SgPoint		= SgPoint(x.size, y.size)
	
	def swap:SgRectangle	= SgRectangle(x,y)
	
	def normalize:SgRectangle	= SgRectangle(
			x.normalize,
			y.normalize)
			
	def union(that:SgRectangle):SgRectangle	= SgRectangle(
			this.x union that.x,
			this.y union that.y)
			
	def intersect(that:SgRectangle):SgRectangle	= SgRectangle(
			this.x intersect that.x,
			this.y intersect that.y)
			
	def intersects(that:SgRectangle):Boolean	=
			(this.x intersects that.x) &&
			(this.y intersects that.y)
			
	def contains(that:SgRectangle):Boolean	=
			(this.x contains that.x) &&
			(this.y contains that.y)
	
	def move(offset:SgPoint):SgRectangle	= SgRectangle(
			x move offset.x, 
			y move offset.y)
			
	def inset(insets:SgRectangleInsets):SgRectangle	= SgRectangle(
			x inset insets.x,
			y inset insets.y)
	
	def get(orientation:SgOrientation):SgSpan	= 
			orientation match {
				case SgHorizontal	=> x
				case SgVertical		=> y
			}
	
	def set(orientation:SgOrientation, it:SgSpan):SgRectangle	=
			orientation match {
				case SgHorizontal	=> SgRectangle(it, y)
				case SgVertical		=> SgRectangle(x, it)
			}
	
	def modify(orientation:SgOrientation, it:SgSpan=>SgSpan):SgRectangle	=
			orientation match {
				case SgHorizontal	=> SgRectangle(it(x), y)
				case SgVertical		=> SgRectangle(x, it(y))
			}
			
	def toRectangle2D:Rectangle2D	= new Rectangle2D.Double(
			x.start, y.start, 
			x.size, y.size)
}
