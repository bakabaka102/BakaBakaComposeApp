package com.baka.composeapp.ui.bottomnavigation

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.baka.composeapp.helper.Logger


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
        Spacer(modifier = Modifier.padding(8.dp))
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
                drawArc(paintColor, 0f, 270f, true)
            })
        }

        Spacer(modifier = Modifier.padding(8.dp))
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
        Spacer(modifier = Modifier.padding(8.dp))
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

        Spacer(modifier = Modifier.padding(8.dp))
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
        Spacer(modifier = Modifier.padding(8.dp))
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

        Spacer(modifier = Modifier.padding(8.dp))
        Column(modifier = Modifier.size(300.dp)) {
            val colorPaint = Color(0xFFBA1122)
            val path = Path().apply {
                moveTo(50f, 50f)
                lineTo(300f, 300f)
                lineTo(550f, 50f)
                close()
            }
            Canvas(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp),
                onDraw = {
                    drawPath(
                        path = path,
                        color = colorPaint,
                        style = Stroke(width = 8f, cap = StrokeCap.Round),
                    )
                })
        }

        Spacer(modifier = Modifier.padding(8.dp))
        Column(modifier = Modifier.size(300.dp)) {
            val colorPaint = Color(0xFFE0CB10)
            val path = Path().apply {
                moveTo(50f, 50f)
                lineTo(300f, 300f)
                lineTo(550f, 50f)
                close()
            }
            Canvas(modifier = Modifier
                .padding(16.dp),
                onDraw = {
                    drawPath(
                        path = path,
                        color = colorPaint,
                        style = Stroke(width = 8f, cap = StrokeCap.Round),
                    )
                })
        }

        Spacer(modifier = Modifier.padding(8.dp))
        Column(
            modifier = Modifier
                .size(300.dp)
                .background(Color.White),
        ) {
            //https://proandroiddev.com/line-chart-ui-with-jetpack-compose-a-simple-guide-f9b8b80efc83
            DrawLineList()
        }
    }
}

@Composable
fun DrawLineList(
    list: List<Float> = listOf(5f, 6f, 3f, 1f, 2f, 4f, 3f)
) {
    val zipList: List<Pair<Float, Float>> = list.zipWithNext().also {
        Logger.d("Value of list point: $it")
    }

    Row {
        val max = list.max()
        val min = list.min()

        val lineColor =
            if (list.last() > list.first()) Color(0xFF2596be) else Color(0xFF063970)

        for (pair in zipList) {

            val fromValuePercentage = getValuePercentageForRange(pair.first, max, min)
            val toValuePercentage = getValuePercentageForRange(pair.second, max, min)

            Canvas(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                onDraw = {
                    val fromPoint =
                        Offset(x = 0f, y = size.height.times(1 - fromValuePercentage))
                    val toPoint =
                        Offset(x = size.width, y = size.height.times(1 - toValuePercentage))

                    drawLine(
                        color = lineColor,
                        start = fromPoint,
                        end = toPoint,
                        strokeWidth = 3f,
                        cap = StrokeCap.Round,
                    )
                })
        }
    }
}

private fun getValuePercentageForRange(value: Float, max: Float, min: Float) =
    (value - min) / (max - min)