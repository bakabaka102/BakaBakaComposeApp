package com.baka.composeapp.features.charts.lines

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.baka.composeapp.helper.Logger
import com.baka.composeapp.helper.divideRangeIntoEqualParts
import com.baka.composeapp.helper.getTextHeightUsingFontMetrics
import com.baka.composeapp.helper.nextMultipleOfNumber
import com.baka.composeapp.helper.previousMultipleOfNumber

@Composable
fun DrawBezierCurves(height: Float = 80f, width: Float = 300f) {
    //https://stackoverflow.com/questions/76148870/how-to-draw-or-create-this-curve-shape-in-jetpack-compose
    Spacer(modifier = Modifier
        .height(height.dp)
        .width(width.dp)
        .background(Color.White)
        .drawWithCache {
            onDrawBehind {
                val path = Path().apply {
                    moveTo(0f, 0f)
                    cubicTo(
                        x1 = width.dp.toPx() / 10,
                        y1 = 0f,
                        x2 = width.dp.toPx() / 4,
                        y2 = (height - 10).dp.toPx(),
                        x3 = width.dp.toPx() / 2,
                        y3 = 70.dp.toPx()
                    )
                    cubicTo(
                        x1 = width.dp.toPx() * 3 / 4,
                        y1 = (height - 10).dp.toPx(),
                        x2 = width.dp.toPx() - width.dp.toPx() / 10,
                        y2 = 0f,
                        x3 = width.dp.toPx(),
                        y3 = 0f
                    )
                }
                path.close()
                drawPath(
                    path = path,
                    /*color = Color.Blue,*/
                    brush = Brush.verticalGradient(
                        listOf(
                            Color(0xFF58EEE0),
                            Color.Transparent
                        )
                    ),
                    /*style = Stroke(width = 6f, cap = StrokeCap.Round),*/
                    style = Fill,
                )
            }
        })
    //https://viblo.asia/p/cai-tien-bottomnavigationview-trong-android-gGJ59bLrKX2
    //Spacer(modifier = Modifier.padding(8.dp))
}

@Composable
fun BezierCurve(
    width: Float = 300f, height: Float = 300f,
    lists: List<Pair<Float, Float>> = listOf(
        Pair(1f, 5f),
        Pair(2f, 8f),
        Pair(3f, 3f),
        Pair(4f, 11f),
        Pair(5f, 2f),
        Pair(6f, 4f),
        Pair(7f, 3f),
    )
) {
    val points by remember { mutableStateOf(lists) }
    Column(
        modifier = Modifier
            .size(width.dp, height.dp)
            .background(Color.White),
    ) {
        var mWidth by remember {
            mutableFloatStateOf(0f)
        }
        var mHeight by remember {
            mutableFloatStateOf(0f)
        }
        val minY = points.minBy { it.second }.second.also {
            Logger.i("Min of y --- $it")
        }
        val maxY = points.maxBy { it.second }.second.also {
            Logger.i("Max of y --- $it")
        }
        val partsOfYAxis = 5
        val partsOfXAxis = points.size
        val yAxisSpace = mHeight / 5
        val paddingSpace = 16
        val spaceWithText = 10
        val radiusCirclePoint = mWidth / 50

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
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 32.dp)
                .background(Color.LightGray)
        ) {
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
            points.forEachIndexed { index, (xValue, yValue) ->
                val xPointOfText =
                    (mWidth * (index + 1) / partsOfXAxis) - spaceWithText.dp.toPx()
                val barHeight = ((yValue / maxY) * mHeight).also {
                    Logger.i("yValue == $yValue ---maxY == $maxY--- mHeight == $mHeight -- Height of point $it")
                }
                /*val barHeight = (dataPoint.value / maxBarValue) * maxBarHeight
                val barHeight = (yValue / maxY) * mHeight * 4 / 5
                */
                //Draw Horizontal texts
                drawContext.canvas.nativeCanvas.drawText(
                    xValue.toString(),
                    xPointOfText,
                    mHeight + textPaintHeight,
                    textPaint,
                )
                //Draw points
                drawCircle(
                    color = Color.Yellow,
                    radius = radiusCirclePoint,
                    center = Offset(
                        xPointOfText - radiusCirclePoint / 2,
                        radiusCirclePoint + mHeight - (barHeight * 4 / 5)
                    )
                    /*topLeft = Offset(xPointOfText - barWidth / 2, mHeight - barHeight),
                    size = Size(barWidth, barHeight),
                    cornerRadius = CornerRadius(x = 8f, y = 8f),*/
                )
            }
        }
    }
}