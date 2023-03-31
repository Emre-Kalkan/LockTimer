package net.emrekalkan.locktimer.presentation.ui.screen.base

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import net.emrekalkan.locktimer.presentation.ui.screen.interstitial.InterstitialAdManager

const val LockTimerRoute = "LockTimerRoute"

@Composable
fun LockTimer() {
    val navController = rememberNavController()
    val context = LocalContext.current

    addInterstitialWatcher(context, navController)
    Scaffold {
        NavHost(
            navController = navController,
            startDestination = LockTimerRoute,
            modifier = Modifier.padding(it)
        ) {
            createNavGraph(navController)
        }
    }
}

private fun addInterstitialWatcher(context: Context, navController: NavController) {
    navController.addOnDestinationChangedListener { _, _, _ ->
        InterstitialAdManager.apply {
            tryLoad(context)
            tryShow(context)
        }
    }
}