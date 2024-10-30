package com.baka.composeapp.features.charts.lines

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.baka.composeapp.helper.Logger
import kotlin.math.abs

//https://archive.is/0SrIb
//https://medium.com/mobile-app-development-publication/learn-jetpack-compose-canvas-cubic-and-quadratic-bezier-and-its-usage-96a4d9a7e3fb
//https://medium.com/@pranjalg2308/understanding-bezier-curve-in-android-and-moddinggraphview-library-a9b1f0f95cd0

//https://medium.com/@kezhang404/either-compose-is-elegant-or-if-you-want-to-draw-something-with-an-android-view-you-have-to-7ce00dc7cc1
//https://gist.github.com/0xZhangKe/0b37c18df37cdcad92e99b91e77d8d54
//https://gist.github.com/0xZhangKe/f2d4a1771b33f4a046ca9a861ecfc04e
@Composable
fun BezierCurve(
    modifier: Modifier,
    points: List<Float>,
    minPoint: Float? = null,
    maxPoint: Float? = null,
    style: BezierCurveStyle,
) {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    var tapPosition by remember { mutableStateOf<Offset?>(null) }
    var isTouched by remember { mutableStateOf(false) }
    Canvas(
        modifier = modifier
            .padding(16.dp)
            .onSizeChanged {
                size = it
            }
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    tapPosition = offset
                    isTouched = true
                    Logger.i("Touch point is --- $tapPosition")
                }
            },
        onDraw = {
            if (size != IntSize.Zero && points.size > 1) {
                drawBezierCurve(
                    size = size,
                    points = points,
                    fixedMinPoint = minPoint,
                    fixedMaxPoint = maxPoint,
                    style = style,
                    touchPoint = tapPosition,
                    isTouched = isTouched,
                )
            }
        },
    )
}

private fun DrawScope.drawBezierCurve(
    size: IntSize,
    points: List<Float>,
    fixedMinPoint: Float? = null,
    fixedMaxPoint: Float? = null,
    style: BezierCurveStyle,
    touchPoint: Offset? = null,
    isTouched: Boolean = false,
) {
    val maxPoint = fixedMaxPoint ?: points.max()
    val minPoint = fixedMinPoint ?: points.min()
    val total = maxPoint - minPoint
    val height = size.height
    val width = size.width
    val xSpacing = width / (points.size - 1F)
    var lastPoint: Offset? = null
    val path = Path()
    var firstPoint = Offset(0F, 0F)
    val paint = Paint().apply {
        textAlign = Paint.Align.CENTER
        textSize = 24f
        color = Color.Red.toArgb()
    }
    for (index in points.indices) {
        val x = index * xSpacing
        val y = height - height * ((points[index] - minPoint) / total)
        /*Draw curve line*/
        if (lastPoint != null) {
            buildCurveLine(path, lastPoint, Offset(x, y))
        }
        lastPoint = Offset(x, y)
        /*Go to start line*/
        if (index == 0) {
            path.moveTo(x, y)
            firstPoint = Offset(x, y)
        }
        /*Draw columns*/
        drawLine(
            color = Color(0xFFFF5722),
            start = Offset(x, y),
            end = Offset(x, height.toFloat()),
            cap = StrokeCap.Round,
            strokeWidth = 2f,
            pathEffect = PathEffect.dashPathEffect(intervals = floatArrayOf(10f, 10f), phase = 1f)
        )
        Logger.i("Gap (x - y): (${abs(touchPoint?.x?.minus(x) ?: 0f)} - ${abs(touchPoint?.y?.minus(y) ?: 0f)})")
        if (isTouched) {
            if (inRangePointTouch(touchPoint = touchPoint, pointInPath = Offset(x, y))) {
                drawContext.canvas.nativeCanvas.drawText("${points[index]}", x, y - 10, paint)
                /*drawRect(
                    modifier = Modifier
                        .offset(x = x.dp, y = (y + 20).dp) // Offset for better visibility
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .padding(8.dp)
                        .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                ) {
                    Text(text = "${points[index]}")
                }*/
            }
        }
    }
    /*Draw line bottom*/
    drawLine(
        color = Color(0xFFFF5722),
        start = Offset(0f, height.toFloat()),
        end = Offset(width.toFloat(), height.toFloat()),
        cap = StrokeCap.Round,
        strokeWidth = 2f,
        pathEffect = PathEffect.dashPathEffect(intervals = floatArrayOf(10f, 10f), phase = 1f)
    )
    fun closeWithBottomLine() {
        path.lineTo(width.toFloat(), height.toFloat())
        path.lineTo(0F, height.toFloat())
        path.lineTo(firstPoint.x, firstPoint.y)
    }

    when (style) {
        is BezierCurveStyle.Fill -> {
            closeWithBottomLine()
            drawPath(
                path = path,
                style = Fill,
                brush = style.brush,
            )
        }

        is BezierCurveStyle.CurveStroke -> {
            drawPath(
                path = path,
                brush = style.brush,
                style = style.stroke,
            )
        }

        is BezierCurveStyle.StrokeAndFill -> {
            drawPath(
                path = path,
                brush = style.strokeBrush,
                style = style.stroke,
            )
            closeWithBottomLine()
            drawPath(
                path = path,
                brush = style.fillBrush,
                style = Fill,
            )
        }
    }
}

fun inRangePointTouch(touchPoint: Offset?, pointInPath: Offset, gap: Float = 20f) =
    abs(touchPoint?.x?.minus(pointInPath.x) ?: 0f) < gap
            && abs(touchPoint?.y?.minus(pointInPath.y) ?: 0f) < gap

private fun buildCurveLine(path: Path, startPoint: Offset, endPoint: Offset) {
    val firstControlPoint = Offset(
        x = startPoint.x + (endPoint.x - startPoint.x) / 2F,
        y = startPoint.y,
    )
    val secondControlPoint = Offset(
        x = startPoint.x + (endPoint.x - startPoint.x) / 2F,
        y = endPoint.y,
    )
    path.cubicTo(
        x1 = firstControlPoint.x,
        y1 = firstControlPoint.y,
        x2 = secondControlPoint.x,
        y2 = secondControlPoint.y,
        x3 = endPoint.x,
        y3 = endPoint.y,
    )
}

sealed class BezierCurveStyle {

    class Fill(val brush: Brush) : BezierCurveStyle()

    class CurveStroke(
        val brush: Brush,
        val stroke: Stroke,
    ) : BezierCurveStyle()

    class StrokeAndFill(
        val fillBrush: Brush,
        val strokeBrush: Brush,
        val stroke: Stroke,
    ) : BezierCurveStyle()
}

@Composable
fun SomeOfBeziersCurve() {
    BezierCurve(
        modifier = Modifier
            .fillMaxWidth()
            /*.padding(horizontal = 32.dp, vertical = 16.dp)*/
            .height(100.dp)
            .background(Color.White),
        points = listOf(10F, 30F, 80F, 10F, 20F, 90F, 10F, 60F, 20F),
        /*minPoint = 0F,
        maxPoint = 100F,*/
        style = BezierCurveStyle.CurveStroke(
            brush = Brush.horizontalGradient(listOf(Color(0x2200FF00), Color(0xFF00FF00))),
            stroke = Stroke(width = 6f)
        ),
    )
    Spacer(modifier = Modifier.padding(8.dp))
    Color(0xFFE66993)
    BezierCurve(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            /*.padding(horizontal = 32.dp, vertical = 16.dp)*/
            .background(Color.White),
        points = listOf(20F, 80F, 30F, 70F, 20F, 90F, 10F, 60F, 20F),
        /*minPoint = 0F,
        maxPoint = 100F,*/
        style = BezierCurveStyle.Fill(
            brush = Brush.verticalGradient(listOf(Color(0x3300FF00), Color(0xFF00FF00))),
        ),
    )
    Spacer(modifier = Modifier.padding(8.dp))
    BezierCurve(
        modifier = Modifier
            .fillMaxWidth()
            /*.padding(horizontal = 32.dp, vertical = 16.dp)*/
            .height(100.dp)
            .background(Color.White),
        points = listOf(20F, 80F, 30F, 70F, 20F, 90F, 10F, 60F, 20F),
        minPoint = 0F,
        maxPoint = 100F,
        style = BezierCurveStyle.StrokeAndFill(
            strokeBrush = Brush.horizontalGradient(listOf(Color.Red, Color.Blue)),
            fillBrush = Brush.verticalGradient(listOf(Color(0xFF00FF00), Color(0x3300FF00))),
            stroke = Stroke(width = 2f)
        ),
    )
    Spacer(modifier = Modifier.padding(8.dp))
    BezierCurve(
        modifier = Modifier
            .fillMaxWidth()
            /*.padding(horizontal = 32.dp, vertical = 16.dp)*/
            .height(100.dp)
            .background(Color.White),
        points = listOf(20F, 80F, 30F, 70F, 20F, 90F, 10F, 60F, 20F),
        minPoint = 0F,
        maxPoint = 100F,
        style = BezierCurveStyle.Fill(brush = SolidColor(Color.Blue)),
    )
}