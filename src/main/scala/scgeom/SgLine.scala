package scgeom

import java.awt.geom.{ Line2D }

object SgLine {
	val zero	= SgLine(SgPoint.zero, SgPoint.zero)
	
	def fromStartSize(start:SgPoint, size:SgPoint):SgLine	= SgLine(start, start+size)
	def fromEndSize(end:SgPoint, size:SgPoint):SgLine		= SgLine(end-size, end)
	
	def fromLine2D(value:Line2D):SgLine	= SgLine(SgPoint(value.getX1, value.getY1), SgPoint(value.getX2, value.getY2))
}
case class SgLine(start:SgPoint, end:SgPoint) {
	def size:SgPoint	= end - start
	def spanX:SgSpan	= SgSpan(start.x, end.x)
	def spanY:SgSpan	= SgSpan(start.y, end.y)
	
	def move(offset:SgPoint):SgLine = SgLine(
			start+offset, 
			end+offset)
	
	def toLine2D:Line2D	= new Line2D.Double(
			start.x, start.y, 
			end.x, end.y)
}
