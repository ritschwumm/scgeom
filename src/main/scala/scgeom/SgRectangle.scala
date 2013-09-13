package scgeom

import java.awt.geom.{ Rectangle2D }

object SgRectangle {
	//------------------------------------------------------------------------------
	//## simple values
	
	val zero	= SgRectangle(SgSpan.zero,	SgSpan.zero)
	val one		= SgRectangle(SgSpan.one,	SgSpan.one)
	
	//------------------------------------------------------------------------------
	//## component factory
	
	def topLeftZeroBy(size:SgPoint):SgRectangle	= SgRectangle(
			SgSpan startZeroBy size.x, 
			SgSpan startZeroBy size.y)
		
	def topLeftBy(pos:SgPoint, size:SgPoint):SgRectangle	= SgRectangle(
			SgSpan(pos.x, pos.x+size.x),
			SgSpan(pos.y, pos.y+size.y))
			
	def topLeftTo(pos:SgPoint, other:SgPoint):SgRectangle	= SgRectangle(
			SgSpan(pos.x, other.x),
			SgSpan(pos.y, other.y))
			
	def centerBy(center:SgPoint, size:SgPoint):SgRectangle	= SgRectangle(
			SgSpan(center.x-size.x/2, center.x+size.x/2),
			SgSpan(center.y-size.y/2, center.y+size.y/2))
			
	//------------------------------------------------------------------------------
	//## orientation factory
	
	def orientationWith(orientation:SgOrientation, master:SgSpan, slave:SgSpan):SgRectangle	=
			orientation match {
				case SgHorizontal	=> SgRectangle(master,	slave)
				case SgVertical		=> SgRectangle(slave,	master)
			}
			
	//------------------------------------------------------------------------------
	//## awt conversion
	
	def fromAwtRectangle2D(it:Rectangle2D):SgRectangle	= 
			SgRectangle(
					SgSpan(it.getX, it.getX+it.getWidth),
					SgSpan(it.getY, it.getY+it.getHeight))
			
	def toAwtRectangle2D(it:SgRectangle):Rectangle2D	= 
			it.toAwtRectangle2D
}

case class SgRectangle(x:SgSpan, y:SgSpan) {
	def top:Double		= y.start
	def left:Double		= x.start
	def bottom:Double	= y.end
	def right:Double	= x.end
	
	def topLeft:SgPoint			= SgPoint(x.start,	y.start)
	def topRight:SgPoint		= SgPoint(x.end,	y.start)
	def bottomLeft:SgPoint		= SgPoint(x.start,	y.end)
	def bottomRight:SgPoint		= SgPoint(x.end,	y.end)
	
	def center:SgPoint			= SgPoint(x.center, y.center)
	def topCenter:SgPoint		= SgPoint(x.center, y.start)
	def bottomCenter:SgPoint	= SgPoint(x.center, y.end)
	def leftCenter:SgPoint		= SgPoint(x.start,	y.center)
	def rightCenter:SgPoint		= SgPoint(x.end,	y.center)
	
	def topLine:SgLine		= SgLine horizontal	(x,		top)
	def bottomLine:SgLine	= SgLine horizontal	(x,		bottom)
	def leftLine:SgLine		= SgLine vertical	(left,	y)
	def rightLine:SgLine	= SgLine vertical	(right,	y)
	
	def diagonal1:SgLine	= SgLine(topLeft,	bottomRight)
	def diagonal2:SgLine	= SgLine(topRight,	bottomLeft)
	
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
			
	def containsPoint(point:SgPoint) =
			(x containsValue point.x)	&&
			(y containsValue point.y)
	
	def contains(that:SgRectangle):Boolean	=
			(this.x contains that.x) &&
			(this.y contains that.y)
	
	def move(offset:SgPoint):SgRectangle	= SgRectangle(
			x move offset.x, 
			y move offset.y)
			
	def inset(insets:SgRectangleInsets):SgRectangle	= SgRectangle(
			x inset insets.x,
			y inset insets.y)
	
	def splitAtX(position:Double):(SgRectangle, SgRectangle)	= {
		val (a, b)	= x splitAt position
		(
			SgRectangle(a, y),
			SgRectangle(b, y)
		)
	}
			
	def splitAtY(position:Double):(SgRectangle, SgRectangle)	= {
		val (a, b)	= y splitAt position
		(
			SgRectangle(x, a),
			SgRectangle(x, b)
		)
	}
			
	def splitAtOrientation(position:Double, orientation:SgOrientation):(SgRectangle, SgRectangle)	=
			orientation cata (
				splitAtX(position),
				splitAtY(position)
			)
			
	def splitAt(position:SgPoint):(SgRectangle, SgRectangle, SgRectangle, SgRectangle)	= {
		val (ax, bx)	= x splitAt position.x
		val (ay, by)	= y splitAt position.y
		(
			SgRectangle(ax, ay),
			SgRectangle(bx, ay),
			SgRectangle(ax, by),
			SgRectangle(bx, by)
		)
	}
			
	//------------------------------------------------------------------------------
	//## factory dsl
	
	def rectangleTransformTo(that:SgRectangle):SgRectangleTransform	=
			SgRectangleTransform fromRectangles (this, that)
		
	def affineTransformTo(that:SgRectangle):SgAffineTransform	=
			rectangleTransformTo(that).toAffineTransform
			
	//------------------------------------------------------------------------------
	//## orientation lens
	
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
			
	//------------------------------------------------------------------------------
	//## awt conversion
	
	def toAwtRectangle2D:Rectangle2D	= new Rectangle2D.Double(
			x.start, y.start, 
			x.size, y.size)
}
