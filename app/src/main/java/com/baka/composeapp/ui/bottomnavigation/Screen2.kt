package com.baka.composeapp.ui.bottomnavigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.baka.composeapp.features.charts.barschart.BarChart
import com.baka.composeapp.features.charts.lines.LineChart
import com.baka.composeapp.ui.models.GraphAppearance

@Composable
fun Screen2() {
//https://proandroiddev.com/exploring-canvas-in-jetpack-compose-crafting-graphics-animations-and-game-experiences-b0aa31160bff

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BarChart()
        //https://proandroiddev.com/line-chart-ui-with-jetpack-compose-a-simple-guide-f9b8b80efc83
        Spacer(modifier = Modifier.padding(8.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(24.dp)
        ) {
            val yStep = 50
            /* to test with random points */
            /*val points = (0..9).map {
                var num = Random.nextInt(350)
                if (num <= 50)
                    num += 100
                num.toFloat()
            }*/
            /* to test with fixed points */

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray)
            ) {
                //https://proandroiddev.com/creating-graph-in-jetpack-compose-312957b11b2
                LineChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp),
                    xValues = (0..9).map { it + 1 },
                    yValues = (0..6).map { (it + 1) * yStep },
                    paddingSpace = 16.dp,
                    verticalStep = yStep,
                    graphAppearance = GraphAppearance(
                        graphColor = Color.White,
                        graphAxisColor = MaterialTheme.colorScheme.primary,
                        graphThickness = 1f,
                        isColorAreaUnderChart = true,
                        colorAreaUnderChart = Color.Green,
                        isCircleVisible = true,
                        circleColor = MaterialTheme.colorScheme.secondary,
                        backgroundColor = MaterialTheme.colorScheme.background
                    )
                )
            }
        }
    }
}

