package net.emrekalkan.locktimer.presentation.ui.screen.base

import androidx.navigation.*
import androidx.navigation.compose.composable
import net.emrekalkan.locktimer.presentation.ui.screen.admin.AdminPermissionScreen
import net.emrekalkan.locktimer.presentation.ui.screen.preferences.PreferenceScreen
import net.emrekalkan.locktimer.presentation.ui.screen.schedule.ScheduleScreen
import net.emrekalkan.locktimer.presentation.ui.screen.splash.SplashScreen

fun NavGraphBuilder.createNavGraph(
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
            AdminPermissionScreen.routeFormula,
            arguments = listOf(
                navArgument(AdminPermissionScreen.ARG_BACK_ROUTE) {
                    type = NavType.StringType
                }
            )
        ) {
            AdminPermissionScreen(
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
                navigateToAdminPermission = {
                    val route = AdminPermissionScreen.getRoute(PreferenceScreen.routeName)
                    navController.navigate(route)
                },
                onBackButtonClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}