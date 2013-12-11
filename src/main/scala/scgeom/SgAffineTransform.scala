package scgeom

import java.awt.{ Shape }
import java.awt.geom.{ Point2D, AffineTransform, NoninvertibleTransformException }

object SgAffineTransform {
	val identity:SgAffineTransform	= SgAffineTransform(new AffineTransform)
	
	def translate(offset:SgPoint):SgAffineTransform	= 
			SgAffineTransform(AffineTransform getTranslateInstance	(offset.x,	offset.y))
	def scale(factor:SgPoint):SgAffineTransform		= 
			SgAffineTransform(AffineTransform getScaleInstance		(factor.x,	factor.y))
	def shear(factor:SgPoint):SgAffineTransform		= 
			SgAffineTransform(AffineTransform getShearInstance		(factor.x,	factor.y))
	def rotate(theta:Double):SgAffineTransform		= 
			SgAffineTransform(AffineTransform getRotateInstance		theta)
	def rotateAround(theta:Double, center:SgPoint):SgAffineTransform	=
			SgAffineTransform(AffineTransform getRotateInstance	(theta, center.x, center.y))
	
	//------------------------------------------------------------------------------
	// awt
	
	def fromAwtAffineTransform(it:AffineTransform):SgAffineTransform	=
			SgAffineTransform(it.clone.asInstanceOf[AffineTransform])
		
	def toAwtAffineTransform(it:SgAffineTransform):AffineTransform	=
			it.toAwtAffineTransform
}

case class SgAffineTransform(delegate:AffineTransform) {
	/** alias for transform */
	def apply(point:SgPoint):SgPoint	= 
			transform(point)
		
	def transform(point:SgPoint):SgPoint	= 
			SgPoint fromAwtPoint2D (delegate transform (point.toAwtPoint2D, null))
			
	def transformAwtPoint2D(point:Point2D):Point2D	= 
			delegate transform (point, null)
		
	def transformAwtShape(shape:Shape):Shape	= 
			delegate createTransformedShape shape
			
	/** fast bounds calculation for a transformed rectangle, as long as the transform is orthogonal */
	def transformBounds(rect:SgRectangle):SgRectangle	= {
		if (isIdentity)		return rect
		if (!isOrthogonal)	return SgRectangle fromAwtRectangle2D (delegate createTransformedShape rect.toAwtRectangle2D getBounds2D)
		
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
	
	def inverse:Option[SgAffineTransform]	=
			try { Some(SgAffineTransform(delegate.createInverse)) }
			catch { case e:NoninvertibleTransformException => None }
	
	/** rotate around a given center */
	def rotateAround(theta:Double, center:SgPoint):SgAffineTransform	=
			modify { _ rotate (theta, center.x, center.y) }
	
	def translate(offset:SgPoint):SgAffineTransform =
			modify { _ translate (offset.x, offset.y) }
	
	def scale(factor:SgPoint):SgAffineTransform = 
			modify { _ scale (factor.x, factor.y) }
			
	def shear(factor:SgPoint):SgAffineTransform = 
			modify { _ shear (factor.x, factor.y) }
	
	def rotate(theta:Double):SgAffineTransform = 
			modify { _ rotate theta }
	
	def andThen(that:SgAffineTransform):SgAffineTransform	= 
			modify { _ concatenate that.delegate }
	
	def compose(that:SgAffineTransform):SgAffineTransform	= 
			that andThen this
	
	private val orthogonalMask	= AffineTransform.TYPE_MASK_ROTATION | AffineTransform.TYPE_GENERAL_TRANSFORM

	def isOrthogonal:Boolean	= (delegate.getType & orthogonalMask) == 0
	
	def isIdentity:Boolean		= delegate.isIdentity
	
	def toAwtAffineTransform:AffineTransform	= cloneDelegate
	
	private def modify(effect:AffineTransform=>Unit):SgAffineTransform = {
		val	out	= cloneDelegate
		effect(out)
		SgAffineTransform(out)
	}
	
	private def cloneDelegate:AffineTransform	= 
			delegate.clone.asInstanceOf[AffineTransform]
}
