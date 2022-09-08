package net.emrekalkan.locktimer.presentation.ui.screen.base

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import net.emrekalkan.locktimer.presentation.ui.screen.onboarding.OnBoardingScreen
import net.emrekalkan.locktimer.presentation.ui.screen.schedule.ScheduleScreen
import net.emrekalkan.locktimer.presentation.ui.screen.splash.SplashScreen
import net.emrekalkan.locktimer.presentation.util.isAdminActive

fun NavGraphBuilder.createNavGraph(
    context: Context,
    navController: NavController
) {
    navigation(startDestination = SplashScreen.route, route = LockTimerRoute) {
        composable(SplashScreen.route) {
            SplashScreen(
                isAdmin = context.isAdminActive(),
                navigateToOnBoarding = {
                    navController.navigate(OnBoardingScreen.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                navigateToSchedule = {
                    navController.navigate(ScheduleScreen.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(OnBoardingScreen.route) {
            OnBoardingScreen(
                navigateToSchedule = {
                    navController.navigate(ScheduleScreen.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(ScheduleScreen.route) {
            ScheduleScreen()
        }
    }
}