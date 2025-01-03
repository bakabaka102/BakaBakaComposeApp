package com.baka.composeapp.features.animations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@Composable
fun ParallelClockAnimation(duration: Int = 6000) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    // Create an infinite animation
    val animationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 720f,
        animationSpec = infiniteRepeatable(
            animation = tween(duration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    var currentHour by remember { mutableIntStateOf(0) }
    val disassembleDuration = duration / 6

    val hours = remember { List(12) { it } }
    val dotsVisibility = remember(currentHour) {
        hours.map { index ->
            when {
                index > currentHour -> false
                index > currentHour - 12 -> true
                else -> false
            }
        }
    }

    val assembleValue = remember(animationAngle) {
        if (animationAngle >= 360) {
            (animationAngle % 30) / 30
        } else -1f
    }

    val disassembleAnimations =
        remember { hours.map { Animatable(1f) } }

    val currentHourChannel = remember { Channel<Int>(12, BufferOverflow.DROP_OLDEST) }
    val currentHourFlow = remember(currentHourChannel) { currentHourChannel.receiveAsFlow() }

    LaunchedEffect(animationAngle) {
        val newCurrentHour = animationAngle.toInt() / 30

        if (newCurrentHour != currentHour) {
            currentHour = newCurrentHour
            currentHourChannel.trySend(currentHour)
        }
    }

    LaunchedEffect(currentHourFlow) {
        currentHourFlow.collectLatest {
            launch {
                if (currentHour < 12) {
                    disassembleAnimations[currentHour].snapTo(0f)
                    disassembleAnimations[currentHour].animateTo(
                        1f,
                        tween(disassembleDuration, easing = LinearOutSlowInEasing)
                    )
                }
            }
        }
    }


    var strokeWidth by remember { mutableFloatStateOf(0f) }

    Spacer(modifier = Modifier
        .fillMaxSize().padding(16.dp)
        // Set strokeWidth based on the size of the viewport
        .onGloballyPositioned {
            strokeWidth = (it.size.width / 24).toFloat()
        }
        .drawBehind {
            val halfStroke: Float = strokeWidth / 2
            val stepHeight = size.height / 24

            val center = Offset(size.width / 2, size.height / 2)
            val endOffset = Offset(
                size.width / 2,
                size.height / 2 - calculateClockHandLength(stepHeight, currentHour)
            )
            // Rotate for 0 to 720 degrees the line around the pivot point, which is the
            // center of the screen
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
                            stepHeight * it *
                            (1 - disassembleAnimations[it].value)

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

private fun calculateClockHandLength(
    stepHeight: Float,
    currentHour: Int,
): Float {
    // Height decreases first 360 deg, then increases again
    return stepHeight * if (currentHour < 12) {
        12 - 1 - currentHour
    } else {
        currentHour - 12
    }
}

private fun calculateAssembleDistance(stepHeight: Float, currentHour: Int): Float {
    val fixedHour = 24 - currentHour - 1
    return stepHeight * fixedHour
}
