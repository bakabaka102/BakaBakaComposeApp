package com.baka.composeapp.ui.bottomnavigation

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import com.baka.composeapp.ui.bottomnavigation.models.PointsData
import kotlinx.coroutines.delay

@Composable
fun Screen3() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.padding(8.dp))
        Pendulum(pointsData = PointsData()) {
            Surface(
                modifier = Modifier.size(24.dp),
                shape = CircleShape,
                color = Color.Red,
                content = {})
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Pendulum()
        Spacer(modifier = Modifier.padding(8.dp))
        BouncingBallGame()
        Spacer(modifier = Modifier.padding(80.dp))
    }
}

@Composable
fun BouncingBallGame() {
    var widthCanvas by remember {
        mutableFloatStateOf(0f)
    }
    var heightCanvas by remember {
        mutableFloatStateOf(0f)
    }
    val radiusBall = 40f
    /* var x by remember {
         mutableFloatStateOf(widthCanvas)
     }
     var y by remember {
         mutableFloatStateOf(heightCanvas)
     }*/
    val position = remember { mutableStateOf(Offset(300f, 0f)) }
    val velocity = remember { mutableStateOf(Offset(3f, 3f)) }

    LaunchedEffect(Unit) {
        while (true) {
            position.value += velocity.value
            // change > condition to size of screen,
            // here just using a static value for demo purposes
            if (position.value.x - radiusBall < 0f || position.value.x + radiusBall > widthCanvas) {
                velocity.value = Offset(-velocity.value.x, velocity.value.y)
            }
            if (position.value.y < 0f || position.value.y + radiusBall > heightCanvas) {
                velocity.value = Offset(velocity.value.x, -velocity.value.y)
            }
            delay(16L)
        }
    }

    Column(
        modifier = Modifier.background(
            /*brush = Brush.horizontalGradient(listOf(Color.Red, Color.Blue))*/
            color = Color(0xFFEEE8AF)
        )
    ) {
        Canvas(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
        ) {
            Color(0xFF009688)
            heightCanvas = size.height
            widthCanvas = size.width
            drawCircle(
                brush = Brush.horizontalGradient(listOf(Color.Magenta, Color(0xFF009688))),
                center = position.value,
                radius = radiusBall,
            )
        }
        HorizontalDivider(thickness = 5.dp, color = Color.Red)
        Button(
            modifier = Modifier
                /*.background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            Color.Blue,
                            Color.Red
                        )
                    )
                )*/
                .align(alignment = Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF009688)),
            onClick = {
                val random = (-1..1).random()
                if (random != 0) {
                    velocity.value += Offset(random * 6f, random * 6f)
                }
            },
        ) {
            Text(text = "Velocity: ${velocity.value.x}, ${velocity.value.y}")
        }
    }
}

@Composable
fun Pendulum(
    modifier: Modifier = Modifier,
    swingDuration: Int = 1500,
    pointsData: PointsData,
    content: @Composable () -> Unit,
) {

    val infiniteTransition = rememberInfiniteTransition(label = "Infinite transition")

    BoxWithConstraints(
        modifier = modifier
            .size(300.dp)
            .background(Color(0xFFB5D1E7))
    ) {
        val start = maxWidth * pointsData.startX
        val end = maxWidth * pointsData.endX
        val top = maxHeight * pointsData.topY
        val bottom = maxHeight * pointsData.bottomY

        val x by infiniteTransition.animateFloat(
            initialValue = start.value,
            targetValue = end.value,
            animationSpec = infiniteRepeatable(
                animation = tween(swingDuration, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ), label = "X Pendulum animation"
        )

        val y by infiniteTransition.animateFloat(
            initialValue = top.value,
            targetValue = bottom.value,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    swingDuration / 2,
                    easing = FastOutLinearInEasing /*LinearOutSlowInEasing*/
                ),
                repeatMode = RepeatMode.Reverse
            ), label = "Y Pendulum animation"
        )

        Box(
            modifier = Modifier.offset(x = x.dp, y = y.dp)
        ) {
            content()
        }
    }
}

//https://github.com/MohammedShaat/pendulum/blob/master/app/src/main/java/com/example/pendulum/Pendulum.kt
@Composable
fun Pendulum(
    modifier: Modifier = Modifier,
    bgColor: Color = Color.White,
    brushBoxColor: Brush = Brush.horizontalGradient(listOf(Color.Yellow, Color.Magenta)),
    circleColor: Color = Color(0xFF7E06B1),
    eyeColor: Color = Color.White,
    pupilColor: Color = Color.Black,
    threadColor: Color = Color.Black,
    ballColor: Color = Color(0xFFB618D1),
    radiusBall: Float = 50f,
) {
    var width by remember { mutableFloatStateOf(0f) }
    var height by remember { mutableFloatStateOf(0f) }
    val centerX by remember(width) { mutableFloatStateOf(width / 2) }
    val clockRadius by remember { mutableFloatStateOf(180f) }
    val clockY by remember(height) { mutableFloatStateOf(height / 3) }
    val leftEyeX by remember(centerX) { mutableFloatStateOf(centerX - (width / 12)) }
    val rightEyeX by remember(centerX) { mutableFloatStateOf(centerX + (width / 12)) }
    val eyeSize by remember { mutableFloatStateOf(40f) }
    val pupilSize by remember { mutableFloatStateOf(16f) }
    val threadLength by remember { mutableFloatStateOf(500f) }
    val boxSize by remember { mutableStateOf(Size(400f, 400f)) }

    val animationDuration by remember { mutableIntStateOf(1000) }
    val infiniteTransition = rememberInfiniteTransition("pendulum")

    val pupilsRotation by infiniteTransition.animateFloat(
        initialValue = 35f,
        targetValue = -35f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), "pupils_translation"
    )
    val pendulumRotation by infiniteTransition.animateFloat(
        initialValue = 20f,
        targetValue = -20f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), "pendulum_rotation"
    )

    Box(
        modifier = modifier
            .background(color = bgColor)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
        ) {
            width = size.width
            height = size.height

            // Pendulum - pendulumRotation
            rotate(
                degrees = pendulumRotation,
                pivot = Offset(centerX, clockY)
            ) {
                // Thread
                drawLine(
                    color = threadColor,
                    start = Offset(centerX, clockY),
                    end = Offset(centerX, clockY + threadLength - 2.div(radiusBall)),
                    strokeWidth = 8f
                )
                // Ball
                drawCircle(
                    color = ballColor,
                    center = Offset(centerX, clockY + threadLength - 2.div(radiusBall)),
                    radius = radiusBall,
                )
            }

            // Clock
            // Box
            drawRoundRect(
                brush = brushBoxColor,
                topLeft = Offset(centerX - boxSize.width / 2, clockY - boxSize.height / 2),
                size = boxSize,
                cornerRadius = CornerRadius(40f)
            )
            // Circle face
            drawCircle(
                color = circleColor,
                center = Offset(width / 2, clockY),
                radius = clockRadius
            )
            // Left eye
            drawCircle(
                color = eyeColor,
                center = Offset(leftEyeX, clockY),
                radius = eyeSize
            )
            // Right eye
            drawCircle(
                color = eyeColor,
                center = Offset(rightEyeX, clockY),
                radius = eyeSize
            )
            //Draw mouth
            drawArc(
                color = Color(0xFF350129),
                startAngle = 45f,
                sweepAngle = 90f,
                topLeft = Offset(0.4f * width, clockY),
                useCenter = false,
                size = Size(2 * clockRadius / 3, 2 * clockRadius / 3),
                style = Stroke(width = 8f, cap = StrokeCap.Round)
            )

            // Left pupil
            rotate(degrees = pupilsRotation, pivot = Offset(leftEyeX, clockY - 10)) {
                drawCircle(
                    color = pupilColor,
                    center = Offset(leftEyeX, clockY + 18),
                    radius = pupilSize
                )
            }
            // Right pupil
            rotate(degrees = pupilsRotation, pivot = Offset(rightEyeX, clockY - 10)) {
                drawCircle(
                    color = pupilColor,
                    center = Offset(rightEyeX, clockY + 18),
                    radius = pupilSize
                )
            }
        }
    }
}