package com.baka.composeapp.features.charts.barschart

import android.graphics.Paint
import android.graphics.RectF
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.baka.composeapp.features.charts.models.PieData
import com.baka.composeapp.features.charts.models.dataPie
import com.baka.composeapp.ui.bottomnavigation.models.DataPoint
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

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
    val state = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .horizontalScroll(state),
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

//https://archive.is/1DPom

@Composable
fun PieChart(data: List<PieData> = dataPie) {
    val totalChartDegree = 360f
    var startAngle = 270f
    val emptyIndex = -1
    val textColor = Color.White
    // calculate each input percentage
    val proportions = data.map {
        it.value * 100 / data.map { pie -> pie.value }.sum()
    }
    // calculate each input slice degrees
    val angleProgress = proportions.map { prop ->
        totalChartDegree * prop / 100
    }
    // clicked slice index
    var clickedItemIndex by remember {
        mutableIntStateOf(emptyIndex)
    }
    // calculate each slice end point in degrees, for handling click position
    val progressSize = mutableListOf<Float>()
    val density = LocalDensity.current
    // Modifier to handle scaling gesture
    val textFontSize = with(density) { 26.dp.toPx() }
    val textPaint = remember {
        Paint().apply {
            color = textColor.toArgb()
            textSize = textFontSize
            textAlign = Paint.Align.CENTER
        }
    }
    LaunchedEffect(angleProgress) {
        progressSize.add(angleProgress.first())
        for (x in 1 until angleProgress.size) {
            progressSize.add(angleProgress[x] + progressSize[x - 1])
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center
    ) {

        /* BoxWithConstraints
        val canvasSize = min(constraints.maxWidth, constraints.maxHeight)
        val size = Size(canvasSize.toFloat(), canvasSize.toFloat())
        val canvasSizeDp = with(density) { canvasSize.toDp() }*/

        Canvas(
            modifier = Modifier
                /*.fillMaxWidth()*/
                .aspectRatio(1f)
                .background(Color.White)
                .pointerInput(data) {
                    detectTapGestures { tapPoint ->
                        val clickedAngle = touchPointToAngle(
                            width = size.width.toFloat(),
                            height = size.width.toFloat(),
                            touchX = tapPoint.x,
                            touchY = tapPoint.y,
                            chartDegrees = totalChartDegree,
                        )
                        progressSize.forEachIndexed { index, angle ->
                            if (clickedAngle <= angle) {
                                clickedItemIndex = index
                                return@detectTapGestures
                            }
                        }

                    }
                }
        ) {
            val center = Offset(size.width / 2, size.height / 2)
            //Draw chart
            angleProgress.forEachIndexed { index, angle ->
                drawArc(
                    color = data[index].color,
                    startAngle = startAngle,
                    sweepAngle = angle,
                    useCenter = true,
                    size = size / 2f,
                    style = Fill
                )
                startAngle += angle
            }
            /*Draw text when touch item of chart*/
            if (clickedItemIndex != emptyIndex) {
                drawIntoCanvas { drawScope ->
                    drawScope.nativeCanvas.drawText(
                        "${proportions[clickedItemIndex].roundToInt()}%",
                        (size.width / 2) + textFontSize / 4,
                        (size.width / 2) + textFontSize / 4,
                        textPaint
                    )
                }
            }

        }

    }
}

internal fun touchPointToAngle(
    width: Float,
    height: Float,
    touchX: Float,
    touchY: Float,
    chartDegrees: Float,
): Double {
    val x = touchX - (width * 0.5f)
    val y = touchY - (height * 0.5f)
    val angle = Math.toDegrees(atan2(y.toDouble(), x.toDouble()) + Math.PI / 2)
    return if (angle < 0) {
        angle + chartDegrees
    } else {
        angle
    }
}


@Composable
fun ScalablePieChart(data: List<Float> = dataPie.map { it.value }) {
    // Initial scale factor
    var scale by remember { mutableFloatStateOf(1f) }
    // Sum up the data to calculate pie slices
    val total = data.sum()
    // Modifier to handle scaling gesture
    val scaleModifier = Modifier.pointerInput(Unit) {
        detectTransformGestures { _, _, zoom, _ ->
            scale = (scale * zoom).coerceIn(0.5f, 3f) // Limit scale factor between 0.5x and 3x
        }
    }
    // Drawing the pie chart
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .background(Color.White)
            .then(scaleModifier) // Apply gesture modifier
    ) {
        Canvas(modifier = Modifier.aspectRatio(1f)) {
            val center = Offset(size.width / 2, size.height / 2)
            var startAngle = 0f

            data.forEach { value ->
                val sweepAngle = (value / total) * 360f

                scale(scale, center) {  // Apply scale around the center of the pie chart
                    drawArc(
                        color = Color(
                            red = (0..255).random(),
                            green = (0..255).random(),
                            blue = (0..255).random()
                        ),
                        startAngle = startAngle,
                        sweepAngle = sweepAngle,
                        useCenter = true,
                        topLeft = Offset(center.x - 200f, center.y - 200f),
                        size = Size(400f, 400f)
                    )
                }
                startAngle += sweepAngle
            }
        }
    }
}

@Composable
fun InteractivePieChart(data: List<Float> = dataPie.map { it.value }) {
    // Total value for calculating percentages
    val total = data.sum()
    // State to track the index of the touched slice
    var selectedSliceIndex by remember { mutableStateOf<Int?>(null) }
    // Animation scale factor for the selected slice
    val selectedSliceScale by animateFloatAsState(
        targetValue = if (selectedSliceIndex != null) 1.1f else 1f, label = "scale"
    )
    // Detect touch on the pie chart
    val pieTouchModifier = Modifier.pointerInput(Unit) {
        detectTapGestures { tapOffset ->
            val center = Offset(size.width / 2f, size.height / 2f)
            val touchAngle = calculateTouchAngle(center, tapOffset)

            // Calculate which slice the touch corresponds to
            var startAngle = 0f
            selectedSliceIndex = data.indexOfFirst { value ->
                val sweepAngle = (value / total) * 360f
                val isTouched = touchAngle in startAngle..(startAngle + sweepAngle)
                startAngle += sweepAngle
                isTouched
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .background(Color.White)
            .then(pieTouchModifier) // Attach touch modifier
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            val center = Offset(size.width / 2, size.height / 2)
            var startAngle = 0f

            // Draw each slice
            data.forEachIndexed { index, value ->
                val sweepAngle = (value / total) * 360f
                val isSelected = index == selectedSliceIndex
                val sliceScale = if (isSelected) selectedSliceScale else 1f

                // Calculate the offset to expand the slice outward
                val offset = if (isSelected) Offset(
                    20f * cos(Math.toRadians((startAngle + sweepAngle / 2).toDouble())).toFloat(),
                    20f * sin(Math.toRadians((startAngle + sweepAngle / 2).toDouble())).toFloat()
                ) else Offset.Zero

                // Draw the slice with optional offset for selected slice
                drawArc(
                    color = Color(
                        red = (0..255).random(),
                        green = (0..255).random(),
                        blue = (0..255).random()
                    ),
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true,
                    topLeft = center - Offset(200f, 200f) + offset * sliceScale,
                    size = Size(400f, 400f) * sliceScale
                )
                startAngle += sweepAngle
            }
        }
    }
}

// Function to calculate angle of touch point relative to chart center
fun calculateTouchAngle(center: Offset, tapOffset: Offset): Float {
    val dx = tapOffset.x - center.x
    val dy = tapOffset.y - center.y
    val angle = Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())).toFloat()
    return (angle + 360) % 360 // Normalize angle to [0, 360)
}

