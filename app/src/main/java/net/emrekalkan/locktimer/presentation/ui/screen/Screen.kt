package net.emrekalkan.locktimer.presentation.ui.screen

abstract class Screen(private val name: String) {
    val route: String
        get() = name
}