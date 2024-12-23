package com.baka.composeapp.features.animations

import android.graphics.PointF
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.baka.composeapp.features.createPolygonPath

//https://archive.is/cEAZ5
//https://archive.is/qgj3R
//file: PolygonShape.kt
@Composable
fun AnimationDrawPathPolygons() {
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 64.dp)
            .background(Color.White), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val pathMeasure = PathMeasure()
        var mainPath = Path()
        // Animation draw Polygon
        val infiniteTransition = rememberInfiniteTransition(label = "")
        val animProgress by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(3000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ), label = ""
        )
        //End Animation draw Polygon
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            //Color(0xFF068B7E)
            val center = PointF(size.width / 2, size.width / 2)
            listOf(
                Polygon(sides = 3, radius = 100f, color = 0xffe84c65),
                Polygon(sides = 4, radius = 160f, color = 0xffe79442),
                Polygon(sides = 5, radius = 220f, color = 0xff620872),
                Polygon(sides = 6, radius = 280f, color = 0xff068B7E),
            ).forEach { polygon ->
                mainPath = createPolygonPath(
                    center = center,
                    sides = polygon.sides,
                    radius = polygon.radius
                )
                val pathEffect = if (polygon.sides % 2 == 0) {
                    PathEffect.dashPathEffect(floatArrayOf(20f, 10f), 0f)
                } else {
                    PathEffect.cornerPathEffect(12f)
                }
                // Apply animation draw Polygon
                pathMeasure.setPath(mainPath, false)
                val pathLength = pathMeasure.length
                val pathPortion = Path()
                pathMeasure.getSegment(0f, animProgress * pathLength, pathPortion, true)
                //End Apply animation draw Polygon
                drawPath(
                    color = Color(polygon.color),
                    path = pathPortion, /*mainPath*/
                    style = Stroke(width = 8f, cap = StrokeCap.Round, pathEffect = pathEffect),
                )
            }
        }

    }
}
