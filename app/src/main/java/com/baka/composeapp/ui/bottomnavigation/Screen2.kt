package com.baka.composeapp.ui.bottomnavigation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun Screen2() {
//https://proandroiddev.com/exploring-canvas-in-jetpack-compose-crafting-graphics-animations-and-game-experiences-b0aa31160bff
//https://proandroiddev.com/creating-graph-in-jetpack-compose-312957b11b2

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Surface(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(24.dp)
        ) {
            BarChart()
        }
    }
}

data class DataPoint(val value: Float, val color: Color)

@Composable
fun BarChart() {
    val temperatureData = listOf(
        DataPoint(20f, Color.Red),
        DataPoint(45f, Color.Blue),
        DataPoint(130f, Color.Magenta),
        DataPoint(80f, Color.Yellow),
        DataPoint(65f, Color.Cyan)
    )
    val maxBarValue = temperatureData.maxOf { it.value }
    Column {
        Canvas(
            modifier = Modifier
                .size(300.dp)
                .padding(24.dp)
        )
        {
            val maxBarHeight = size.height
            val barWidth = size.width / temperatureData.size

            temperatureData.forEachIndexed { index, dataPoint ->
                val barHeight = (dataPoint.value / maxBarValue) * maxBarHeight
                drawRect(
                    color = dataPoint.color,
                    topLeft = Offset(index * barWidth, size.height - barHeight),
                    size = Size(barWidth, barHeight),
                    /*style = Stroke(width = 12f, cap = StrokeCap.Round),*/
                )
            }
        }
    }
}