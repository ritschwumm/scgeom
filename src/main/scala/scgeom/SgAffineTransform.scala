package scgeom

import java.awt.{ Shape }
import java.awt.geom.{ Point2D, AffineTransform, NoninvertibleTransformException }

import scala.util.control.Exception._

object SgAffineTransform {
	val identity:SgAffineTransform					= SgAffineTransform(new AffineTransform)
	def translate(point:SgPoint):SgAffineTransform	= SgAffineTransform(AffineTransform getTranslateInstance	(point.x, point.y))
	def scale(point:SgPoint):SgAffineTransform		= SgAffineTransform(AffineTransform getScaleInstance		(point.x, point.y))
	def shear(point:SgPoint):SgAffineTransform		= SgAffineTransform(AffineTransform getShearInstance		(point.x, point.y))
	def rotate(theta:Double):SgAffineTransform		= SgAffineTransform(AffineTransform getRotateInstance		theta)
	
	/*
	// TODO check for inverted concatenation logic
	
	// creates a transform mapping points in the from rectangle to points in the to rectangle
	def bounds(from:SgRectangle, to:SgRectangle):SgAffineTransform =
			identity translate to.topLeft scale to.size scale from.size.mulinv translate from.topLeft.addinv
			
	def transforms(x:SgTransform, y:SgTransform):SgAffineTransform	=
			bounds(SgRectangle(x.from, y.from), SgRectangle(x.to, y.to))
	
	def make(origin:SgPoint, position:SgPoint, scale:SgPoint, rotate:Double):SgAffineTransform =
			identity translate origin.addinv rotate rotate scale scale translate position
	*/
}

case class SgAffineTransform(delegate:AffineTransform) {
	def apply(point:Point2D):Point2D	= 
			delegate transform (point, null)
			
	def apply(point:SgPoint):SgPoint	= 
			SgPoint fromPoint2D (delegate transform (point.toPoint2D, null))
			
	def apply(rect:SgRectangle):SgRectangle	= {
		val	topLeft		= this apply rect.topLeft
		val bottomRight	= this apply rect.bottomRight
		SgRectangle fromPosSize (topLeft, bottomRight - topLeft)
	}
			
	def apply(shape:Shape):Shape	= 
			delegate createTransformedShape shape
			
	def translate(offset:SgPoint):SgAffineTransform =
			modify { out => out translate (offset.x, offset.y) }
	
	def scale(factor:SgPoint):SgAffineTransform = 
			modify { out => out scale (factor.x, factor.y) }
			
	def shear(factor:SgPoint):SgAffineTransform = 
			modify { out => out shear (factor.x, factor.y) }
	
	def rotate(theta:Double):SgAffineTransform = 
			modify { out => out rotate theta }
	
	def inverse:Option[SgAffineTransform]	= 
			catching(classOf[NoninvertibleTransformException]) opt SgAffineTransform(delegate.createInverse)
	
	def andThen(that:SgAffineTransform):SgAffineTransform	= 
			modify { out => out concatenate that.delegate }
	
	def compose(that:SgAffineTransform):SgAffineTransform	= 
			that andThen this
	
	private val orthogonalMask	= AffineTransform.TYPE_MASK_ROTATION | AffineTransform.TYPE_GENERAL_TRANSFORM

	def isOrthogonal:Boolean	= (delegate.getType & orthogonalMask) == 0
	
	def isIdentity:Boolean		= delegate.isIdentity
	
	/** fast bounds calculation for a transformed rectangle, as long as the transform is orthogonal */
	def bounds(rect:SgRectangle):SgRectangle	= {
		if (isIdentity)		return rect
		if (!isOrthogonal)	return SgRectangle fromRectangle2D (delegate createTransformedShape rect.toRectangle2D getBounds2D)
		
		val coords:Array[Double]	= Array(
				rect.x.start,
				rect.y.start,
				rect.x.end,
				rect.y.end)
		delegate transform (coords, 0, coords, 0, 2)
		SgRectangle(
				SgSpan(coords(0), coords(2)), 
				SgSpan(coords(1), coords(3)))
	}
	
	def toAffineTransform:AffineTransform	= cloneDelegate
	
	//------------------------------------------------------------------------------
	
	private def modify(effect:AffineTransform=>Unit):SgAffineTransform = {
		val	out	= cloneDelegate
		effect(out)
		SgAffineTransform(out)
	}
	
	private def cloneDelegate:AffineTransform	= 
			delegate.clone.asInstanceOf[AffineTransform]
}
