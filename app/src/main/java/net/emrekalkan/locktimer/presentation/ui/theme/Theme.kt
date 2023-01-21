package net.emrekalkan.locktimer.presentation.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = ColorBananaYellow,
    primaryVariant = Color.DarkGray,
    secondary = Color.Yellow,
    background = ColorGunMetal,
    surface = ColorGunMetal,
    onPrimary = ColorGunMetal,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun LockTimerTheme(content: @Composable () -> Unit) {
    rememberSystemUiController().apply {
        setStatusBarColor(DarkColorPalette.background)
        setNavigationBarColor(DarkColorPalette.background)
        setSystemBarsColor(DarkColorPalette.background)
    }
    MaterialTheme(
        colors = DarkColorPalette,
        content = content,
        typography = LockTimerTypography,
        shapes = LockTimerShapes
    )
}