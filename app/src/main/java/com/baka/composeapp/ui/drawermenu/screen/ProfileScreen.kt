package com.baka.composeapp.ui.drawermenu.screen

import android.widget.Space
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.Morph
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath

//https://developer.android.com/develop/ui/compose/graphics/draw/shapes?hl=vi
@Composable
fun ProfileScreen(innerPadding: PaddingValues) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .verticalScroll(state = scrollState)
            .fillMaxSize()
            .background(Color(0xFFDB6363)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .drawWithCache {
                    val roundedPolygon = RoundedPolygon(
                        numVertices = 6,
                        radius = size.minDimension / 2,
                        centerX = size.width / 2,
                        centerY = size.height / 2
                    )
                    val roundedPolygonPath = roundedPolygon
                        .toPath()
                        .asComposePath()
                    onDrawBehind {
                        drawPath(roundedPolygonPath, color = Color.Blue)
                    }
                }
            /*.fillMaxSize().aspectRatio(1f)*/
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .drawWithCache {
                    val triangle = RoundedPolygon(
                        numVertices = 3,
                        radius = size.minDimension / 2f,
                        centerX = size.width / 2f,
                        centerY = size.height / 2f,
                        rounding = CornerRounding(
                            size.minDimension / 10f,
                            smoothing = 0.1f
                        )
                    )
                    val square = RoundedPolygon(
                        numVertices = 4,
                        radius = size.minDimension / 3f,
                        centerX = size.width / 2f,
                        centerY = size.height / 2f
                    )

                    val morph = Morph(start = triangle, end = square)
                    val morphPath = morph
                        .toPath(progress = 0.8f)
                        .asComposePath()

                    onDrawBehind {
                        drawPath(morphPath, color = Color(0xFF793AEB))
                    }
                }
        )
        Spacer(modifier = Modifier.padding(8.dp))
        val infiniteAnimation = rememberInfiniteTransition(label = "infinite animation")
        val morphProgress = infiniteAnimation.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                tween(1000),
                repeatMode = RepeatMode.Reverse
            ),
            label = "shape animation"
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .drawWithCache {
                    val triangle = RoundedPolygon(
                        numVertices = 3,
                        radius = size.minDimension / 3f,
                        centerX = size.width / 2f,
                        centerY = size.height / 2f,
                        rounding = CornerRounding(
                            size.minDimension / 10f,
                            smoothing = 0.1f
                        )
                    )
                    val square = RoundedPolygon(
                        numVertices = 4,
                        radius = size.minDimension / 4f,
                        centerX = size.width / 2f,
                        centerY = size.height / 2f
                    )

                    val morph = Morph(start = triangle, end = square)
                    val morphPath = morph
                        .toPath(progress = morphProgress.value)
                        .asComposePath()

                    onDrawBehind {
                        drawPath(morphPath, color = Color.Black)
                    }
                }
        )
        Spacer(modifier = Modifier.padding(80.dp))
    }
}