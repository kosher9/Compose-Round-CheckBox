package com.example.composenicecheckbox

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun NiceCheckBox(
    enable: Boolean,
    isChecked: Boolean,
    onClick: (() -> Unit),
    modifier: Modifier = Modifier,
    color: NiceCheckBoxColors = NiceCheckBoxDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {

//    RadioButton(selected = , onClick = { /*TODO*/ })
//
//    Checkbox(checked = , onCheckedChange = )

    Canvas(modifier = modifier) {

    }

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

}

private const val RadioAnimationDuration = 100
