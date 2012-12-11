package scgeom

object SgExtreme {
	def start(start:Boolean):SgExtreme	=
			if (start)	SgStart
			else		SgEnd
			
	def end(end:Boolean):SgExtreme	=
			if (end)	SgEnd
			else		SgStart
}

sealed trait SgExtreme {
	def cata[T](start: =>T, end: =>T):T	=
			this match {
				case SgStart	=> start
				case SgEnd		=> end
			}
			
	def opposite:SgExtreme	= this match {
		case SgStart	=> SgEnd
		case SgEnd		=> SgStart
	}
}

object SgStart	extends SgExtreme
object SgEnd	extends SgExtreme
