package com.baka.composeapp.ui.drawermenu.screen

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.baka.composeapp.features.animations.AmazingClock
import com.baka.composeapp.features.animations.ParallelClockAnimation
import com.baka.composeapp.features.animations.SingleClockAnimation
import com.baka.composeapp.features.charts.icons.FacebookIcon
import com.baka.composeapp.features.charts.icons.GooglePhotosIcon
import com.baka.composeapp.features.charts.icons.InstagramIcon
import com.baka.composeapp.features.charts.icons.MessengerIcon
import com.baka.composeapp.features.charts.icons.WeatherAppIcon
import com.baka.composeapp.helper.Logger
import com.baka.composeapp.ui.dialog.ConfirmDialog
import com.baka.composeapp.ui.dialog.DialogInput
import com.baka.composeapp.ui.dialog.DialogProgress
import com.baka.composeapp.ui.drawermenu.HomeViewModel

@Composable
fun HomeScreen(innerPadding: PaddingValues) {
    val viewModel = viewModel<HomeViewModel>()
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(Color.LightGray)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.padding(8.dp))
        SomeOfClock()
        Spacer(modifier = Modifier.padding(8.dp))
        MovingCircles()
        Spacer(modifier = Modifier.padding(8.dp))
        SomeOfIcon()
        Spacer(modifier = Modifier.padding(8.dp))
        Spacer(modifier = Modifier.padding(8.dp))
        SomeActionDemo(viewModel)
        Spacer(modifier = Modifier.padding(80.dp))
    }
}

@Composable
fun SomeOfClock() {
    val scrollState = rememberScrollState()
    Row(modifier = Modifier.horizontalScroll(state = scrollState)) {
        Spacer(modifier = Modifier.padding(8.dp))
        AmazingClock()
        Spacer(modifier = Modifier.padding(8.dp))
        val size = 200.dp
        Box(
            modifier = Modifier
                .size(size)
                .background(Color.Black)
        ) {
            ParallelClockAnimation()
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Box(
            modifier = Modifier
                .size(size)
                .background(Color.Black)
        ) {
            SingleClockAnimation()
        }
    }
}

@Composable
fun SomeOfIcon() {
    val scrollState = rememberScrollState()
    Row(modifier = Modifier.horizontalScroll(state = scrollState)) {
        //https://medium.com/falabellatechnology/jetpack-compose-canvas-8aee73eab393
        Spacer(modifier = Modifier.padding(8.dp))
        InstagramIcon()
        Spacer(modifier = Modifier.padding(8.dp))
        FacebookIcon()
        Spacer(modifier = Modifier.padding(8.dp))
        MessengerIcon()
        Spacer(modifier = Modifier.padding(8.dp))
        GooglePhotosIcon()
        Spacer(modifier = Modifier.padding(8.dp))
        WeatherAppIcon()
        Spacer(modifier = Modifier.padding(8.dp))
    }
}

@Composable
private fun SomeActionDemo(viewModel: HomeViewModel) {
    val count: State<Int?> = viewModel.count.observeAsState()
    val isShowDialogInput = remember { mutableStateOf(false) }
    val isShowDialogProgress = remember { mutableStateOf(false) }
    var showCustomDialog by remember { mutableStateOf(false) }

    if (isShowDialogProgress.value) {
        DialogProgress(
            title = "Loading",
            description = "This is loading dialog",
            timeOut = 5000,
            setShowDialog = { isShowDialogProgress.value = it }
        )
    }

    if (isShowDialogInput.value)
        DialogInput(title = "Dialog input", timeOut = 30_000, setShowDialog = {
            isShowDialogInput.value = it
        }, setValue = {
            Logger.i("HomePage, value inputted: $it")
        })

    if (showCustomDialog) {
        ConfirmDialog(
            onDismiss = {
                showCustomDialog = !showCustomDialog
            },
            onConfirm = {
                showCustomDialog = !showCustomDialog
            }
        )
    }

    Color(0xFF93EBF7)
    Column(
        modifier = Modifier
            .background(Color(0xFF93EBF7))
            .fillMaxWidth(),
    ) {
        Button(onClick = {
            viewModel.incrementCount()
        }) {
            Text(text = "Clicked: ${count.value}")
        }
        Button(onClick = {
            isShowDialogInput.value = true
        }) {
            Text(text = "Input Dialog")
        }
        Button(onClick = {
            isShowDialogProgress.value = true
        }) {
            Text(text = "Progress Dialog")
        }
        Button(
            onClick = {
                showCustomDialog = !showCustomDialog
            },
        ) {
            Text(text = "Confirm Dialog")
        }
    }
}

////https://proandroiddev.com/exploring-canvas-in-jetpack-compose-crafting-graphics-animations-and-game-experiences-b0aa31160bff
@Composable
fun MovingCircles() {
    val animationDuration = 3000
    val infiniteTransition = rememberInfiniteTransition(label = "pendulum")
    var width by remember { mutableFloatStateOf(0f) }
    var height by remember { mutableFloatStateOf(0f) }
    val yAxis by remember(height) { mutableFloatStateOf(height) }
    val radius = 50f

    val pupilsRotation by infiniteTransition.animateFloat(
        initialValue = /*35f*/0f,
        targetValue = /*-35f*/360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDuration, easing = LinearEasing),
            /*repeatMode = RepeatMode.Reverse*/
        ), "pupils_translation"
    )

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .height(300.dp)
            .background(Color.White)
    ) {
        width = size.width
        height = size.height

        rotate(degrees = pupilsRotation, pivot = Offset(width / 2, height / 2)) {
            drawCircle(color = Color.Red, center = Offset(width / 2, yAxis / 2), radius = radius)
            drawCircle(
                color = Color.Blue,
                center = Offset(width / 2, 2 * yAxis / 3),
                radius = radius.div(1.6f),
            )
            drawCircle(
                color = Color.Magenta,
                center = Offset(width / 2, 3 * yAxis / 4),
                radius = radius.div(3),
            )
            drawCircle(
                color = Color.Yellow,
                center = Offset(width / 2, 4 * yAxis / 5),
                radius = radius.div(4),
            )
        }
    }
}