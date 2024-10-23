package com.baka.composeapp.features.charts.lines

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.sin

@Composable
fun SinCosPath() {
    Canvas(modifier = Modifier
        .size(300.dp)
        .background(Color.White),
        onDraw = {
            val middleW = size.width / 2
            val middleH = size.height / 2
            drawLine(Color.Red, Offset(0f, middleH), Offset(size.width - 1, middleH))
            drawLine(Color.Magenta, Offset(middleW, 0f), Offset(middleW, size.height - 1))
            val points = mutableListOf<Offset>()
            for (x in 0 until size.width.toInt()) {
                val y = (sin(x * (2f * PI / size.width)) * middleH + middleH).toFloat()
                points.add(Offset(x.toFloat(), y))
            }
            drawPoints(
                points = points,
                strokeWidth = 4f,
                pointMode = PointMode.Points,
                color = Color.Blue
            )
        }
    )
    Spacer(modifier = Modifier.padding(8.dp))
    Spacer(
        modifier = Modifier
            .height(80.dp)
            .width(300.dp)
            .background(Color.White)
            .drawWithCache {
                onDrawBehind {
                    drawLine(
                        color = Color.Black, start = Offset(0f, 0f),
                        end = Offset(size.width - 1, size.height - 1)
                    )
                    drawLine(
                        Color.Black, Offset(0f, size.height - 1),
                        Offset(size.width - 1, 0f)
                    )
                    drawCircle(
                        Color.Red, 64f,
                        Offset(size.width / 2, size.height / 2)
                    )
                }
            }
    )
}