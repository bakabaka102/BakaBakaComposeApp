package com.baka.composeapp.ui.drawermenu.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import com.baka.composeapp.features.charts.shapes.AnimationShape
import com.baka.composeapp.features.charts.shapes.HexPolygon
import com.baka.composeapp.features.charts.shapes.MixShapes
import com.baka.composeapp.features.charts.shapes.PolygonAsClip

//https://developer.android.com/develop/ui/compose/graphics/draw/shapes?hl=vi
@Composable
fun ProfileScreen(innerPadding: PaddingValues) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .verticalScroll(state = scrollState)
            .fillMaxSize()
            .background(Color(0xFFDBEBE9)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HexPolygon()
        Spacer(modifier = Modifier.padding(8.dp))
        MixShapes()
        Spacer(modifier = Modifier.padding(8.dp))
        AnimationShape()
        Spacer(modifier = Modifier.padding(8.dp))
        PolygonAsClip()
        Spacer(modifier = Modifier.padding(80.dp))
    }
}