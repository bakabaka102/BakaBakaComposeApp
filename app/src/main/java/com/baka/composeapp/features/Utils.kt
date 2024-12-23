package com.baka.composeapp.features

import android.graphics.PointF
import androidx.compose.ui.graphics.Path
import kotlin.math.cos
import kotlin.math.sin

fun createPolygonPath(center: PointF, sides: Int = 6, radius: Float): Path {
    val centerPointX = center.x
    val centerPointy = center.y
    val path = Path()
    val padding = 64f
    val angle = 2 * Math.PI / sides
    val mRadius = radius - padding /*?: ((this.size.width / 2) - padding)*/
    //Move cursor to first point
    path.moveTo(
        centerPointX + (mRadius * cos(0f)),
        centerPointy + (mRadius * sin(0f))
    )
    for (i in 1 until sides) {
        path.lineTo(
            centerPointX + (mRadius * cos(angle * i)).toFloat(),
            centerPointy + (mRadius * sin(angle * i)).toFloat()
        )
    }
    path.close()
    return path
}
