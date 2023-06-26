package com.example.composenicecheckbox

class NiceCheckBoxTest {

//    @get: Rule
//    val composeTestRule = createComposeRule()
//
//    @Test
//    fun checkBoxDisabledShouldNotClick() {
//        composeTestRule.setContent {
//            ComposeNiceCheckBoxTheme {
//                Surface(
//                    modifier = Modifier
//                        .fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(5.dp),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        com.github.kosher9.roundcheckbox.NiceCheckBox(
//                            isChecked = false,
//                            onClick = {},
//                            enabled = false,
//                            modifier = Modifier.semantics { contentDescription = "checkbox" })
//                    }
//                }
//            }
//        }
//        composeTestRule.onNodeWithContentDescription("checkbox").assertIsNotEnabled()
//        composeTestRule.onNodeWithContentDescription("checkbox").assertIsNotSelected()
//        composeTestRule.onNodeWithContentDescription("checkbox").assertHasClickAction()
//    }
//
//    @Test
//    fun checkBoxEnabledShouldClick() {
//        composeTestRule.setContent {
//            ComposeNiceCheckBoxTheme {
//                Surface(
//                    modifier = Modifier
//                        .fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    var state by remember { mutableStateOf(false) }
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(5.dp),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        com.github.kosher9.roundcheckbox.NiceCheckBox(
//                            isChecked = state,
//                            onClick = { state = !state },
//                            enabled = true,
//                            modifier = Modifier.semantics { contentDescription = "checkbox" })
//                    }
//                }
//            }
//        }
//        composeTestRule.onNodeWithContentDescription("checkbox").assertIsEnabled()
//        composeTestRule.onNodeWithContentDescription("checkbox").performClick()
//        composeTestRule.onNodeWithContentDescription("checkbox").assertIsSelected()
//        composeTestRule.onNodeWithContentDescription("checkbox").assertHasClickAction()
//    }
}