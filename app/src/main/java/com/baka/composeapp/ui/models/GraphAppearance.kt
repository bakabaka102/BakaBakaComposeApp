package com.baka.composeapp.ui.models

import androidx.compose.ui.graphics.Color

data class GraphAppearance(
    val graphColor: Color,
    val graphAxisColor: Color,
    val graphThickness: Float,
    val isColorAreaUnderChart: Boolean,
    val colorAreaUnderChart: Color,
    val isCircleVisible: Boolean,
    val circleColor: Color,
    val backgroundColor: Color,
)