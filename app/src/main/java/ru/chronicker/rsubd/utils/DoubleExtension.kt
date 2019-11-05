package ru.chronicker.rsubd.utils

import java.math.BigDecimal
import java.math.RoundingMode

fun Double.safeCompare(value: Double) =
    BigDecimal(this).compareTo(BigDecimal(value))

fun Double.isBiggerThan(value: Double) =
    this.safeCompare(value) == 1


fun Double.isSmallerThan(value: Double) =
    this.safeCompare(value) == -1

fun Double.isBiggerOrEqualThan(value: Double): Boolean {
    val compareResult = this.safeCompare(value)
    return compareResult == 1 || compareResult == 0
}

fun Double.isSmallerOrEqualThan(value: Double) : Boolean {
    val compareResult = this.safeCompare(value)
    return compareResult == -1 || compareResult == 0
}
