package net.emrekalkan.locktimer.presentation.ui.screen.base

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

const val LockTimerRoute = "LockTimerRoute"

@Composable
fun LockTimer() {
    val navController = rememberNavController()
    val context = LocalContext.current

    Scaffold {
        NavHost(
            navController = navController,
            startDestination = LockTimerRoute,
            modifier = Modifier.padding(it)
        ) {
            createNavGraph(context, navController)
        }
    }
}