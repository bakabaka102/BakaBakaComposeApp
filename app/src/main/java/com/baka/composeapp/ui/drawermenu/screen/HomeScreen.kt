package com.baka.composeapp.ui.drawermenu.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.baka.composeapp.features.charts.barschart.DrawSpiltFourCircle
import com.baka.composeapp.features.charts.barschart.InteractivePieChart
import com.baka.composeapp.features.charts.barschart.PieChart
import com.baka.composeapp.features.charts.barschart.ScalablePieChart
import com.baka.composeapp.features.charts.columns.ColumnsChart
import com.baka.composeapp.features.charts.lines.ColumnBezierCurve
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
        ColumnsChart()
        Spacer(modifier = Modifier.padding(8.dp))
        ColumnBezierCurve()
        Spacer(modifier = Modifier.padding(8.dp))
        DrawSpiltFourCircle()
        Spacer(modifier = Modifier.padding(8.dp))
        PieChart()
        Spacer(modifier = Modifier.padding(8.dp))
        ScalablePieChart()
        Spacer(modifier = Modifier.padding(8.dp))
        InteractivePieChart()
        Spacer(modifier = Modifier.padding(8.dp))
        SomeActionDemo(viewModel)
        Spacer(modifier = Modifier.padding(80.dp))
    }
}