package scgeom

import java.awt.geom.{ Rectangle2D }

object SgRectangle {
	val zero	= SgRectangle(SgSpan.zero, SgSpan.zero)
	
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
	
	def empty:Boolean		= x.empty && y.empty
	def size:SgPoint		= SgPoint(x.size, y.size)
	
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
	
	def toRectangle2D:Rectangle2D	= new Rectangle2D.Double(
			x.start, y.start, 
			x.size, y.size)
}
