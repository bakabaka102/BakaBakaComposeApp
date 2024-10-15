package com.baka.composeapp.ui.bottomnavigation

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.baka.composeapp.helper.Logger
import kotlin.math.PI
import kotlin.math.sin

@Composable
fun Screen1() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DrawRectangle()
        Spacer(modifier = Modifier.padding(8.dp))
        DrawCircle()
        Spacer(modifier = Modifier.padding(8.dp))
        DrawPackMan()
        Spacer(modifier = Modifier.padding(8.dp))
        DrawCircleBeveledEdge()
        Spacer(modifier = Modifier.padding(8.dp))
        DrawLine()
        Spacer(modifier = Modifier.padding(8.dp))
        DrawOval1()
        Spacer(modifier = Modifier.padding(8.dp))
        DrawOval2()
        //https://proandroiddev.com/line-chart-ui-with-jetpack-compose-a-simple-guide-f9b8b80efc83
        Spacer(modifier = Modifier.padding(8.dp))
        DrawLines()
        //https://dev.to/tkuenneth/drawing-and-painting-in-jetpack-compose-1-2okl
        Spacer(modifier = Modifier.padding(8.dp))
        SinCosPath()
        Spacer(modifier = Modifier.padding(8.dp))
        DrawSpiltFourCircle()
        Spacer(modifier = Modifier.padding(8.dp))
        DrawBezierCurves()
        Spacer(modifier = Modifier.padding(8.dp))
        DrawWithContent()
        Spacer(modifier = Modifier.padding(8.dp))
        ModifierDrawWithCache()
        Spacer(modifier = Modifier.padding(80.dp))
    }
}

@Composable
fun DrawSpiltFourCircle(width: Float = 300f, height: Float = 300f) {
    Column(
        modifier = Modifier
            .size(width = width.dp, height = height.dp)
            .background(Color.White)
    ) {
        val padding = 24f
        val density = LocalDensity.current
        val textPaint = remember(density) {
            Paint().apply {
                /*color = android.graphics.Color.BLACK*/
                color = Color(0xFF91085B).toArgb()
                textAlign = Paint.Align.CENTER
                textSize = density.run { 24.sp.toPx() }
                strokeWidth = density.run { 4.sp.toPx() }
                isAntiAlias = true
            }
        }
        Canvas(
            modifier = Modifier
                .fillMaxSize()
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
                size.width + density.run { padding.sp.toPx().div(2) },
                size.height.div(2) + density.run { textPaint.textSize.div(2) },
                textPaint,
            )
            drawArc(Color(0xFF60fcdc), 0f, 90f, true)
            drawContext.canvas.nativeCanvas.drawText(
                "270",
                size.width.div(2),
                density.run { textPaint.textSize.div(2) },
                textPaint,
            )
            drawArc(Color(0xFFfc9a57), 90f, 90f, true)
            drawContext.canvas.nativeCanvas.drawText(
                "90",
                size.width.div(2),
                size.height - density.run { textPaint.textSize.div(2) },
                textPaint,
            )
            drawArc(Color(0xFFfef259), -90f, -90f, true)
            drawContext.canvas.nativeCanvas.drawText(
                "180",
                density.run { padding.sp.toPx().div(2) },
                size.height.div(2) + density.run { textPaint.textSize.div(2) },
                textPaint,
            )
        }
    }
}

@Composable
@Preview
fun ModifierDrawWithCache() {
    //https://developer.android.com/develop/ui/compose/graphics/draw/modifiers
    // [START android_compose_graphics_modifiers_drawWithCache]
    Text(
        color = Color.White,
        text = "Hello Compose!",
        modifier = Modifier
            /*.padding(horizontal = 12.dp, vertical = 6.dp)*/
            .drawWithCache {
                val brush = Brush.linearGradient(
                    listOf(
                        Color(0xFF9E82F0),
                        Color(0xFF42A5F5),
                    )
                )
                onDrawBehind {
                    drawRoundRect(
                        brush = brush,
                        cornerRadius = CornerRadius(10.dp.toPx()),
                    )
                }
            }
            .padding(12.dp)
    )
    // [END android_compose_graphics_modifiers_drawWithCache]
}


@Composable
@Preview
fun DrawWithContent(width: Float = 300f, height: Float = 300f) {
    // [START android_compose_graphics_modifiers_drawWithContent]
    //https://developer.android.com/develop/ui/compose/graphics/draw/modifiers
    var pointerOffset by remember {
        mutableStateOf(Offset(0f, 0f))
    }
    Column(
        modifier = Modifier
            .size(width.dp)
            .pointerInput("dragging") {
                detectDragGestures { change, dragAmount ->
                    pointerOffset += dragAmount
                }
            }
            .onSizeChanged {
                pointerOffset = Offset(it.width / 2f, it.height / 2f)
            }
            .drawWithContent {
                drawContent()
                // draws a fully black area with a small keyhole at pointerOffset that’ll show part of the UI.
                drawRect(
                    Brush.radialGradient(
                        listOf(Color.Transparent, Color.Black),
                        center = pointerOffset,
                        radius = 100.dp.toPx(),
                    )
                )
            }
    ) {
        // Your composables here
        Box(modifier = Modifier.size(width.dp)) {
            Text(
                text = "This is a demo text, it is hidden by a dark color.",
                style = TextStyle(fontSize = 24.sp, color = Color.Red, textAlign = TextAlign.Center)
            )
        }
    }
    // [END android_compose_graphics_modifiers_drawWithContent]
}

@Composable
fun DrawBezierCurves(height: Float = 80f, width: Float = 330f) {
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
                    brush = Brush.verticalGradient(listOf(Color.Blue, Color.Transparent)),
                    /*style = Stroke(width = 6f, cap = StrokeCap.Round),*/
                    style = Fill,
                )
            }
        })

    //https://viblo.asia/p/cai-tien-bottomnavigationview-trong-android-gGJ59bLrKX2
    Spacer(modifier = Modifier.padding(8.dp))

}


@Composable
private fun DrawOval2() {
    Surface(onClick = { /*TODO*/ }) {
        val colorPaint = Color(0xFFBA1122)
        Canvas(modifier = Modifier
            .size(200.dp)
            .padding(16.dp), onDraw = {
            drawOval(
                color = colorPaint,
                topLeft = Offset.Zero,
                size = Size(size.width - 10f, size.height - 80f)
            )
        })
    }
}

@Composable
private fun DrawOval1() {
    Surface(onClick = { /*TODO*/ }) {
        Canvas(modifier = Modifier
            .size(200.dp)
            .padding(16.dp), onDraw = {
            drawOval(
                brush = Brush.linearGradient(listOf(Color.Red, Color.Blue)),
                topLeft = Offset(10f, 10f),
                size = Size(size.width - 10f, size.height - 10f)
            )
        })
    }
}

@Composable
private fun DrawLine() {
    Surface(onClick = { /*TODO*/ }) {
        val colorPaint = Color(0xFFBA6688)
        Canvas(modifier = Modifier
            .fillMaxWidth(0.5f)
            .height(100.dp)
            .padding(16.dp), onDraw = {
            drawLine(
                color = colorPaint,
                start = Offset.Zero,
                end = Offset(200f, 50f),
                strokeWidth = 24f,
                cap = StrokeCap.Round
            )
        })
    }
}

@Composable
private fun DrawCircleBeveledEdge() {
    Surface(
        onClick = { /*TODO*/ },
    ) {
        val paintColor = Color(0xFF460252)
        Canvas(modifier = Modifier
            .size(200.dp)
            .padding(16.dp), onDraw = {
            drawArc(paintColor, 0f, 270f, false)
        })
    }
}

@Composable
private fun DrawPackMan() {
    Surface(
        onClick = { /*TODO*/ },
        /* modifier = Modifier
                 .fillMaxWidth()
                 .fillMaxHeight()
                 .background(Color.LightGray)*/
    ) {
        val paintColor = Color(0xFFBA68C8)
        Canvas(modifier = Modifier
            .size(200.dp)
            .padding(16.dp), onDraw = {
            drawArc(paintColor, 30f, 270f, true)
        })
    }
}

@Composable
private fun DrawCircle() {
    Surface(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxSize()
    ) {
        Canvas(modifier = Modifier
            .size(200.dp)
            .padding(24.dp), onDraw = {
            drawCircle(Color(0xFFAAFF00))
        })
    }
}

@Composable
private fun DrawRectangle() {
    Surface(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxSize()
    ) {
        Canvas(
            modifier = Modifier
                .size(200.dp)
                .padding(24.dp)
        ) {
            drawRect(
                topLeft = Offset(50f, 50f),
                /*size = Size(300f, 300f),*/
                color = Color.Red,
                alpha = 1f,
                style = Fill,
            )
        }
    }
}

@Composable
private fun DrawLines() {
    val list: List<Float> = listOf(5f, 6f, 3f, 1f, 2f, 4f, 3f)
    Column(modifier = Modifier.size(300.dp)) {
        /*val linePoints = listOf(
                Pair(300f, 600f), Pair(400f, 400f),
                Pair(175f, 200f), Pair(80f, 10f)
            )*/
        /*val path = Path().apply {
            moveTo(50f, 50f)
            lineTo(300f, 600f)
            lineTo(550f, 50f)
            close()
        }*/
        val path = Path().apply {
            moveTo(300f, 600f)
            lineTo(400f, 400f)
            lineTo(175f, 200f)
            lineTo(80f, 10f)
            close()
        }
        Canvas(
            modifier = Modifier
                .size(300.dp)
                .background(Color(0xFFEFF3F1))
                .padding(10.dp)
        ) {
            // Draw a rectangle
            drawRect(color = Color(0xFFAA7800))
            // Draw a circle
            drawCircle(color = Color(0xFFAADD00), radius = 200f)
            // Draw a custom path
            drawPath(
                path = path,
                brush = Brush.horizontalGradient(
                    listOf(
                        Color.Blue,
                        Color.Magenta
                    )
                ),
                style = Stroke(width = 8f, cap = StrokeCap.Round)
            )
        }
    }

    Spacer(modifier = Modifier.padding(8.dp))
    Column(
        modifier = Modifier
            .size(300.dp)
            .background(Color.White),
    ) {
        val zipList: List<Pair<Float, Float>> = list.zipWithNext().also {
            Logger.d("Value of list point: $it")
        }

        Row {
            val max = list.max()
            val min = list.min()
            val density = LocalDensity.current
            val textPaint = remember(density) {
                Paint().apply {
                    color = Color(0xFF91085B).toArgb()
                    textAlign = Paint.Align.CENTER
                    textSize = density.run { 12.sp.toPx() }
                }
            }
            Canvas(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                onDraw = {
                    val yAxisSpace = size.height / list.size
                    val paddingSpace = 16
                    for (i in 0 until 5) {
                        drawContext.canvas.nativeCanvas.drawText(
                            "$i",
                            paddingSpace.dp.toPx() / 2f,
                            size.height - yAxisSpace * (i + 1),
                            textPaint
                        )
                        drawLine(
                            color = Color(0xFF91085B),
                            start = Offset(0f, size.height - paddingSpace),
                            end = Offset(
                                0f,
                                size.height - (yAxisSpace * (i + 1))
                            ),
                            strokeWidth = 24f,
                            cap = StrokeCap.Round
                        )
                    }

                })
        }
    }
}

@Composable
private fun SinCosPath() {
    Canvas(modifier = Modifier
        .size(300.dp)
        .padding(16.dp)
        .background(Color.White),
        onDraw = {
            val middleW = size.width / 2
            val middleH = size.height / 2
            drawLine(Color.Gray, Offset(0f, middleH), Offset(size.width - 1, middleH))
            drawLine(Color.Gray, Offset(middleW, 0f), Offset(middleW, size.height - 1))
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