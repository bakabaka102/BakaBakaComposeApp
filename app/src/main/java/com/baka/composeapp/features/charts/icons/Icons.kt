package com.baka.composeapp.features.charts.icons

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp

@Composable
fun InstagramIcon() {
    val instagramColors = listOf(Color.Yellow, Color.Red, Color.Magenta)
    Canvas(
        modifier = Modifier
            .size(160.dp)
            .background(Color.White)
            .padding(16.dp)
    ) {
        drawRoundRect(
            brush = Brush.linearGradient(colors = instagramColors),
            cornerRadius = CornerRadius(60f, 60f),
            style = Stroke(width = 10f, cap = StrokeCap.Round)
        )
        drawCircle(
            brush = Brush.linearGradient(colors = instagramColors),
            radius = 46f,
            style = Stroke(width = 10f, cap = StrokeCap.Round)
        )
        drawCircle(
            brush = Brush.linearGradient(colors = instagramColors),
            radius = 12f,
            center = Offset(this.size.width * .80f, this.size.height * 0.20f),
        )
    }
}

@Composable
fun FacebookIcon() {
    /*val assetManager = LocalContext.current.assets*/
    val paint = Paint().apply {
        textAlign = Paint.Align.CENTER
        textSize = 280f
        color = Color.White.toArgb()
        /* typeface = Typeface.createFromAsset(assetManager, "FACEBOLF.OTF")*/
    }
    Canvas(
        modifier = Modifier
            .size(160.dp)
            .background(Color.White)
            .padding(16.dp)
    ) {
        drawRoundRect(
            color = Color(0xFF1776d1),
            cornerRadius = CornerRadius(20f, 20f)
        )
        drawContext.canvas.nativeCanvas.drawText("f", center.x + 25, center.y + 90, paint)
    }
}

@Composable
fun MessengerIcon() {
    val colors = listOf(Color(0xFF02b8f9), Color(0xFF0277fe))
    Canvas(
        modifier = Modifier
            .size(160.dp)
            .background(Color.White)
            .padding(16.dp)
    ) {

        val trianglePath = Path().let {
            it.moveTo(this.size.width * .20f, this.size.height * .77f)
            it.lineTo(this.size.width * .20f, this.size.height * 0.95f)
            it.lineTo(this.size.width * .37f, this.size.height * 0.86f)
            it.close()
            it
        }

        val electricPath = Path().let {
            it.moveTo(this.size.width * .20f, this.size.height * 0.60f)
            it.lineTo(this.size.width * .45f, this.size.height * 0.35f)
            it.lineTo(this.size.width * 0.56f, this.size.height * 0.46f)
            it.lineTo(this.size.width * 0.78f, this.size.height * 0.35f)
            it.lineTo(this.size.width * 0.54f, this.size.height * 0.60f)
            it.lineTo(this.size.width * 0.43f, this.size.height * 0.45f)
            it.close()
            it
        }

        drawOval(
            brush = Brush.verticalGradient(colors = colors),
            size = Size(this.size.width, this.size.height * 0.95f)
        )

        drawPath(
            path = trianglePath,
            brush = Brush.verticalGradient(colors = colors),
            style = Stroke(width = 15f, cap = StrokeCap.Round)
        )
        drawPath(path = electricPath, color = Color.White)
    }
}

@Composable
fun GooglePhotosIcon() {
    Canvas(
        modifier = Modifier
            .size(160.dp)
            .background(Color.White)
            .padding(16.dp)
    ) {
        drawArc(
            color = Color(0xFFf04231),
            startAngle = -90f,
            sweepAngle = 180f,
            useCenter = true,
            size = Size(size.width * .50f, size.height * .50f),
            topLeft = Offset(size.width * .25f, 0f)
        )
        drawArc(
            color = Color(0xFF4385f7),
            startAngle = 0f,
            sweepAngle = 180f,
            useCenter = true,
            size = Size(size.width * .50f, size.height * .50f),
            topLeft = Offset(size.width * .50f, size.height * .25f)
        )
        drawArc(
            color = Color(0xFF30a952),
            startAngle = 0f,
            sweepAngle = -180f,
            useCenter = true,
            size = Size(size.width * .50f, size.height * .50f),
            topLeft = Offset(0f, size.height * .25f)
        )

        drawArc(
            color = Color(0xFFffbf00),
            startAngle = 270f,
            sweepAngle = -180f,
            useCenter = true,
            size = Size(size.width * .50f, size.height * .50f),
            topLeft = Offset(size.width * .25f, size.height * .50f)
        )
    }
}

@Composable
fun WeatherAppIcon() {
    val backgroundColor = listOf(Color(0xFF2078EE), Color(0xFF74E6FE))
    val sunColor = listOf(Color(0xFFFFC200), Color(0xFFFFE100))
    Canvas(
        modifier = Modifier
            .size(160.dp)
            .background(Color.White)
            .padding(16.dp)
    ) {
        val width = size.width
        val height = size.height
        val path = Path().apply {
            moveTo(width.times(.76f), height.times(.72f))
            cubicTo(
                x1 = width.times(.93f),
                y1 = height.times(.72f),
                x2 = width.times(.98f),
                y2 = height.times(.41f),
                x3 = width.times(.76f),
                y3 = height.times(.40f)
            )
            cubicTo(
                x1 = width.times(.75f),
                y1 = height.times(.21f),
                x2 = width.times(.35f),
                y2 = height.times(.21f),
                x3 = width.times(.38f),
                y3 = height.times(.50f)
            )
            cubicTo(
                x1 = width.times(.25f),
                y1 = height.times(.50f),
                x2 = width.times(.20f),
                y2 = height.times(.69f),
                x3 = width.times(.41f),
                y3 = height.times(.72f)
            )
            close()
        }
        drawRoundRect(
            brush = Brush.verticalGradient(backgroundColor),
            cornerRadius = CornerRadius(50f, 50f),

            )
        drawCircle(
            brush = Brush.verticalGradient(sunColor),
            radius = width.times(.17f),
            center = Offset(width.times(.35f), height.times(.35f))
        )
        drawPath(path = path, color = Color.White.copy(alpha = .90f))
    }
}