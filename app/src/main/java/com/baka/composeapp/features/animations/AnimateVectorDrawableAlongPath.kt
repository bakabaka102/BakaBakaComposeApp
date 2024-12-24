package com.baka.composeapp.features.animations

import android.graphics.Path
import android.graphics.PathMeasure
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationResult
import androidx.compose.animation.core.AnimationVector1D
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.baka.composeapp.features.buildInfinityPath

/**
 * Translating Objects along a Path
 * https://archive.is/5AKJP
 */
@Composable
fun AnimateVectorDrawableAlongPath() {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val anim: Animatable<Float, AnimationVector1D> = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        val animateTo: AnimationResult<Float, AnimationVector1D> =
            anim.animateTo(targetValue = 1f, animationSpec = tween(3000, easing = LinearEasing))
    }

    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )
    val pos = FloatArray(2)
    val tan = FloatArray(2)
    val pathMeasure = PathMeasure()
    val pathPortion = Path()
    Canvas(
        modifier = Modifier
            .fillMaxWidth(fraction = 0.8f)
            .aspectRatio(1f)
            .background(Color.White)
    ) {
        val infinityPath = buildInfinityPath()
        pathMeasure.apply {
            setPath(infinityPath.asAndroidPath(), false)
            getPosTan(length * progress, pos, tan)
            getSegment(0f, this.length * progress, pathPortion, true)
            drawCircle(
                center = Offset(pos[0], pos[1]),
                radius = 6.dp.toPx(),
                color = Color.Red
            )
        }
        drawPath(
            path = /*infinityPath*/ pathPortion.asComposePath(),
            color = Color.Black,
            style = Stroke(width = 2.dp.toPx()),
        )
    }
}