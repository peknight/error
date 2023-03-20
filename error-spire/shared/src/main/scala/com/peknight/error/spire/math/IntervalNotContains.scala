package com.peknight.error.spire.math

import cats.Show
import cats.syntax.show.*
import com.peknight.error.ErrorType
import spire.math.*
import spire.math.interval.{Closed, Open}

trait IntervalNotContains extends ErrorType
case object IntervalNotContains extends IntervalNotContains:

  given [N : Show]: IntervalNotContainsErrorShow[N] with
    def show(errorType: IntervalNotContains, label: String, actual: N, expect: Interval[N]): String =
      expect match
        case All() => s"$label(${actual.show}) is not in an all interval"
        case above@Above(_, _) => above.lowerBound match
          case Open(lower) => s"$label should greater than ${lower.show}, but was ${actual.show}"
          case Closed(lower) => s"$label should greater than or equal to ${lower.show}, but was ${actual.show}"
        case below@Below(_, _) => below.upperBound match
          case Open(upper) => s"$label should less than ${upper.show}, but was ${actual.show}"
          case Closed(upper) => s"$label should less than or equal to ${upper.show}, but was ${actual.show}"
        case bounded@Bounded(_, _, _) => (bounded.lowerBound, bounded.upperBound) match
          case (Open(lower), Open(upper)) =>
            s"$label should greater than ${lower.show} and less than ${upper.show}, but was ${actual.show}"
          case (Open(lower), Closed(upper)) =>
            s"$label should greater than ${lower.show} and less than or equal to ${upper.show}, but was ${actual.show}"
          case (Closed(lower), Open(upper)) =>
            s"$label should greater than or equal to ${lower.show} and less than ${upper.show}, but was ${actual.show}"
          case (Closed(lower), Closed(upper)) =>
            s"$label should greater than or equal to ${lower.show} and less than or equal to ${upper.show}, " +
              s"but was ${actual.show}"
        case Point(v) => s"$label should be ${v.show}, but was ${actual.show}"
        case Empty() => s"$label(${actual.show}) will never be in an empty interval"
  end given
end IntervalNotContains