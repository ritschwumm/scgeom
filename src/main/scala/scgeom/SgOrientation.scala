package scgeom

object SgOrientation {
	def horizontal(horizontal:Boolean):SgOrientation	=
			if (horizontal)	SgHorizontal
			else			SgVertical
			
	def vertical(vertical:Boolean):SgOrientation	=
			if (vertical)	SgVertical
			else			SgHorizontal
}

sealed trait SgOrientation {
	def cata[T](horizontal: =>T, vertical: =>T):T	=
			this match {
				case SgHorizontal	=> horizontal
				case SgVertical		=> vertical
			}
			
	def opposite:SgOrientation	= this match {
		case SgHorizontal	=> SgVertical
		case SgVertical		=> SgHorizontal
	}
}

object SgHorizontal	extends SgOrientation
object SgVertical	extends SgOrientation
