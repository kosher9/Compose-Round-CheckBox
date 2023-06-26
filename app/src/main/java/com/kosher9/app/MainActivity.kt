package com.kosher9.app

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kosher9.app.ui.theme.ComposeNiceCheckBoxTheme
import com.kosher9.roundcheckbox.RoundCheckBox
import com.kosher9.roundcheckbox.RoundCheckBoxDefaults

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNiceCheckBoxTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RoundCheckBoxes()
                }
            }
        }
    }
}

@Composable
fun RoundCheckBoxes(modifier: Modifier = Modifier) {

    var roundCheckBoxEnableState by remember { mutableStateOf(false) }
    var roundCheckBoxDisableState by remember { mutableStateOf(false) }
    var roundCheckBoxCheckedDisableState by remember { mutableStateOf(true) }
    var customRoundCheckBoxDisableState by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(top = 50.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Enabled",
                modifier = modifier
            )

            RoundCheckBox(
                modifier = Modifier.width(60.dp),
                isChecked = roundCheckBoxEnableState,
                onClick = { roundCheckBoxEnableState = !roundCheckBoxEnableState },
                enabled = true
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Disabled",
                modifier = modifier
            )

            RoundCheckBox(
                modifier = Modifier.width(60.dp),
                isChecked = roundCheckBoxDisableState,
                onClick = { roundCheckBoxDisableState = !roundCheckBoxDisableState },
                enabled = false
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Disabled & Checked",
                modifier = modifier
            )

            RoundCheckBox(
                modifier = Modifier.width(60.dp),
                isChecked = roundCheckBoxCheckedDisableState,
                onClick = { roundCheckBoxCheckedDisableState = !roundCheckBoxCheckedDisableState },
                enabled = false
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Custom",
                modifier = modifier
            )

            RoundCheckBox(
                modifier = Modifier.width(60.dp),
                radius = 10.dp,
                color = RoundCheckBoxDefaults.colors(Color(20, 138, 254), Color(0, 0, 0), Color(20, 138, 254), Color.White, Color(20, 138, 254)),
                isChecked = customRoundCheckBoxDisableState,
                onClick = { customRoundCheckBoxDisableState = !customRoundCheckBoxDisableState },
                enabled = true
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeNiceCheckBoxTheme {
        RoundCheckBoxes()
    }
}