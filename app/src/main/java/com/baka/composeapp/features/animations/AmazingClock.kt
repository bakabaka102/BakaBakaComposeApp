package com.baka.composeapp.features.animations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import com.baka.composeapp.helper.Logger
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


//https://proandroiddev.com/amazing-clock-animation-with-jetpack-compose-part-1-8d143f38a3cd
//https://proandroiddev.com/amazing-clock-animation-with-jetpack-compose-part-3-optimisation-adding-some-colours-29ea14ca13c7
@Composable
fun AmazingClock(durationTime: Int = 10000) {
    var mWidth by remember { mutableFloatStateOf(0f) }
    var mHeight by remember { mutableFloatStateOf(0f) }
    val infiniteTransition = rememberInfiniteTransition(label = "infinitive_rotation")
    val animationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 720f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationTime, easing = LinearEasing),
            /*repeatMode = RepeatMode.Restart,*/
        ), label = ""
    )
    var currentHour: Int by remember { mutableIntStateOf(0) }
    /*val currentHour: Int by remember(animationAngle) {
        derivedStateOf { animationAngle.toInt() / (360 / 12) }
    }*/
    // Assume that duration is 1/12th of the whole duration, which equals to the length of 2 hours. It can be longer or shorter if necessary
    val disassembleDuration = durationTime / 12
    val currentHourChanel = remember {
        Channel<Int>(12, BufferOverflow.DROP_OLDEST) { }
    }
    val currentHourFlow = remember(currentHourChanel) {
        currentHourChanel.receiveAsFlow()
    }
    val hours: List<Int> = remember { List(12) { it } }
    val dotsVisible = remember(currentHour) {
        hours.map { index ->
            when {
                index > currentHour -> false
                index > currentHour - 12 -> true
                /*index == currentHour - 12 || index == currentHour -> true*/
                else -> false
            }
        }
    }
    var strokeWidth by remember { mutableFloatStateOf(0f) }
    val assembleAnim = remember { Animatable(-1f) }

    // Start calculation each time the animationAngle changes.
    val assembleValue = remember(animationAngle) {
        // We only need this animation for second rotation
        if (animationAngle >= 360) {
            // Reversed linear interpolation between 0..30 degrees, transformed into 0..1
            (animationAngle % 30) / 30
        } else -1f
    }

    val disassembleAnimations = remember { hours.map { Animatable(1f) } }

    LaunchedEffect(animationAngle) {
        val newCurrentHour = animationAngle.toInt() / 30
        if (newCurrentHour != currentHour) {
            currentHour = newCurrentHour
            currentHourChanel.trySend(currentHour)
        }
    }
    LaunchedEffect(key1 = currentHourFlow) {
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
        assembleAnim.snapTo(0f)
        assembleAnim.animateTo(1f, tween(50 * (24 - currentHour), easing = LinearOutSlowInEasing))
    }

    Canvas(
        modifier = Modifier
            .size(200.dp)
            .background(Color.Black)
            .padding(16.dp)
    ) {
        mWidth = this.size.width
        mHeight = this.size.height
        strokeWidth = mWidth / 24
        val halfStroke = strokeWidth / 2
        val stepHeight = mHeight / 24
        val centerPointHand = Offset(x = mWidth / 2, y = mHeight / 2)
        val endPointHand = Offset(
            x = this.size.width / 2,
            y = mHeight / 2 - calculateClockHandLength(/*mHeight / 2*/stepHeight, currentHour)
        )

        //Animation of Hand
        rotate(degrees = animationAngle, pivot = centerPointHand) {
            drawLine(
                /*color = Color.White,*/
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Red, Color.Yellow, Color.Green, Color.Blue, Color.Magenta,
                    )
                ),
                start = centerPointHand,
                end = endPointHand,
                cap = StrokeCap.Round,
                strokeWidth = strokeWidth,
            )
            if (assembleValue != -1f) {
                val positionY = halfStroke +
                        calculateAssembleDistance(stepHeight, currentHour) * assembleValue

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
        //Hour Points animation
        hours.forEach { hour ->
            if (dotsVisible[hour].not()) return@forEach
            val degree = hour * 30f.also {
                Logger.i("Degree --- $it")
            }
            rotate(degree) {
                val positionY = halfStroke +
                        stepHeight * hour * (1 - disassembleAnimations[hour].value)
                val start = Offset(mWidth / 2, positionY /*- halfStroke*/).also {
                    Logger.i("Start point --- $it")
                }
                val end = Offset(mWidth / 2, positionY /*+ halfStroke*/).also {
                    Logger.i("End point --- $it")
                }
                drawLine(
                    /*color = Color.White,*/
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Red, Color.Yellow, Color.Green, Color.Blue, Color.Magenta
                        )
                    ),
                    start = start,
                    end = end,
                    cap = StrokeCap.Round,
                    strokeWidth = strokeWidth,
                )
            }
        }

    }
}

private fun calculateClockHandLength(stepHeight: Float, currentHour: Int): Float {
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