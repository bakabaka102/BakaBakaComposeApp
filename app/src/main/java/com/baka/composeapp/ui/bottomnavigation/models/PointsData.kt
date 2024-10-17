package com.baka.composeapp.ui.bottomnavigation.models

import androidx.compose.ui.graphics.Color

data class PointsData(
    val startX: Float = .2f,
    val endX: Float = .8f,
    val topY: Float = .2f,
    val bottomY: Float = .4f,
)

data class DataPoint(val value: Float, val color: Color)
