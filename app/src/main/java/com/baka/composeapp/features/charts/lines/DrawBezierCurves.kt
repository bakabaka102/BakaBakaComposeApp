package com.baka.composeapp.features.charts.lines

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.dp

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
                    brush = Brush.verticalGradient(listOf(Color.Blue, Color.Transparent)),
                    /*style = Stroke(width = 6f, cap = StrokeCap.Round),*/
                    style = Fill,
                )
            }
        })
    //https://viblo.asia/p/cai-tien-bottomnavigationview-trong-android-gGJ59bLrKX2
    //Spacer(modifier = Modifier.padding(8.dp))
}
