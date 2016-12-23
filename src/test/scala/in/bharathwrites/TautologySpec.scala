package in.bharathwrites

import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.{Inspectors, Matchers, WordSpec}

class TautologySpec extends WordSpec with Matchers with TypeCheckedTripleEquals with Inspectors {

  import Tautology._

  "Verify Proposition" should {
    "return false for non tautologies" in {
      isTautology("a") should === (false)
      isTautology("a & b") should === (false)
      isTautology("a & (b | c)") should === (false)
      isTautology("!a & !b") should === (false)
      isTautology("(!a | (b & !a))") should === (false)
    }
    "return true for tautologies" in {
      isTautology("a | !a") should === (true)
      isTautology("(a & (!b | b)) | (!a & (!b | b))") should === (true)
      isTautology("(!a | (a & a))") should === (true)
      isTautology("((a & (!b | b)) | (!a & (!b | b)))") should === (true)
    }
  }
}
