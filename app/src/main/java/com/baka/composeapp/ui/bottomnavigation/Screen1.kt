package com.baka.composeapp.ui.bottomnavigation

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.compose.ui.platform.LocalDensity
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
        Spacer(modifier = Modifier.padding(8.dp))
        DrawPath()
        //https://proandroiddev.com/line-chart-ui-with-jetpack-compose-a-simple-guide-f9b8b80efc83
        Spacer(modifier = Modifier.padding(8.dp))
        DrawLines()
        //https://dev.to/tkuenneth/drawing-and-painting-in-jetpack-compose-1-2okl
        Spacer(modifier = Modifier.padding(8.dp))
        SinCosPath()
        Spacer(modifier = Modifier.padding(8.dp))
        Column(modifier = Modifier.size(300.dp)) {
            GenericShape { size, layoutDirection ->
                moveTo(size.width / 2f, 0f)
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))

    }
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
            .background(Color.White)
            .padding(24.dp)
    ) {
        Canvas(modifier = Modifier.size(200.dp), onDraw = {
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
            .background(Color.White)
            .padding(24.dp)
    ) {
        Canvas(
            modifier = Modifier.size(200.dp)
        ) {
            drawRect(
                topLeft = Offset(50f, 50f),
                size = Size(300f, 300f),
                color = Color.Red,
                alpha = 1f,
                style = Fill,
            )
        }
    }
}

@Composable
private fun DrawPath() {
    Column(modifier = Modifier.size(300.dp)) {
        val colorPaint = Color(0xFFBA1122)
        val path = Path().apply {
            moveTo(50f, 50f)
            lineTo(300f, 600f)
            lineTo(550f, 50f)
            close()
        }
        Canvas(modifier = Modifier.padding(16.dp),
            onDraw = {
                drawPath(
                    path = path,
                    color = colorPaint,
                    style = Stroke(width = 8f, cap = StrokeCap.Round),
                )
            })
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
}