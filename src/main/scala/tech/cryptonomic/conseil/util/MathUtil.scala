package tech.cryptonomic.conseil.util

import scala.math.sqrt

object MathUtil {

  /**
    * Average value of a sequence of integers.
    * @param l Sequence of doubles.
    * @return
    */
  def mean(l: Seq[Double]): Double =
    l.sum*1.0 / l.length

  /**
    * Standard deviation of a sequence of integers.
    * @param l Sequence of doubles.
    * @return
    */
  def stdev(l: Seq[Double]): Double = {
    val m = mean(l)
    val len = l.length*1.0
    sqrt(l.map(x => (x - m)*(x - m)).sum / len)
  }

}
