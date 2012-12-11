package scgeom

import java.awt.geom.{ Line2D }

object SgLine {
	val zero	= SgLine(SgPoint.zero, SgPoint.zero)
	val one		= SgLine(SgPoint.zero, SgPoint.one)
	
	def horizontal(x:SgSpan, y:Double):SgLine	=
			SgLine(SgPoint(x.start, y), SgPoint(x.end, y))
		
	def vertical(x:Double, y:SgSpan):SgLine	=
			SgLine(SgPoint(x, y.start), SgPoint(x, y.end))
		
	def fromStartSize(start:SgPoint, size:SgPoint):SgLine	= 
			SgLine(start, start+size)
	
	def fromEndSize(end:SgPoint, size:SgPoint):SgLine		= 
			SgLine(end-size, end)
	
	def fromExtreme(extreme:SgExtreme, master:SgPoint, slave:SgPoint):SgLine	=
			extreme match {
				case SgStart	=> SgLine(master, slave)
				case SgEnd		=> SgLine(slave, master)
			}
			
	def fromExtremeSize(extreme:SgExtreme, point:SgPoint, size:SgPoint):SgLine	=
			extreme match {
				case SgStart	=> fromStartSize(point, size)
				case SgEnd		=> fromEndSize(point, size)
			}
	
	def fromLine2D(value:Line2D):SgLine	= SgLine(
			SgPoint(value.getX1, value.getY1), 
			SgPoint(value.getX2, value.getY2))
}

case class SgLine(start:SgPoint, end:SgPoint) {
	def x:SgSpan		= SgSpan(start.x, end.x)
	def y:SgSpan		= SgSpan(start.y, end.y)
	
	def size:SgPoint	= end - start
	
	def swap:SgLine		= SgLine(end, start)
	
	def move(offset:SgPoint):SgLine = SgLine(
			start	+ offset, 
			end		+ offset)
			
	// TODO
	// def intersect(that:SgLine):Option[SgPoint]
	// def intersectInside(that:SgLine):Option[SgPoint]
	
	def get(extreme:SgExtreme):SgPoint	= 
			extreme match {
				case SgStart	=> start
				case SgEnd		=> end
			}
	
	def set(extreme:SgExtreme, it:SgPoint):SgLine	=
			extreme match {
				case SgStart	=> SgLine(it, end)
				case SgEnd		=> SgLine(start, it)
			}
	
	def modify(extreme:SgExtreme, it:SgPoint=>SgPoint):SgLine	=
			extreme match {
				case SgStart	=> SgLine(it(start), end)
				case SgEnd		=> SgLine(start, it(end))
			}
	
	def toLine2D:Line2D	= new Line2D.Double(
			start.x, start.y, 
			end.x, end.y)
}
