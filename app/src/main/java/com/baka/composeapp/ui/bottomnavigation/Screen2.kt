package com.baka.composeapp.ui.bottomnavigation

import android.graphics.Paint
import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.baka.composeapp.ui.bottomnavigation.models.DataPoint
import com.baka.composeapp.ui.models.GraphAppearance

@Composable
fun Screen2() {
//https://proandroiddev.com/exploring-canvas-in-jetpack-compose-crafting-graphics-animations-and-game-experiences-b0aa31160bff
//https://proandroiddev.com/creating-graph-in-jetpack-compose-312957b11b2

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Surface(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(24.dp)
        ) {
            BarChart()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(24.dp)
        ) {
            val yStep = 50
            /* to test with random points */
            /*val points = (0..9).map {
                var num = Random.nextInt(350)
                if (num <= 50)
                    num += 100
                num.toFloat()
            }*/
            /* to test with fixed points */
            val points = listOf(150f, 100f, 250f, 200f, 330f, 300f, 90f, 120f, 285f, 199f)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray)
            ) {
                Graph(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp),
                    xValues = (0..9).map { it + 1 },
                    yValues = (0..6).map { (it + 1) * yStep },
                    points = points,
                    paddingSpace = 16.dp,
                    verticalStep = yStep,
                    graphAppearance = GraphAppearance(
                        graphColor = Color.White,
                        graphAxisColor = MaterialTheme.colorScheme.primary,
                        graphThickness = 1f,
                        isColorAreaUnderChart = true,
                        colorAreaUnderChart = Color.Green,
                        isCircleVisible = true,
                        circleColor = MaterialTheme.colorScheme.secondary,
                        backgroundColor = MaterialTheme.colorScheme.background
                    )
                )
            }
        }
    }
}

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
fun Graph(
    modifier: Modifier,
    xValues: List<Int>,
    yValues: List<Int>,
    points: List<Float>,
    paddingSpace: Dp,
    verticalStep: Int,
    graphAppearance: GraphAppearance,
) {
    val controlPoints1 = mutableListOf<PointF>()
    val controlPoints2 = mutableListOf<PointF>()
    val coordinates = mutableListOf<PointF>()
    val density = LocalDensity.current
    val textPaint = remember(density) {
        Paint().apply {
            color = graphAppearance.graphAxisColor.toArgb()
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }
    Box(
        modifier = modifier
            .background(graphAppearance.backgroundColor)
            .padding(horizontal = 8.dp, vertical = 12.dp),
        contentAlignment = Center
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize(),
            onDraw = {
                val xAxisSpace = (size.width - paddingSpace.toPx()) / xValues.size
                val yAxisSpace = size.height / yValues.size
                //val yAxisSpace = size.height / yValues.size
                /** placing x axis points */
                for (i in xValues.indices) {
                    drawContext.canvas.nativeCanvas.drawText(
                        "${xValues[i]}",
                        xAxisSpace * (i + 1),
                        size.height - 30,
                        textPaint
                    )
                }
                /** placing y axis points */
                for (i in yValues.indices) {
                    drawContext.canvas.nativeCanvas.drawText(
                        "${yValues[i]}",
                        paddingSpace.toPx() / 2f,
                        size.height - yAxisSpace * (i + 1),
                        textPaint
                    )
                }
                /** placing our x axis points */
                for (i in points.indices) {
                    val x1 = xAxisSpace * xValues[i]
                    val y1 = size.height - (yAxisSpace * (points[i] / verticalStep.toFloat()))
                    coordinates.add(PointF(x1, y1))
                    /** drawing circles to indicate all the points */
                    if (graphAppearance.isCircleVisible) {
                        drawCircle(
                            color = graphAppearance.circleColor,
                            radius = 10f,
                            center = Offset(x1, y1)
                        )
                    }
                }
                /** calculating the connection points */
                for (i in 1 until coordinates.size) {
                    controlPoints1.add(
                        PointF(
                            (coordinates[i].x + coordinates[i - 1].x) / 2,
                            coordinates[i - 1].y
                        )
                    )
                    controlPoints2.add(
                        PointF(
                            (coordinates[i].x + coordinates[i - 1].x) / 2,
                            coordinates[i].y
                        )
                    )
                }
                /** drawing the path */
                val stroke = Path().apply {
                    reset()
                    moveTo(coordinates.first().x, coordinates.first().y)
                    for (i in 0 until coordinates.size - 1) {
                        cubicTo(
                            controlPoints1[i].x, controlPoints1[i].y,
                            controlPoints2[i].x, controlPoints2[i].y,
                            coordinates[i + 1].x, coordinates[i + 1].y
                        )
                    }
                }

                /** filling the area under the path */
                val fillPath = android.graphics.Path(stroke.asAndroidPath())
                    .asComposePath()
                    .apply {
                        lineTo(xAxisSpace * xValues.last(), size.height - yAxisSpace)
                        lineTo(xAxisSpace, size.height - yAxisSpace)
                        close()
                    }
                if (graphAppearance.isColorAreaUnderChart) {
                    drawPath(
                        path = fillPath,
                        brush = Brush.verticalGradient(
                            listOf(
                                graphAppearance.colorAreaUnderChart,
                                Color.Transparent,
                            ),
                            endY = size.height - yAxisSpace,
                        ),
                    )
                }
                drawPath(
                    stroke,
                    color = graphAppearance.graphColor,
                    style = Stroke(
                        width = graphAppearance.graphThickness,
                        cap = StrokeCap.Round
                    )
                )
            })
    }
}
