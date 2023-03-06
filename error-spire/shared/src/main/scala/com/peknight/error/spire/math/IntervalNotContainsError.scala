package com.peknight.error.spire.math

import cats.Show
import cats.syntax.show.*
import com.peknight.error.ValueError
import spire.math.*
import spire.math.interval.{Closed, Open}

trait IntervalNotContainsError[N] extends ValueError[N]:
  def interval: Interval[N]
end IntervalNotContainsError
object IntervalNotContainsError:
  def apply[N : Show](value0: N, label0: String, interval0: Interval[N]): IntervalNotContainsError[N] =
    new IntervalNotContainsError[N]:
      def value: N = value0
      def label: String = label0
      def interval: Interval[N] = interval0
      def message: String = interval match
        case All() => s"$label(${value.show}) is not in an all interval"
        case above@Above(_, _) => above.lowerBound match
          case Open(lower) => s"$label should greater than ${lower.show}, but was ${value.show}"
          case Closed(lower) => s"$label should greater than or equal to ${lower.show}, but was ${value.show}"
        case below@Below(_, _) => below.upperBound match
          case Open(upper) => s"$label should less than ${upper.show}, but was ${value.show}"
          case Closed(upper) => s"$label should less than or equal to ${upper.show}, but was ${value.show}"
        case bounded@Bounded(_, _, _) => (bounded.lowerBound, bounded.upperBound) match
          case (Open(lower), Open(upper)) =>
            s"$label should greater than ${lower.show} and less than ${upper.show}, but was ${value.show}"
          case (Open(lower), Closed(upper)) =>
            s"$label should greater than ${lower.show} and less than or equal to ${upper.show}, but was ${value.show}"
          case (Closed(lower), Open(upper)) =>
            s"$label should greater than or equal to ${lower.show} and less than ${upper.show}, but was ${value.show}"
          case (Closed(lower), Closed(upper)) =>
            s"$label should greater than or equal to ${lower.show} and less than or equal to ${upper.show}, " +
              s"but was ${value.show}"
        case Point(v) => s"$label should be ${v.show}, but was ${value.show}"
        case Empty() => s"$label(${value.show}) will never be in an empty interval"
  end apply

  def unapply[N](error: IntervalNotContainsError[N]): Some[(N, String, Interval[N])] =
    Some((error.value, error.label, error.interval))

end IntervalNotContainsError
