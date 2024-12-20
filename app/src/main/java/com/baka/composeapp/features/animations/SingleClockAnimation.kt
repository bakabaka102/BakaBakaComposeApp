package com.baka.composeapp.features.animations

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baka.composeapp.ui.theme.BakaBakaComposeAppTheme


//https://github.com/Kpeved/ClockProgressAnimation/blob/main/app/src/main/java/com/kpeved/circleAnimation/SingleClockAnimation.kt

@Preview(showBackground = true)
@Composable
fun SingleClockWithSliderPreview() {
    BakaBakaComposeAppTheme {
        var progress by remember { mutableFloatStateOf(0f) }
        var animationAngle by remember { mutableFloatStateOf(0f) }

        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(Color.Black)
            ) {
                SingleClockAnimationProgress(animationAngle)
            }
            Slider(
                modifier = Modifier.padding(16.dp),
                value = progress,
                onValueChange = {
                    progress = it
                    animationAngle = it * 720f
                }
            )
        }
    }
}

@Composable
fun SingleClockAnimation(duration: Int = 6000) {
    // Step 1. Set infinite animation
    val infiniteTransition = rememberInfiniteTransition(label = "")

    // Creates a child animation of float type as a part of the [InfiniteTransition].
    val clockAnimation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 720f,
        animationSpec = infiniteRepeatable(
            animation = tween(duration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )
    SingleClockAnimationProgress(clockAnimation)
}

@Composable
fun SingleClockAnimationProgress(animationAngle: Float) {
    val hours: List<Int> = remember { List(12) { it } }
    val currentHour: Int by remember(animationAngle) {
        derivedStateOf { animationAngle.toInt() / 30 }
    }

    val dotsVisibility = remember(currentHour) {
        hours.map { index ->
            when {
                index > currentHour -> false
                index > currentHour - 12 -> true
                else -> false
            }
        }
    }

    val dotsPositions = remember(animationAngle) {
        List(12) { index ->
            angleToFraction(
                angle = animationAngle,
                startAngle = index * 30f,
                degreeLimit = 60f,
                easing = LinearOutSlowInEasing
            )
        }
    }

    val assembleValue = remember(animationAngle) {
        if (animationAngle >= 360) {
            (animationAngle % 30) / 30
        } else -1f
    }
    var strokeWidth by remember { mutableFloatStateOf(0f) }

    //Step 2 -  create a spacer for animation
    Spacer(modifier = Modifier
        .fillMaxSize().padding(16.dp)
        .onGloballyPositioned {
            strokeWidth = (it.size.width / 24).toFloat()
        }
        .drawBehind {
            val halfStroke: Float = strokeWidth / 2
            val stepHeight = size.height / 24

            // Step 3 - set center
            val center = Offset(size.width / 2, size.height / 2)
            val endOffset = Offset(
                size.width / 2,
                size.height / 2 - calculateClockHandLength(stepHeight, currentHour)
            )
            rotate(animationAngle, pivot = center) {
                drawLine(
                    color = Color.White,
                    start = center,
                    end = endOffset,
                    strokeWidth = strokeWidth,
                )
                if (assembleValue != -1f) {
                    val positionY = halfStroke +
                            calculateAssembleDistance(stepHeight, currentHour) *
                            assembleValue

                    val start = Offset(size.width / 2, positionY - halfStroke)
                    val end = Offset(size.width / 2, positionY + halfStroke)
                    drawLine(
                        color = Color.White,
                        start = start,
                        end = end,
                        strokeWidth = strokeWidth
                    )
                }
            }

            hours.forEach {
                if (!dotsVisibility[it]) return@forEach
                val degree = it * 30f
                rotate(degree) {
                    val positionY = halfStroke +
                            calculateDisassembleDistance(stepHeight, it) *
                            (1 - dotsPositions[it])

                    val start = Offset(size.width / 2, positionY - halfStroke)
                    val end = Offset(size.width / 2, positionY + halfStroke)
                    drawLine(
                        color = Color.White,
                        start = start,
                        end = end,
                        strokeWidth = strokeWidth,
                    )
                }
            }
        }
    )
}

// Returns fraction from 0 to 1
@Suppress("SameParameterValue")
private fun angleToFraction(
    angle: Float,
    startAngle: Float,
    degreeLimit: Float,
    easing: Easing
): Float {
    val currentDeg: Float = (angle - startAngle).coerceIn(0f, degreeLimit)
    // Progress from 0 to 1
    val progressFraction: Float = currentDeg / degreeLimit
    return easing.transform(progressFraction)
}

private fun calculateClockHandLength(stepHeight: Float, currentHour: Int): Float {
    // Height decreases first 360 deg, then increases again

    return stepHeight * if (currentHour < 12) {
        // We need to subtract 1 because we have one additional hour to assemble 1st item
        12 - 1 - currentHour
    } else {
        currentHour - 12
    }
}

private fun calculateAssembleDistance(stepHeight: Float, currentHour: Int): Float {
    val fixedHour = 24 - currentHour - 1
    return stepHeight * fixedHour
}

private fun calculateDisassembleDistance(stepHeight: Float, currentHour: Int): Float {
    return stepHeight * (currentHour)
}
