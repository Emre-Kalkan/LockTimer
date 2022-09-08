package net.emrekalkan.locktimer.presentation.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = Color.Black,
    primaryVariant = Color.DarkGray,
    secondary = Color.Yellow,
    background = Color.Black,
    surface = Color.Black,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun LockTimerTheme(content: @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()
    systemUiController.apply {
        setStatusBarColor(DarkColorPalette.primary)
        setNavigationBarColor(DarkColorPalette.primary)
        setSystemBarsColor(DarkColorPalette.primary)
    }
    MaterialTheme(
        colors = DarkColorPalette,
        content = content,
        typography = LockTimerTypography,
        shapes = LockTimerShapes
    )
}