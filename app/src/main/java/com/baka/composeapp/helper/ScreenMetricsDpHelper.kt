package com.baka.composeapp.helper

import android.graphics.Paint

fun Paint.getTextHeightUsingFontMetrics(): Float {
    val metrics = Paint.FontMetrics()
    this.getFontMetrics(metrics)
    return metrics.descent - metrics.ascent
}