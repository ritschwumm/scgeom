package scgeom

import org.specs2.mutable._

class SgSpanTest extends Specification {
	"SgSpan" should {
		"intersect nothing when empty" in {
			SgSpan(1, 1) intersects SgSpan(0, 2) mustEqual false
		}
		"intersect nothing when empty" in {
			SgSpan(0, 2) intersects SgSpan(1, 1) mustEqual false
		}
		
		"intersect itself" in {
			SgSpan(0, 1) intersects SgSpan(0, 1) mustEqual true
		}
		
		"not intersect left to left" in {
			SgSpan(0, 1) intersects SgSpan(2, 4) mustEqual false
		}
		"not intersect left to start" in {
			SgSpan(0, 2) intersects SgSpan(2, 4) mustEqual false
		}
		"intersect left to inside" in {
			SgSpan(0, 3) intersects SgSpan(2, 4) mustEqual true
		}
		"intersect left to end" in {
			SgSpan(0, 4) intersects SgSpan(2, 4) mustEqual true
		}
		"intersect left to right" in {
			SgSpan(0, 5) intersects SgSpan(2, 4) mustEqual true
		}
		
		"intersect start to inside" in {
			SgSpan(2, 3) intersects SgSpan(2, 4) mustEqual true
		}
		"intersect start to end" in {
			SgSpan(2, 4) intersects SgSpan(2, 4) mustEqual true
		}
		"intersect start to right" in {
			SgSpan(2, 5) intersects SgSpan(2, 4) mustEqual true
		}
		
		"intersect inside to end" in {
			SgSpan(3, 4) intersects SgSpan(2, 4) mustEqual true
		}
		"intersect inside to right" in {
			SgSpan(3, 5) intersects SgSpan(2, 4) mustEqual true
		}
		
		"not intersect end to right" in {
			SgSpan(4, 5) intersects SgSpan(2, 4) mustEqual false
		}
		
		"not intersect right to right" in {
			SgSpan(5, 6) intersects SgSpan(2, 4) mustEqual false
		}
	}
	
	"SgSpan" should {
		"intersect nothing when empty" in {
			SgSpan(1, 1) intersect SgSpan(0, 2) mustEqual None
		}
		"intersect nothing when empty" in {
			SgSpan(0, 2) intersect SgSpan(1, 1) mustEqual None
		}
		
		"intersect itself" in {
			SgSpan(0, 1) intersect SgSpan(0, 1) mustEqual Some(SgSpan(0, 1))
		}
		
		"not intersect left to left" in {
			SgSpan(0, 1) intersect SgSpan(2, 4) mustEqual None
		}
		"not intersect left to start" in {
			SgSpan(0, 2) intersect SgSpan(2, 4) mustEqual None
		}
		"intersect left to inside" in {
			SgSpan(0, 3) intersect SgSpan(2, 4) mustEqual Some(SgSpan(2,3))
		}
		"intersect left to end" in {
			SgSpan(0, 4) intersect SgSpan(2, 4) mustEqual Some(SgSpan(2,4))
		}
		"intersect left to right" in {
			SgSpan(0, 5) intersect SgSpan(2, 4) mustEqual Some(SgSpan(2,4))
		}
		
		"intersect start to inside" in {
			SgSpan(2, 3) intersect SgSpan(2, 4) mustEqual Some(SgSpan(2,3))
		}
		"intersect start to end" in {
			SgSpan(2, 4) intersect SgSpan(2, 4) mustEqual Some(SgSpan(2,4))
		}
		"intersect start to right" in {
			SgSpan(2, 5) intersect SgSpan(2, 4) mustEqual Some(SgSpan(2,4))
		}
		
		"intersect inside to end" in {
			SgSpan(3, 4) intersect SgSpan(2, 4) mustEqual Some(SgSpan(3,4))
		}
		"intersect inside to right" in {
			SgSpan(3, 5) intersect SgSpan(2, 4) mustEqual Some(SgSpan(3,4))
		}
		
		"not intersect end to right" in {
			SgSpan(4, 5) intersect SgSpan(2, 4) mustEqual None
		}
		
		"not intersect right to right" in {
			SgSpan(5, 6) intersect SgSpan(2, 4) mustEqual None
		}
	}
}
