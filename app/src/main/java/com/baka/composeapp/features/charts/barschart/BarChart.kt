package com.baka.composeapp.features.charts.barschart

import android.graphics.Paint
import android.graphics.RectF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

@Composable
fun DrawSpiltFourCircle(width: Float = 200f, height: Float = 200f) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val padding = 48f
        val density = LocalDensity.current
        val textPaint = remember(density) {
            Paint().apply {
                /*color = android.graphics.Color.BLACK*/
                color = Color(0xFF91085B).toArgb()
                textAlign = Paint.Align.CENTER
                textSize = density.run { 20.sp.toPx() }
                strokeWidth = density.run { 4.sp.toPx() }
                isAntiAlias = true
            }
        }
        Canvas(
            modifier = Modifier
                .width(width.dp)
                .height(height.dp)
                .background(Color.White)
                .padding(padding.dp)
        ) {
            drawArc(
                Color(color = 0xFFfa59fd),
                startAngle = 0f,
                sweepAngle = -90f,
                useCenter = true,
            )
            drawContext.canvas.nativeCanvas.drawText(
                "0",
                size.width + density.run { padding.dp.div(2).toPx() },
                size.height.div(2) + density.run { padding.div(2) },
                textPaint,
            )
            drawArc(Color(0xFF60fcdc), 0f, 90f, true)
            drawContext.canvas.nativeCanvas.drawText(
                "270",
                0 + size.width.div(2),
                0f - density.run { padding.div(2) },
                textPaint,
            )
            drawArc(Color(0xFFfc9a57), 90f, 90f, true)
            drawContext.canvas.nativeCanvas.drawText(
                "90",
                0 + size.width.div(2),
                size.height + density.run { textPaint.textSize.div(2) } + density.run {
                    padding.dp.div(2).toPx()
                },
                textPaint,
            )
            drawArc(Color(0xFFfef259), -90f, -90f, true)
            drawContext.canvas.nativeCanvas.drawText(
                "180",
                0 - density.run { padding.dp.div(2).toPx() } - density.run {
                    textPaint.textSize.div(2)
                },
                size.height.div(2) + density.run { padding.div(2) },
                textPaint,
            )
        }

        Spacer(modifier = Modifier.padding(8.dp))
        val rectF = RectF()
        val paint = remember {
            Paint().apply {

            }
        }
        var mWidth by remember {
            mutableFloatStateOf(0f)
        }
        var mHeight by remember {
            mutableFloatStateOf(0f)
        }
        Canvas(
            modifier = Modifier
                .width(width.dp)
                .height(height.dp)
                .background(Color.White)
                .padding(padding.dp)
        ) {
            mWidth = this.size.width
            mHeight = this.size.height
            rectF.set(0f + 10, 0f + 10, mWidth, mHeight)
            paint.color = Color(0xFF60fcdc).toArgb()
            drawContext.canvas.nativeCanvas.drawArc(rectF, 0f, 90f, true, paint)
            rectF.set(0f - 10, 0f + 10, mWidth, mHeight)
            paint.color = Color(0xFFfa59fd).toArgb()
            drawContext.canvas.nativeCanvas.drawArc(rectF, 90f, 90f, true, paint)
            rectF.set(0f - 10, 0f - 10, mWidth, mHeight)
            paint.color = Color(0xFFfc9a57).toArgb()
            drawContext.canvas.nativeCanvas.drawArc(rectF, 180f, 90f, true, paint)
            rectF.set(0f + 10, 0f - 10, mWidth, mHeight)
            paint.color = Color(0xFFfef259).toArgb()
            drawContext.canvas.nativeCanvas.drawArc(rectF, 270f, 90f, true, paint)
        }
    }
}