package com.baka.composeapp.features.animations

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp

////https://proandroiddev.com/exploring-canvas-in-jetpack-compose-crafting-graphics-animations-and-game-experiences-b0aa31160bff
@Composable
fun RotationClock() {
    val animationDuration = 1000
    val infiniteTransition = rememberInfiniteTransition(label = "pendulum")
    var mWidth by remember { mutableFloatStateOf(0f) }
    var mHeight by remember { mutableFloatStateOf(0f) }
    val radius = 50f

    val pendulumRotation by infiniteTransition.animateFloat(
        initialValue = 35f/*0f*/,
        targetValue = -35f/*360f*/,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ), "pupils_translation"
    )

    /*val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Yellow)
            .height(screenWidth) // Set height equal to screen width
    ) {
        Text("Height width View ratio 1:1")
    }
    Spacer(modifier = Modifier.padding(8.dp))*/
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 64.dp)
            .aspectRatio(1f) // Tỷ lệ 1:1 view
            .background(Color.White)
    ) {
        mWidth = size.width
        mHeight = size.height

        rotate(degrees = pendulumRotation, pivot = Offset(mWidth / 2, mHeight / 2)) {
            drawCircle(
                color = Color.Red,
                center = Offset(mWidth / 2, 4 * mHeight / 5),
                radius = radius
            )
            drawCircle(
                color = Color.Blue,
                center = Offset(mWidth / 2, 3 * mHeight / 4),
                radius = radius.div(1.6f),
            )
            drawCircle(
                color = Color.Magenta,
                center = Offset(mWidth / 2, 2 * mHeight / 3),
                radius = radius.div(3),
            )
            drawCircle(
                color = Color.Yellow,
                center = Offset(mWidth / 2, mHeight / 2),
                radius = radius.div(4),
            )
        }
    }
}