package scgeom

import java.awt.Shape
import java.awt.geom.Area

object SgArea {
	def fromShape(shape:Shape):SgArea	= new SgArea(new Area(shape))
}

case class SgArea(delegate:Area) {
	def | (that:SgArea):SgArea	= modify { _ add			that.delegate }
	def |! (that:SgArea):SgArea	= modify { _ subtract		that.delegate }
	def ^ (that:SgArea):SgArea	= modify { _ exclusiveOr	that.delegate }
	def & (that:SgArea):SgArea	= modify { _ intersect		that.delegate }
	
	def toArea:Area	= cloneDelegate
	
	private def modify(effect:Area=>Unit):SgArea = {
		val	out	= cloneDelegate
		effect(out)
		SgArea(out)
	}
	
	private def cloneDelegate:Area	= 
			delegate.clone.asInstanceOf[Area]
}
