package scgeom

import java.awt.Shape
import java.awt.geom.Area

object SgArea {
	def fromAwtShape(shape:Shape):SgArea	= SgArea(new Area(shape))
	
	//------------------------------------------------------------------------------
	//## awt conversion
	
	def fromAwtArea(it:Area):SgArea	= SgArea(it.clone.asInstanceOf[Area])
	def toAwtArea(it:SgArea):Area	= it.toAwtArea
}

case class SgArea(delegate:Area) {
	def | (that:SgArea):SgArea	= modify { _ add			that.delegate }
	def |! (that:SgArea):SgArea	= modify { _ subtract		that.delegate }
	def ^ (that:SgArea):SgArea	= modify { _ exclusiveOr	that.delegate }
	def & (that:SgArea):SgArea	= modify { _ intersect		that.delegate }
	
	//------------------------------------------------------------------------------
	//## awt conversion
	
	def toAwtArea:Area	= cloneDelegate
	
	//------------------------------------------------------------------------------
	//## internals
	
	private def modify(effect:Area=>Unit):SgArea = {
		val	out	= cloneDelegate
		effect(out)
		SgArea(out)
	}
	
	private def cloneDelegate:Area	= 
			delegate.clone.asInstanceOf[Area]
}
