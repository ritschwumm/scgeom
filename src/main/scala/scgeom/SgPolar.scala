package scgeom

object SgPolar {
	val zero	= SgPolar(0,0)
	val one		= SgPolar(1,1)
}

case class SgPolar(length:Double, angle:Double) {
	import scala.math._
	
	def +(that:SgPolar):SgPolar	= (this.toKartesian + that.toKartesian).toPolar
	def -(that:SgPolar):SgPolar	= (this.toKartesian - that.toKartesian).toPolar
	
	def *(value:Double):SgPolar	= SgPolar(length * value, angle)
	def /(value:Double):SgPolar	= SgPolar(length / value, angle)
	
	def normalize:SgPolar				= SgPolar(1, angle)
	def rotate(angle:Double):SgPolar	= SgPolar(length, this.angle + angle)
	
	def x:Double	= length * cos(angle)
	def y:Double	= length * sin(angle)
	
	def toKartesian:SgPoint	= SgPoint(x, y)
}
