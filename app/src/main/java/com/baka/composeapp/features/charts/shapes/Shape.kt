package com.baka.composeapp.features.charts.shapes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp


@Composable
fun DrawOval2() {
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
fun DrawMixedShaped() {
    Column(modifier = Modifier.size(200.dp)) {
        val path = Path().apply {
            moveTo(200f, 300f)
            lineTo(266f, 266f)
            lineTo(116f, 134f)
            lineTo(54f, 6f)
            close()
        }
        Canvas(
            modifier = Modifier
                .size(200.dp)
                .background(Color(0xFFEFF3F1))
                .padding(10.dp)
        ) {
            // Draw a rectangle
            drawRect(color = Color(0xFFAA7800))
            // Draw a circle
            drawCircle(color = Color(0xFFAADD00), radius = 160f)
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
}

@Composable
fun DrawOval1() {
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
fun DrawLine() {
    Surface(onClick = { /*TODO*/ }) {
        val colorPaint = Color(0xFFBA6688)
        Canvas(modifier = Modifier
            .size(200.dp)
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
fun DrawCircleBeveledEdge() {
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
fun DrawPackMan() {
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
fun DrawCircle() {
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
fun DrawRectangle() {
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
                size = Size(200f, 100f),
                color = Color.Red,
                alpha = 1f,
                style = Fill,
            )
        }
    }
}
