package net.emrekalkan.locktimer.presentation.screen.schedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import net.emrekalkan.locktimer.presentation.screen.Screen
import net.emrekalkan.locktimer.presentation.util.removeAdmin

object ScheduleScreen : Screen(name = "ScheduleScreen")

@Preview
@Composable
fun ScheduleScreenPreview() {
    ScheduleScreen()
}

@Composable
fun ScheduleScreen() {
    val context = LocalContext.current
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Schedule Screen", style = MaterialTheme.typography.h6)
            Button(
                enabled = true,
                onClick = {
                    context.removeAdmin()
                }
            ) {
                Text(text = "Remove Admin")
            }
        }
    }
}