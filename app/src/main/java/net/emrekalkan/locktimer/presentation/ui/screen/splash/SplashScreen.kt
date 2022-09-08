package net.emrekalkan.locktimer.presentation.ui.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.emrekalkan.locktimer.R
import net.emrekalkan.locktimer.presentation.ui.screen.Screen

object SplashScreen : Screen("SplashScreen")

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreenContent()
}

@Composable
fun SplashScreen(
    isAdmin: Boolean,
    navigateToOnBoarding: () -> Unit,
    navigateToSchedule: () -> Unit,
) {
    SplashScreenContent()
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit, block = {
        scope.launch {
            delay(1000)
            if (isAdmin) {
                navigateToSchedule()
            } else {
                navigateToOnBoarding()
            }
        }
    })
}

@Composable
fun SplashScreenContent() {
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "")
    }
}