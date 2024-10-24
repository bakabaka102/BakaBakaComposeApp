package com.baka.composeapp.ui.drawermenu.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.baka.composeapp.features.animations.AmazingClock
import com.baka.composeapp.features.animations.ParallelClockAnimation
import com.baka.composeapp.features.animations.RotationClock
import com.baka.composeapp.features.animations.SingleClockAnimation
import com.baka.composeapp.features.charts.barschart.DrawSpiltFourCircle
import com.baka.composeapp.features.charts.columns.ColumnsChart
import com.baka.composeapp.features.charts.lines.BezierCurve
import com.baka.composeapp.features.logicaction.SomeActionDemo
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
        ColumnsChart()
        Spacer(modifier = Modifier.padding(8.dp))
        val points = listOf(
            Pair(100f, 300f),
            Pair(150f, 100f),
            Pair(250f, 500f),
            Pair(400f, 300f),
            Pair(500f, 100f),
            Pair(600f, 400f)
        )
        BezierCurve()
        Spacer(modifier = Modifier.padding(8.dp))
        RotationClock()
        Spacer(modifier = Modifier.padding(8.dp))
        DrawSpiltFourCircle()
        Spacer(modifier = Modifier.padding(8.dp))

        SomeActionDemo(viewModel)
        Spacer(modifier = Modifier.padding(80.dp))
    }
}

@Composable
fun SomeOfClock() {
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