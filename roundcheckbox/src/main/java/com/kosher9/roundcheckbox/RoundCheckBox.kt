package com.kosher9.roundcheckbox

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * RoundCheckboxes allow users to select one or more
 * items from a set. RoundCheckboxes can turn an option on or off.
 *
 *
 * @param isChecked whether the checkbox is checked or unchecked.
 * @param onClick called when this rounded checkbox is clicked. If null,
 * then this rounded checkbox will not be interactable, unless something
 * else handles its input events and updates its state.
 * @param modifier the Modifier to be applied to this radio button
 * @param borderWidth the width of the circle border.
 * @param radius the radius of the circle.
 * @param tickWidth the width of the tick.
 * @param enabled controls the enabled state of this rounded checkbox.
 * When false, this component will not respond to user input,
 * and it will appear visually disabled and disabled to accessibility
 * services.
 * @param color [RoundCheckBoxColors] that will be used to resolve
 * the colors used for this rounded checkbox in different states
 * @param interactionSource he MutableInteractionSource representing
 * the stream of Interactions for this rounded checkbox. You can create
 * and pass in your own remembered instance to observe Interactions
 * and customize the appearance / behavior of this rounded checkbox in
 * different states.
 */
@Composable
fun RoundCheckBox(
    isChecked: Boolean,
    onClick: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    borderWidth: Dp = boxStrokeWidth,
    radius: Dp = maxRadius,
    tickWidth: Float = TICK_STROKE_WIDTH,
    enabled: Boolean = true,
    color: RoundCheckBoxColors = RoundCheckBoxDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {

    val circleRadius by animateDpAsState(
        targetValue = if (isChecked) 0.dp else radius  - borderWidth / 2,
        animationSpec = tween(durationMillis = 200, easing = LinearEasing), label = "checkbox animation"
    )

    val tickPaint = Paint().apply {
        this.color = color.roundCheckBoxTickColor().value
        this.strokeCap = StrokeCap.Round
        this.strokeWidth = 10f
    }

    val checkBoxColor = color.roundCheckBoxColors(enabled = enabled, isChecked = isChecked)
    val checkBoxBorderColor = color.roundCheckBoxBorderColor().value

    val toggleableModifier = if (onClick != null){
        Modifier.toggleable(
            value = isChecked,
            enabled = enabled,
            role = Role.Checkbox,
            onValueChange = onClick,
            interactionSource = interactionSource,
            indication = rememberRipple(
                bounded = false,
                radius = radius * 2
            )
        )
    } else {
        Modifier
    }

    Canvas(
        modifier
            .then(toggleableModifier)
            .wrapContentSize(Alignment.Center)
            .padding(boxPadding)
            .requiredSize(requiredSize)
            .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    ) {

            drawCircle(
                color = checkBoxBorderColor,
                radius = radius.toPx(),
                style = Stroke(
                    width = borderWidth.toPx(),
                ),
            )

//          Destination
            drawCircle(
                color = checkBoxColor.value,
                radius = radius.toPx() - borderWidth.toPx() / 2,
                style = Fill
            )

            val path = Path()
            path.moveTo(center.x * 2 / 3, center.y)
            path.lineTo(center.x - center.x / 6, center.y + center.y / 4)
            path.lineTo(center.x + center.x * 3 / 8, center.y * 6 / 8)
            drawPath(
                path,
                tickPaint.color,
                style = Stroke(tickWidth)
            )
            path.reset()

//            Source
            drawCircle(
                color = Color.Transparent,
                radius = circleRadius.toPx(),
                blendMode = BlendMode.Clear,
                style = Fill
            )
    }

}

object RoundCheckBoxDefaults{
    @Composable
    fun colors(
        selectedColor: Color = Color(36, 199, 31),
        disabledSelectedColor: Color = Color(220, 219, 220),
        disabledUnselectedColor: Color = Color.Transparent,
        tickColor: Color = Color.White,
        borderColor: Color = Color(53, 61, 53)
    ): RoundCheckBoxColors = RoundCheckBoxColors(
        selectedColor,
        disabledSelectedColor,
        disabledUnselectedColor,
        tickColor,
        borderColor
    )
}

@Immutable
class RoundCheckBoxColors internal constructor(
    private val selectedColor: Color,
    private val disabledSelectedColor: Color,
    private val disabledUnselectedColor: Color,
    private val tickColor: Color,
    private val borderColor: Color
){
    @Composable
    internal fun roundCheckBoxColors(enabled: Boolean, isChecked: Boolean): State<Color> {
        val target = when {
            enabled && isChecked -> selectedColor
            enabled && !isChecked -> selectedColor
            !enabled && isChecked -> disabledSelectedColor
            else -> disabledUnselectedColor
        }
        return rememberUpdatedState(target)
    }

    @Composable
    internal fun roundCheckBoxTickColor(): State<Color> {
        return remember{ mutableStateOf(tickColor) }
    }

    @Composable
    internal fun roundCheckBoxBorderColor(): State<Color> {
        return remember{ mutableStateOf(borderColor) }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is RoundCheckBoxColors) return false

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

private val boxStrokeWidth = 2.dp
private const val TICK_STROKE_WIDTH = 5f
private val maxRadius = 10.dp
private val boxPadding = 2.dp
private val requiredSize = 30.dp
