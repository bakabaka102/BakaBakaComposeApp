package com.baka.composeapp.features

import android.graphics.PointF
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.math.cos
import kotlin.math.sin

//file: Utils.kt
/**
 * Ref: Playing with Paths at https://archive.is/cEAZ5
 * Create a polygon path
 * @param center the center of the polygon
 * @param sides the number of sides of the polygon
 * @param radius the radius of the polygon
 * @return the path of the polygon
 */
fun buildPolygonPath(center: PointF, sides: Int = 6, radius: Float): Path {
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

/**
 * This function to build a infinity path
 * Ref: Translating Objects along a Path at https://archive.is/5AKJP
 */
fun DrawScope.buildInfinityPath(): Path {
    // Start & End Points
    val start = Offset(0f, size.height / 2)
    val end = Offset(size.width, size.height / 2)
    // Control Points
    val c1 = Offset(size.width / 4, 0f)
    val c2 = Offset(3 * size.width / 4, size.height)
    val c3 = Offset(3 * size.width / 4, 0f)
    val c4 = Offset(size.width / 4, size.height)
    return Path().apply {
        // Move to Start Position
        moveTo(start.x, start.y)
        // first curve : start -> c1 -> c2 -> end
        cubicTo(c1.x, c1.y, c2.x, c2.y, end.x, end.y)
        // Second curve : end -> c3 -> c4 -> start
        cubicTo(c3.x, c3.y, c4.x, c4.y, start.x, start.y)
        // Close the Path
        close()
    }
}
