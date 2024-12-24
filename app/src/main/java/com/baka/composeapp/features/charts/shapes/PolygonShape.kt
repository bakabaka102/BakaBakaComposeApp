package com.baka.composeapp.features.charts.shapes

import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.baka.composeapp.features.buildPolygonPath
import com.baka.composeapp.helper.Logger
import kotlin.math.roundToInt

//https://archive.is/qgj3R

//file: PolygonShape.kt
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawPathPolygon() {
    var sliderValue by remember {
        mutableFloatStateOf(0f)
    }
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 64.dp)
            .background(Color.White), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DrawPath(sliderValue.roundToInt())
        Slider(
            modifier = Modifier.padding(horizontal = 16.dp),
            value = sliderValue,
            onValueChange = { value ->
                sliderValue = value
            },
            thumb = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                )
            },
            onValueChangeFinished = {
                // this is called when the user completed selecting the value
            },
            valueRange = 0f..20f,
            /*  steps = 1,*/
        )
        Logger.i("Sides: ${sliderValue.roundToInt()}")
        Text(modifier = Modifier.padding(8.dp), text = "Sides: ${sliderValue.roundToInt()}")
        /*if (sides >= 3) {
            DrawPath(sides)
            Text(modifier = Modifier.padding(8.dp), text = sides.toString())
        } else {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Current sides: $sides, must greater than 2"
            )
        }*/
    }
}

@Composable
private fun DrawPath(sides: Int) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f) // Tỷ lệ 1:1 view
            .background(Color.White)
    ) {
        val center = PointF(size.width / 2, size.height / 2)
        val mainPath = buildPolygonPath(center = center, sides = sides, radius = size.width / 2)
        if (sides >= 3) {
            drawPath(
                color = Color(0xFF7E46E3),
                path = mainPath,/*animatedPath.value*/
                style = Stroke(width = 10f, cap = StrokeCap.Round),
            )
        }
    }
}