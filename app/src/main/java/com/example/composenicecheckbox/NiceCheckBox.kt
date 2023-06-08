//package com.example.composenicecheckbox
//
//import androidx.compose.foundation.Canvas
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.material3.Checkbox
//import androidx.compose.material3.RadioButton
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.Immutable
//import androidx.compose.runtime.State
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//
//@Composable
//fun NiceCheckBox(
//    enable: Boolean,
//    isChecked: Boolean,
//    onClick: (() -> Unit),
//    modifier: Modifier = Modifier,
//    color: NiceCheckBoxColors = NiceCheckBoxDefaults.Colors(),
//    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
//) {
//
////    RadioButton(selected = , onClick = { /*TODO*/ })
////
////    Checkbox(checked = , onCheckedChange = )
//
//    Canvas(modifier = modifier) {
//
//    }
//
//}
//
//object NiceCheckBoxDefaults{
//
//    @Composable
//    fun Colors(): NiceCheckBoxColors = NiceCheckBoxColors(
//
//    )
//
//}
//
//@Immutable
//class NiceCheckBoxColors internal constructor(
//    private val selectedColor: Color,
//    private val unselectedColor: Color,
//    private val disabledSelectedColor: Color,
//    private val disabledUnselectedColor: Color
//){
//
//    @Composable
//    internal fun niceCheckColors(enabled: Boolean, isChecked: Boolean): State<Color> {
//
//    }
//
//}
