package com.baka.composeapp.features.charts.models

import androidx.compose.ui.graphics.Color

data class PieData(val color: Color, val value: Float)

val dataPie = listOf(
    PieData(Color(0xFFAA6B0E), 60f),
    PieData(Color(0xFF1398A8), 110f),
    PieData(Color(0xFFC90849), 20f),
    PieData(Color(0xFF502E8B), 80f),
    PieData(Color(0xFFD84315), 180f),
)