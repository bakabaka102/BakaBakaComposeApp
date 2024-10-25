package com.baka.composeapp.ui.drawermenu.screen.notification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.baka.composeapp.features.animations.RotationClock
import com.baka.composeapp.features.charts.lines.BezierCurve
import com.baka.composeapp.features.charts.lines.BezierCurveStyle

@Composable
fun AnimationScreenPager() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Spacer(modifier = Modifier.padding(8.dp))
        RotationClock()
    }
}

@Composable
fun ShoppingScreenPager() {
    /*Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val points = listOf(
            Pair(100f, 300f),
            Pair(150f, 100f),
            Pair(250f, 500f),
            Pair(400f, 300f),
            Pair(500f, 100f),
            Pair(600f, 400f)
        )
    }*/

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BezierCurve(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
                .height(100.dp),
            points = listOf(10F, 30F, 80F, 10F, 20F, 90F, 10F, 60F, 20F),
            minPoint = 0F,
            maxPoint = 100F,
            style = BezierCurveStyle.CurveStroke(
                brush = Brush.horizontalGradient(listOf(Color(0x2200FF00), Color(0xFF00FF00))),
                stroke = Stroke(width = 6f)
            ),
        )
        BezierCurve(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, top = 30.dp, end = 30.dp)
                .height(100.dp),
            points = listOf(20F, 80F, 30F, 70F, 20F, 90F, 10F, 60F, 20F),
            minPoint = 0F,
            maxPoint = 100F,
            style = BezierCurveStyle.Fill(
                brush = Brush.verticalGradient(listOf(Color(0x3300FF00), Color(0xFF00FF00))),
            ),
        )
        BezierCurve(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, top = 30.dp, end = 30.dp)
                .height(100.dp),
            points = listOf(20F, 80F, 30F, 70F, 20F, 90F, 10F, 60F, 20F),
            minPoint = 0F,
            maxPoint = 100F,
            style = BezierCurveStyle.StrokeAndFill(
                strokeBrush = Brush.horizontalGradient(listOf(Color.Red, Color.Blue)),
                fillBrush = Brush.verticalGradient(listOf(Color(0xFF00FF00), Color(0x3300FF00))),
                stroke = Stroke(width = 2f)
            ),
        )
        BezierCurve(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, top = 30.dp, end = 30.dp)
                .height(100.dp),
            points = listOf(20F, 80F, 30F, 70F, 20F, 90F, 10F, 60F, 20F),
            minPoint = 0F,
            maxPoint = 100F,
            style = BezierCurveStyle.Fill(brush = SolidColor(Color.Blue)),
        )
    }
}

@Composable
fun AccountScreenPager() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "AccountScreenPager")
    }
}
