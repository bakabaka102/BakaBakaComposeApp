package com.baka.composeapp.helper

fun divideRangeIntoEqualParts(start: Int, end: Int, parts: Int): List<Int> {
    if (parts <= 0 || start >= end) return emptyList()

    val step = ((end - start) / (parts - 1)).also {
        Logger.i("Step === $it")
    }
    return List(parts) { index ->
        start + index * step
    }
}

fun nextMultipleOfNumber(value: Int, number: Int = 4): Int {
    return if (value < 0) {
        ((value / number) - 1) * number
    } else {
        ((value / number) + 1) * number
    }
}

fun previousMultipleOfNumber(value: Int, number: Int = 4): Int {
    return if (value < 0) {
        ((value / number) + 1) * number
    } else {
        (value / number) * number
    }
}