package com.kosher9.app

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kosher9.app.ui.theme.ComposeNiceCheckBoxTheme
import com.kosher9.roundcheckbox.RoundCheckBox

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
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    var radioButtonState by remember { mutableStateOf(false) }
    var niceCheckBoxState by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "Hello $name!",
            modifier = modifier
        )
//        Checkbox(checked = , onCheckedChange = )

        RadioButton(selected = radioButtonState, onClick = { radioButtonState = !radioButtonState })

        RoundCheckBox(
            modifier = Modifier.width(60.dp),
            radius = 10.dp,
            isChecked = niceCheckBoxState,
            onClick = { niceCheckBoxState = !niceCheckBoxState },
            enabled = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeNiceCheckBoxTheme {
        Greeting("Android")
    }
}