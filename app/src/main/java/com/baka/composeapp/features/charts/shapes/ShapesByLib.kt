package com.baka.composeapp.features.charts.shapes

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.Morph
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath
import com.baka.composeapp.R
import kotlin.math.max

@Composable
fun HexPolygon() {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .background(Color.White)
            .aspectRatio(1f)
            .drawWithCache {
                val roundedPolygon = RoundedPolygon(
                    numVertices = 6,
                    radius = size.minDimension / 4,
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
}

@Composable
fun MixShapes() {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .background(Color.White)
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
}

@Composable
fun AnimationShape() {
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
            .fillMaxWidth(0.6f)
            .background(Color.White)
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
}

@Composable
@Preview
fun PolygonAsClip() {
    val hexagon = remember {
        RoundedPolygon(numVertices = 6, rounding = CornerRounding(0.2f))
    }
    val clip = remember(hexagon) {
        RoundedPolygonShape(polygon = hexagon)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth(0.66f)
            .aspectRatio(1f)
            .background(MaterialTheme.colorScheme.secondary)
            .size(200.dp),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .aspectRatio(1f)
                .clip(clip)
                .background(Color(0xFF80DEEA))
                .size(200.dp)
        ) {
            Text(
                "Hello Compose",
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )
            /*Image(painter = painterResource(id = R.drawable.bakabaka_ic_launcher),
                contentDescription = "Baka icon",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .graphicsLayer {
                        this.shadowElevation = 6.dp.toPx()
                        this.shape = clip
                        this.clip = true
                        this.ambientShadowColor = Color.Black
                        this.spotShadowColor = Color.Black
                    }
                    .size(100.dp))*/
        }
    }
}

fun RoundedPolygon.getBounds() = calculateBounds().let {
    Rect(it[0], it[1], it[2], it[3])
}

class RoundedPolygonShape(
    private val polygon: RoundedPolygon,
    private val matrix: Matrix = Matrix(),
) : Shape {
    private var path = Path()
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        path.rewind()
        path = polygon.toPath().asComposePath()
        matrix.reset()
        val bounds = polygon.getBounds()
        val maxDimension = max(bounds.width, bounds.height)
        matrix.scale(size.width.div(maxDimension), size.height / maxDimension)
        matrix.translate(-bounds.left, -bounds.top)
        path.transform(matrix)
        return Outline.Generic(path)
    }
}