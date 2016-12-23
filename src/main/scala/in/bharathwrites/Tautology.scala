package in.bharathwrites

import scala.util.control.NonFatal
import scala.util.parsing.combinator.JavaTokenParsers

object Tautology {

  case object NotTautology extends Exception()

  def main(args: Array[String]) = {
    isTautology(args(0)) match {
      case true => println(s"${args(0)}: is a tautology")
      case false => println(s"${args(0)}: NOT a tautology")
    }
  }

  def isTautology(input: String): Boolean = {
    val variables: Set[String] = getVariables(input).map(_.toString)
    val truth = createTruthTable(variables.toSeq: _*).par
    // println(s"variables = $variables, truth table = $truth")
    try {
      for (row <- truth) {
        val variableParser = sparse(row) _
        if (!variableParser(input)) throw NotTautology
      }
      true
    } catch {
      case NonFatal(e) =>
        false
    }
  }

  private case class LogicParser(variableMap: Map[String, Boolean]) extends JavaTokenParsers {
    private lazy val b_expression: Parser[Boolean] = b_term ~ rep("|" ~ b_term) ^^ { case f1 ~ fs ⇒ (f1 /: fs) (_ || _._2) }
    private lazy val b_term: Parser[Boolean] = (b_not_factor ~ rep("&" ~ b_not_factor)) ^^ { case f1 ~ fs ⇒ (f1 /: fs) (_ && _._2) }
    private lazy val b_not_factor: Parser[Boolean] = opt("!") ~ b_factor ^^ (x ⇒ x match {
      case Some(v) ~ f ⇒ !f;
      case None ~ f ⇒ f
    })
    private lazy val b_factor: Parser[Boolean] = b_literal | b_variable | ("(" ~ b_expression ~ ")" ^^ { case "(" ~ exp ~ ")" ⇒ exp })
    private lazy val b_literal: Parser[Boolean] = "true" ^^ (x ⇒ true) | "false" ^^ (x ⇒ false)
    private lazy val b_variable: Parser[Boolean] = variableMap.keysIterator.map(Parser(_)).reduceLeft(_ | _) ^^ (x ⇒ variableMap(x))

    def sparse(expression: String): Boolean = this.parseAll(b_expression, expression).getOrElse(false)
  }

  private def sparse(variables: Map[String, Boolean])(value: String): Boolean = LogicParser(variables).sparse(value)

  private def getVariables(statement: String): Set[Char] = statement.replaceAll("\\s+|&|\\||!|\\(|\\)", "").toSet

  private lazy val tflist = List(true, false)

  private def createTruthTable(v: String*): List[Map[String, Boolean]] = {
    v.length match {
      case 0 => println(s"No variables"); throw NotTautology
      case 1 => for (tf1 <- tflist)
        yield Map(v(0) -> tf1)
      case 2 => for (tf1 <- tflist; tf2 <- tflist)
        yield Map(v(0) -> tf1, v(1) -> tf2)
      case 3 => for (tf1 <- tflist; tf2 <- tflist; tf3 <- tflist)
        yield Map(v(0) -> tf1, v(1) -> tf2, v(2) -> tf3)
      case 4 => for (tf1 <- tflist; tf2 <- tflist; tf3 <- tflist; tf4 <- tflist)
        yield Map(v(0) -> tf1, v(1) -> tf2, v(2) -> tf3, v(3) -> tf4)
      case 5 => for (tf1 <- tflist; tf2 <- tflist; tf3 <- tflist; tf4 <- tflist; tf5 <- tflist)
        yield Map(v(0) -> tf1, v(1) -> tf2, v(2) -> tf3, v(3) -> tf4, v(4) -> tf5)
      case 6 => for (tf1 <- tflist; tf2 <- tflist; tf3 <- tflist; tf4 <- tflist; tf5 <- tflist; tf6 <- tflist)
        yield Map(v(0) -> tf1, v(1) -> tf2, v(2) -> tf3, v(3) -> tf4, v(4) -> tf5, v(5) -> tf6)
      case 7 => for (tf1 <- tflist; tf2 <- tflist; tf3 <- tflist; tf4 <- tflist; tf5 <- tflist; tf6 <- tflist; tf7 <- tflist)
        yield Map(v(0) -> tf1, v(1) -> tf2, v(2) -> tf3, v(3) -> tf4, v(4) -> tf5, v(5) -> tf6, v(6) -> tf7)
      case 8 => for (tf1 <- tflist; tf2 <- tflist; tf3 <- tflist; tf4 <- tflist; tf5 <- tflist; tf6 <- tflist; tf7 <- tflist; tf8 <- tflist)
        yield Map(v(0) -> tf1, v(1) -> tf2, v(2) -> tf3, v(3) -> tf4, v(4) -> tf5, v(5) -> tf6, v(6) -> tf7, v(7) -> tf8)
      case 9 => for (tf1 <- tflist; tf2 <- tflist; tf3 <- tflist; tf4 <- tflist; tf5 <- tflist; tf6 <- tflist; tf7 <- tflist; tf8 <- tflist; tf9 <- tflist)
        yield Map(v(0) -> tf1, v(1) -> tf2, v(2) -> tf3, v(3) -> tf4, v(4) -> tf5, v(5) -> tf6, v(6) -> tf7, v(7) -> tf8, v(8) -> tf9)
      case 10 => for (tf1 <- tflist; tf2 <- tflist; tf3 <- tflist; tf4 <- tflist; tf5 <- tflist; tf6 <- tflist; tf7 <- tflist; tf8 <- tflist; tf9 <- tflist; tf10 <- tflist)
        yield Map(v(0) -> tf1, v(1) -> tf2, v(2) -> tf3, v(3) -> tf4, v(4) -> tf5, v(5) -> tf6, v(6) -> tf7, v(7) -> tf8, v(8) -> tf9, v(9) -> tf10)
      case _ => println(s"More than 10 variables"); throw NotTautology
    }
  }
}