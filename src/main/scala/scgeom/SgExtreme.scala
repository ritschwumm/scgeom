package scgeom

object SgExtreme {
	def trueStart(start:Boolean):SgExtreme	=
		if (start)	Start
		else		End

	def trueEnd(end:Boolean):SgExtreme	=
		if (end)	End
		else		Start

	//------------------------------------------------------------------------------

	final object Start	extends SgExtreme
	final object End	extends SgExtreme
}

sealed trait SgExtreme {
	def cata[T](start: =>T, end: =>T):T	=
		this match {
			case SgExtreme.Start	=> start
			case SgExtreme.End	=> end
		}

	def opposite:SgExtreme	=
		this match {
			case SgExtreme.Start	=> SgExtreme.End
			case SgExtreme.End		=> SgExtreme.Start
		}
}

