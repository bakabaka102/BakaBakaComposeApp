package com.baka.composeapp.ui.bottomnavigation

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.baka.composeapp.features.charts.icons.FacebookIcon
import com.baka.composeapp.features.charts.icons.GooglePhotosIcon
import com.baka.composeapp.features.charts.icons.InstagramIcon
import com.baka.composeapp.features.charts.icons.MessengerIcon
import com.baka.composeapp.features.charts.icons.WeatherAppIcon
import com.baka.composeapp.features.charts.lines.DrawBezierCurves
import com.baka.composeapp.features.charts.lines.SinCosPath
import com.baka.composeapp.features.charts.shapes.DrawCircle
import com.baka.composeapp.features.charts.shapes.DrawCircleBeveledEdge
import com.baka.composeapp.features.charts.shapes.DrawLine
import com.baka.composeapp.features.charts.shapes.DrawMixedShaped
import com.baka.composeapp.features.charts.shapes.DrawOval1
import com.baka.composeapp.features.charts.shapes.DrawOval2
import com.baka.composeapp.features.charts.shapes.DrawPackMan
import com.baka.composeapp.features.charts.shapes.DrawRectangle

@Composable
fun Screen1() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.padding(8.dp))
        SomeOfShapes()
        //https://dev.to/tkuenneth/drawing-and-painting-in-jetpack-compose-1-2okl
        Spacer(modifier = Modifier.padding(8.dp))
        SomeOfIcon()
        Spacer(modifier = Modifier.padding(8.dp))
        SinCosPath()
        Spacer(modifier = Modifier.padding(8.dp))
        DrawBezierCurves()
        Spacer(modifier = Modifier.padding(8.dp))
        DrawWithContent()
        Spacer(modifier = Modifier.padding(8.dp))
        DrawBackgroundOfText()
        Spacer(modifier = Modifier.padding(80.dp))
    }
}

@Composable
fun SomeOfShapes() {
    val scrollState = rememberScrollState()
    Row(modifier = Modifier.horizontalScroll(state = scrollState)) {
        Spacer(modifier = Modifier.padding(8.dp))
        DrawRectangle()
        Spacer(modifier = Modifier.padding(8.dp))
        DrawCircle()
        Spacer(modifier = Modifier.padding(8.dp))
        DrawPackMan()
        Spacer(modifier = Modifier.padding(8.dp))
        DrawCircleBeveledEdge()
        Spacer(modifier = Modifier.padding(8.dp))
        DrawLine()
        Spacer(modifier = Modifier.padding(8.dp))
        DrawOval1()
        Spacer(modifier = Modifier.padding(8.dp))
        DrawOval2()
        Spacer(modifier = Modifier.padding(8.dp))
        DrawMixedShaped()
        Spacer(modifier = Modifier.padding(8.dp))
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
@Preview
fun DrawBackgroundOfText() {
    //https://developer.android.com/develop/ui/compose/graphics/draw/modifiers
    // [START android_compose_graphics_modifiers_drawWithCache]
    Text(
        color = Color.White,
        text = "Hello Compose!",
        modifier = Modifier
            /*.padding(horizontal = 12.dp, vertical = 6.dp)*/
            .drawWithCache {
                val brush = Brush.linearGradient(
                    listOf(
                        Color(0xFF9E82F0),
                        Color(0xFF42A5F5),
                    )
                )
                onDrawBehind {
                    drawRoundRect(
                        brush = brush,
                        cornerRadius = CornerRadius(10.dp.toPx()),
                    )
                }
            }
            .padding(12.dp)
    )
    // [END android_compose_graphics_modifiers_drawWithCache]
}

@Composable
@Preview
fun DrawWithContent(width: Float = 300f, height: Float = 300f) {
    // [START android_compose_graphics_modifiers_drawWithContent]
    //https://developer.android.com/develop/ui/compose/graphics/draw/modifiers
    var pointerOffset by remember {
        mutableStateOf(Offset(0f, 0f))
    }
    Column(
        modifier = Modifier
            .size(width.dp, height.dp)
            .pointerInput("dragging") {
                detectDragGestures { _, dragAmount ->
                    pointerOffset += dragAmount
                }
            }
            .onSizeChanged {
                pointerOffset = Offset(it.width / 2f, it.height / 2f)
            }
            .drawWithContent {
                drawContent()
                // draws a fully black area with a small keyhole at pointerOffset that’ll show part of the UI.
                drawRect(
                    Brush.radialGradient(
                        listOf(Color.Transparent, Color.Black),
                        center = pointerOffset,
                        radius = 100.dp.toPx(),
                    )
                )
            }
    ) {
        // Your composable here
        Box(modifier = Modifier.size(width.dp)) {
            Text(
                text = "This is a demo text, it is hidden by a dark color.",
                style = TextStyle(fontSize = 24.sp, color = Color.Red, textAlign = TextAlign.Center)
            )
        }
    }
    // [END android_compose_graphics_modifiers_drawWithContent]
}
