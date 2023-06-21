package com.example.composenicecheckbox

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
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

    val circleRadius by animateDpAsState(
        targetValue = if (isChecked) 0.dp else maxRadius  - boxStrokeWidth / 2,
        animationSpec = tween(durationMillis = 200, easing = LinearEasing), label = "checkbox animation"
    )

    val tickPaint = Paint().apply {
        this.color = Color.White
        this.strokeCap = StrokeCap.Round
        this.strokeWidth = 10f
    }

    val checkBoxColor = color.niceCheckColors(enabled = enabled, isChecked = isChecked)

    val selectableModifier = if (onClick != null){
        Modifier.selectable(
            selected = isChecked,
            enabled = enabled,
            role = Role.Checkbox,
            onClick = onClick,
            interactionSource = interactionSource,
            indication = rememberRipple(
                bounded = false
            )
        )
    } else {
        Modifier
    }

    Canvas(
        modifier
            .then(selectableModifier)
            .wrapContentSize(Alignment.Center)
            .padding(boxPadding)
            .requiredSize(requiredSize)
    ) {


        with(drawContext.canvas.nativeCanvas) {
            val count = saveLayer(null, null)

            drawCircle(
                color = boxBorderColor,
                radius = maxRadius.toPx(),
                style = Stroke(
                    width = boxStrokeWidth.toPx(),
                ),
            )

//          Destination
            drawCircle(
                color = checkBoxColor.value,
                radius = maxRadius.toPx() - boxStrokeWidth.toPx() / 2,
                style = Fill
            )

            val path = Path()
            path.moveTo(center.x * 2 / 3, center.y)
            path.lineTo(center.x - center.x / 6, center.y + center.y / 4)
            path.lineTo(center.x + center.x * 3 / 8, center.y * 6 / 8)
            drawPath(
                path,
                tickPaint.color,
                style = Stroke(5f)
            )
            path.reset()

//            Source
            drawCircle(
                color = Color.Transparent,
                radius = circleRadius.toPx(),
                blendMode = BlendMode.Clear,
                style = Fill
            )

            restoreToCount(count)
        }
    }

}

object NiceCheckBoxDefaults{
    @Composable
    fun colors(
        selectedColor: Color = Color(36, 199, 31),
        disabledSelectedColor: Color = Color(220, 219, 220),
        disabledUnselectedColor: Color = Color.White,
    ): NiceCheckBoxColors = NiceCheckBoxColors(
        selectedColor,
        disabledSelectedColor,
        disabledUnselectedColor
    )
}

@Immutable
class NiceCheckBoxColors internal constructor(
    private val selectedColor: Color,
    private val disabledSelectedColor: Color,
    private val disabledUnselectedColor: Color
){

    @Composable
    internal fun niceCheckColors(enabled: Boolean, isChecked: Boolean): State<Color> {
        val target = when {
            enabled && isChecked -> selectedColor
            enabled && !isChecked -> selectedColor
            !enabled && isChecked -> disabledSelectedColor
            else -> disabledUnselectedColor
        }

        return rememberUpdatedState(target)

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is NiceCheckBoxColors) return false

        if (selectedColor != other.selectedColor) return false
        if (disabledSelectedColor != other.disabledSelectedColor) return false
        if (disabledUnselectedColor != other.disabledUnselectedColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = selectedColor.hashCode()
        result = 31 * result + disabledSelectedColor.hashCode()
        result = 31 * result + disabledUnselectedColor.hashCode()
        return result
    }

}

private val boxBorderColor = Color(53, 61, 53)
private val boxStrokeWidth = 2.dp
private val maxRadius = 10.dp
private val boxPadding = 2.dp
private val requiredSize = 30.dp
