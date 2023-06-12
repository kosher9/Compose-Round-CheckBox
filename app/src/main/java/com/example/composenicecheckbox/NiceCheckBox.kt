package com.example.composenicecheckbox

import android.graphics.Paint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.NativeCanvas
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun NiceCheckBox(
    isChecked: Boolean,
    onClick: (() -> Unit),
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    color: NiceCheckBoxColors = NiceCheckBoxDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {

    Canvas(Modifier
        // Provide a slight opacity to for compositing into an
        // offscreen buffer to ensure blend modes are applied to empty pixel information
        // By default any alpha != 1.0f will use a compositing layer by default
        .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    ) {
        drawCircle(
            color = Color.Red,
            center = Offset(0f, 0f),
            radius = 40f,
            blendMode = BlendMode.Xor
        )

        drawCircle(
            color = Color.Blue,
            center = Offset(0f, 0f),
            radius = 20f,
            blendMode = BlendMode.Xor
        )
    }

}

//private fun DrawScope.drawBounds(){
//    drawCircle(
//        color = Color.Black,
//        style = Stroke(2f),
//        radius = 40f
//    )
//}

//private fun DrawScope.drawBackground(){
////    val paint = Paint().apply {
////        isAntiAlias = true
////        color = android.graphics.Color.BLUE
////    }
////    drawCircle(
////        0f,
////        0f,
////        40f,
////        paint
////    )
//
//    drawCircle(
//        color = Color.Blue,
//        style = Fill,
//        radius = 40f,
//    )
//}

@RequiresApi(Build.VERSION_CODES.Q)
private fun DrawScope.drawMask(){
    val paint = Paint().apply {
        isAntiAlias = true
        color = android.graphics.Color.GREEN
        blendMode = android.graphics.BlendMode.CLEAR
    }
    drawCircle(
        color = Color.Green,
        style = Fill,
        radius = 20f,
        blendMode = BlendMode.DstOut
    )
}

object NiceCheckBoxDefaults{
    @Composable
    fun colors(
        selectedColor: Color = MaterialTheme.colorScheme.onPrimary,
        unselectedColor: Color = MaterialTheme.colorScheme.onPrimary,
        disabledSelectedColor: Color = MaterialTheme.colorScheme.onPrimary,
        disabledUnselectedColor: Color = MaterialTheme.colorScheme.onPrimary,
    ): NiceCheckBoxColors = NiceCheckBoxColors(
        selectedColor,
        unselectedColor,
        disabledSelectedColor,
        disabledUnselectedColor
    )
}

@Immutable
class NiceCheckBoxColors internal constructor(
    private val selectedColor: Color,
    private val unselectedColor: Color,
    private val disabledSelectedColor: Color,
    private val disabledUnselectedColor: Color
){

    @Composable
    internal fun niceCheckColors(enabled: Boolean, isChecked: Boolean): State<Color> {
        val target = when {
            enabled && isChecked -> selectedColor
            enabled && !isChecked -> unselectedColor
            !enabled && isChecked -> disabledSelectedColor
            else -> disabledUnselectedColor
        }

        return if (enabled){
            animateColorAsState(target, tween(RadioAnimationDuration), label = "NiceCheckBox")
        } else{
            rememberUpdatedState(target)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is NiceCheckBoxColors) return false

        if (selectedColor != other.selectedColor) return false
        if (unselectedColor != other.unselectedColor) return false
        if (disabledSelectedColor != other.disabledSelectedColor) return false
        if (disabledUnselectedColor != other.disabledUnselectedColor) return false

        return true
    }

    // Try to understand this part
    override fun hashCode(): Int {
        var result = selectedColor.hashCode()
        result = 31 * result + unselectedColor.hashCode()
        result = 31 * result + disabledSelectedColor.hashCode()
        result = 31 * result + disabledUnselectedColor.hashCode()
        return result
    }

}

private const val RadioAnimationDuration = 100
