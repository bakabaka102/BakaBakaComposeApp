package com.baka.composeapp.features.charts.barschart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.baka.composeapp.ui.bottomnavigation.models.DataPoint

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