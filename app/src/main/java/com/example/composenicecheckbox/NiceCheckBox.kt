package com.example.composenicecheckbox

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color as CColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun NiceCheckBox(
    isChecked: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: NiceCheckBoxColors = NiceCheckBoxDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {

    val circleRadius = animateDpAsState(
        targetValue = 0.dp,
        animationSpec = tween(durationMillis = 1000), label = "checkbox animation"
    )

    val tickPaint = Paint().apply {
        this.color = Color.parseColor("#ffffff")
        this.strokeCap = Paint.Cap.ROUND
        this.strokeWidth = 5f
        this.style = Paint.Style.STROKE
    }

    val checkBoxColor = color.niceCheckColors(enabled = enabled, isChecked = isChecked)
    val selectableModifier = if (onClick != null){
        Modifier.selectable(
            selected = isChecked,
            enabled = enabled,
            role = Role.Checkbox,
            onClick = onClick,
            interactionSource = interactionSource,
            indication = rememberRipple()
        )
    } else {
        Modifier
    }


    Canvas(
        Modifier
            .then(selectableModifier)
            .wrapContentSize(Alignment.Center)
            .padding(BOX_PADDING)
            .requiredSize(REQUIRED_SIZE)
    ) {


        with(drawContext.canvas.nativeCanvas) {
            val count = saveLayer(null, null)

            drawCircle(
                color = CColor.DarkGray,
                radius = MAX_RADIUS.toPx(),
                style = Stroke(
                    width = BOX_STROKE_WIDTH.toPx(),
                ),
            )

//          Destination
            drawCircle(
                color = CColor.Red,
                radius = MAX_RADIUS.toPx() - BOX_STROKE_WIDTH.toPx() / 2,
            )

//            Source
            drawCircle(
                color = BOX_MASK_COLOR,
                radius = circleRadius.value.toPx(),
                blendMode = BlendMode.Clear,
            )

            val path = Path()
            path.moveTo(center.x / 2, center.y)
            path.lineTo(center.x - center.x / 4, center.y + center.y / 4)
            drawPath(
                path,
                tickPaint
            )
            path.reset()

            path.moveTo(center.x - center.x / 4, center.y + center.y / 4)
            path.lineTo(center.x + center.x * 3 / 8, center.y * 6 / 8)
            drawPath(
                path,
                tickPaint
            )
            path.reset()

            restoreToCount(count)
        }
    }

}

object NiceCheckBoxDefaults{
    @Composable
    fun colors(
        selectedColor: CColor = MaterialTheme.colorScheme.onPrimary,
        unselectedColor: CColor = MaterialTheme.colorScheme.onPrimary,
        disabledSelectedColor: CColor = MaterialTheme.colorScheme.onPrimary,
        disabledUnselectedColor: CColor = MaterialTheme.colorScheme.onPrimary,
    ): NiceCheckBoxColors = NiceCheckBoxColors(
        selectedColor,
        unselectedColor,
        disabledSelectedColor,
        disabledUnselectedColor
    )
}

@Immutable
class NiceCheckBoxColors internal constructor(
    private val selectedColor: CColor,
    private val unselectedColor: CColor,
    private val disabledSelectedColor: CColor,
    private val disabledUnselectedColor: CColor
){

    @Composable
    internal fun niceCheckColors(enabled: Boolean, isChecked: Boolean): State<CColor> {
        val target = when {
            enabled && isChecked -> selectedColor
            enabled && !isChecked -> unselectedColor
            !enabled && isChecked -> disabledSelectedColor
            else -> disabledUnselectedColor
        }

        return if (enabled){
            animateColorAsState(target, tween(BOX_ANIMATION_DURATION), label = "NiceCheckBox")
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

private const val BOX_ANIMATION_DURATION = 1000
private val BOX_MASK_COLOR = CColor.Gray
private val BOX_STROKE_WIDTH = 3.dp
//private const val BACKGROUND_CIRCLE_RADIUS = 40f
private val MAX_RADIUS = 10.dp
private val BOX_PADDING = 2.dp
private val REQUIRED_SIZE = 30.dp
