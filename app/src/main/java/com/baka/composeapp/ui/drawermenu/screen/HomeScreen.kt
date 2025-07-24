package com.baka.composeapp.ui.drawermenu.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.baka.composeapp.features.animations.AnimateVectorDrawableAlongPath
import com.baka.composeapp.features.animations.AnimationDrawPathPolygons
import com.baka.composeapp.features.charts.barschart.DrawSpiltFourCircle
import com.baka.composeapp.features.charts.columns.ColumnsChart
import com.baka.composeapp.features.charts.lines.ColumnBezierCurve
import com.baka.composeapp.features.logicaction.SomeActionDemo
import com.baka.composeapp.ui.drawermenu.HomeViewModel

@Composable
fun HomeScreen(innerPadding: PaddingValues, onClick: () -> Unit = {}) {
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
        //ColumnsChart()
        //Spacer(modifier = Modifier.padding(8.dp))
        //ColumnBezierCurve()
        //Spacer(modifier = Modifier.padding(8.dp))
        //DrawSpiltFourCircle()
        //Spacer(modifier = Modifier.padding(8.dp))
        //SomeActionDemo(viewModel)
        //Spacer(modifier = Modifier.padding(8.dp))
        AnimationDrawPathPolygons()
        Spacer(modifier = Modifier.padding(8.dp))
        AnimateVectorDrawableAlongPath()
        Spacer(modifier = Modifier.padding(8.dp))
        CenteredBoxScreen(onClick)
        Spacer(modifier = Modifier.padding(80.dp))
    }
}

@Composable
fun CenteredBoxScreen(onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth(1f)
            .background(Color.White)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onClick
        ) {
            Text("Show")
        }
    }
}
