package net.emrekalkan.locktimer.presentation.ui.screen.base

import android.content.Context
import androidx.navigation.*
import androidx.navigation.compose.composable
import net.emrekalkan.locktimer.presentation.ui.screen.onboarding.OnBoardingScreen
import net.emrekalkan.locktimer.presentation.ui.screen.preferences.PreferenceScreen
import net.emrekalkan.locktimer.presentation.ui.screen.schedule.ScheduleScreen
import net.emrekalkan.locktimer.presentation.ui.screen.splash.SplashScreen
import net.emrekalkan.locktimer.presentation.util.extensions.isAdminActive

fun NavGraphBuilder.createNavGraph(
    context: Context,
    navController: NavController
) {
    navigation(startDestination = SplashScreen.routeFormula, route = LockTimerRoute) {
        composable(SplashScreen.routeFormula) {
            SplashScreen(
                navigateToSchedule = {
                    navController.navigate(ScheduleScreen.routeFormula) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            OnBoardingScreen.routeFormula,
            arguments = listOf(
                navArgument(OnBoardingScreen.ARG_BACK_ROUTE) {
                    type = NavType.StringType
                }
            )
        ) {
            OnBoardingScreen(
                onBackButtonClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(ScheduleScreen.routeFormula) {
            ScheduleScreen(
                navigateToSettings = {
                    navController.navigate(PreferenceScreen.routeFormula)
                }
            )
        }
        composable(PreferenceScreen.routeFormula) {
            PreferenceScreen(
                navigateToOnBoarding = {
                    val route = OnBoardingScreen.getRoute(PreferenceScreen.routeName)
                    navController.navigate(route)
                },
                onBackButtonClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}