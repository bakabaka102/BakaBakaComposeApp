package com.baka.composeapp.ui.drawermenu.screen.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.baka.composeapp.features.animations.RotationClock
import com.baka.composeapp.features.charts.lines.SomeOfBeziersCurve
import com.baka.composeapp.ui.drawermenu.screen.SomeOfClock

@Composable
fun AnimationScreenPager() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.LightGray)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.padding(8.dp))
        RotationClock()
        Spacer(modifier = Modifier.padding(8.dp))
        SomeOfClock()
        Spacer(modifier = Modifier.padding(64.dp))
    }
}

@Composable
fun ChartsScreenPager() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.LightGray)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.padding(8.dp))
        SomeOfBeziersCurve()
        Spacer(modifier = Modifier.padding(64.dp))
    }
}

@Composable
fun AccountScreenPager() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "AccountScreenPager")
    }
}
