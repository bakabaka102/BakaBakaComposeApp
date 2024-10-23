package com.baka.composeapp.features.charts.columns

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.baka.composeapp.helper.Logger

@Composable
fun ColumnsChart(width: Float = 300f, height: Float = 300f) {
    val list: List<Pair<Float, Float>> = listOf(
        Pair(1f, 5f),
        Pair(2f, 8f),
        Pair(3f, 3f),
        Pair(4f, 11f),
        Pair(5f, 2f),
        Pair(6f, 4f),
        Pair(7f, 3f)
    )
    Column(
        modifier = Modifier
            .size(width.dp, height.dp)
            .background(Color.White),
    ) {
        Row {
            var mWidth by remember {
                mutableFloatStateOf(0f)
            }
            var mHeight by remember {
                mutableFloatStateOf(0f)
            }
            val minY = list.minBy { it.second }.second.also {
                Logger.i("Min of y --- $it")
            }
            val maxY = list.maxBy { it.second }.second.also {
                Logger.i("Max of y --- $it")
            }
            val partsOfYAxis = 5
            val partsOfXAxis = list.size
            val yAxisSpace = mHeight / 5
            val paddingSpace = 16
            val spaceWithText = 10

            val dividedRange: List<Int> = divideRangeIntoEqualParts(
                previousMultipleOfNumber(value = minY.toInt(), number = partsOfYAxis),
                nextMultipleOfNumber(value = maxY.toInt(), number = partsOfYAxis),
                partsOfYAxis,
            )
            val density = LocalDensity.current
            val textPaint = remember(density) {
                Paint().apply {
                    color = Color(0xFF91085B).toArgb()
                    textAlign = Paint.Align.CENTER
                    textSize = density.run { 12.sp.toPx() }
                }
            }
            // Measure the height of the text
            val textPaintHeight by remember {
                /*mutableFloatStateOf(textPaint.descent() - textPaint.ascent())*/
                mutableFloatStateOf(textPaint.getTextHeightUsingFontMetrics()).also {
                    Logger.i("Text paint --- $it")
                }
            }

            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 32.dp),
                onDraw = {
                    mWidth = this.size.width
                    mHeight = this.size.height
                    //Draw Text and horizontal lines
                    dividedRange.forEachIndexed { index, value ->
                        drawContext.canvas.nativeCanvas.drawText(
                            value.toString(),
                            paddingSpace.dp.toPx() / 2f,
                            mHeight - yAxisSpace * index,
                            textPaint,
                        )
                        drawLine(
                            color = Color(0xFF91085B),
                            start = Offset(
                                0f + (paddingSpace.dp.toPx() / 2f) + spaceWithText.dp.toPx(),
                                (mHeight - yAxisSpace * index)
                            ),
                            end = Offset(
                                mWidth - paddingSpace,
                                (mHeight - yAxisSpace * index)
                            ),
                            strokeWidth = 2f,
                            pathEffect = PathEffect.dashPathEffect(
                                intervals = floatArrayOf(16f, 4f),
                                phase = 0f,
                            ),
                            cap = StrokeCap.Round,
                        )
                    }
                    list.forEachIndexed { index, (xValue, yValue) ->
                        //Draw Horizontal texts
                        val xPointOfText =
                            (mWidth * (index + 1) / partsOfXAxis) - spaceWithText.dp.toPx()
                        val barHeight = (yValue / maxY) * mHeight * 4 / 5
                        val barWidth = mWidth / /*list.size*/24
                        drawContext.canvas.nativeCanvas.drawText(
                            xValue.toString(),
                            xPointOfText,
                            mHeight + textPaintHeight,
                            textPaint,
                        )
                        //Draw Columns
                        drawRoundRect(
                            color = Color.Yellow,
                            topLeft = Offset(xPointOfText - barWidth / 2, mHeight - barHeight),
                            size = Size(barWidth, barHeight),
                            cornerRadius = CornerRadius(x = 8f, y = 8f),
                        )
                    }
                })
        }
    }
}

fun Paint.getTextHeightUsingFontMetrics(): Float {
    val metrics = Paint.FontMetrics()
    this.getFontMetrics(metrics)
    return metrics.descent - metrics.ascent
}

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