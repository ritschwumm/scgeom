import java.awt.{ Insets }
import java.awt.geom.{ Point2D, Dimension2D, Rectangle2D, Line2D, Area, AffineTransform }

package object scgeom {
	implicit class DoubleSgExt(delegate:Double) {
		def pointWith(y:Double):SgPoint	= SgPoint(delegate, y)
		
		def spanTo(end:Double):SgSpan	= SgSpan(delegate, end)
		def spanStartBy(size:Double):SgSpan		= SgSpan startBy	(delegate, size)
		def spanEndBy(size:Double):SgSpan		= SgSpan endBy		(delegate, size)
		def spanCenterBy(size:Double):SgSpan	= SgSpan centerBy	(delegate, size)
		
		def spanInsetsWith(end:Double):SgSpanInsets	= SgSpanInsets(delegate, end)
	}
	
	implicit class BooleanSgExt(delegate:Boolean) {
		def trueHorizontal:SgOrientation	= SgOrientation trueHorizontal	delegate
		def trueVertical:SgOrientation		= SgOrientation trueVertical	delegate
		
		def trueStart:SgExtreme		= SgExtreme trueStart	delegate
		def trueEnd:SgExtreme		= SgExtreme trueEnd		delegate
	}
	
	//------------------------------------------------------------------------------
	
	implicit class InsetsSgExt(delegate:Insets) {
		def toSgRectangleInsets:SgRectangleInsets	= SgRectangleInsets fromAwtInsets delegate
	}
	
	implicit class AreaSgExt(delegate:Area) {
		def toSgArea:SgArea	= SgArea fromAwtArea delegate
	}
	
	implicit class Point2DSgExt(delegate:Point2D) {
		def toSgPoint:SgPoint	= SgPoint fromAwtPoint2D delegate
	}
	
	implicit class Dimension2DSgExt(delegate:Dimension2D) {
		def toSgPoint:SgPoint	= SgPoint fromAwtDimension2D delegate
	}
	
	implicit class Rectangle2DSgExt(delegate:Rectangle2D) {
		def toSgRectangle:SgRectangle	= SgRectangle fromAwtRectangle2D delegate
	}
	
	implicit class Line2DSgExt(delegate:Line2D) {
		def toSgLine:SgLine	= SgLine fromAwtLine2D delegate
	}
	
	implicit class AffineTransformSgExt(delegate:AffineTransform) {
		def toSgAffineTransform:SgAffineTransform	= SgAffineTransform fromAwtAffineTransform delegate
	}
}
