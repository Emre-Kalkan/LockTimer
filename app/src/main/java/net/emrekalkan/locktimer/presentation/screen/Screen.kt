package net.emrekalkan.locktimer.presentation.screen

abstract class Screen(private val name: String) {
    val route: String
        get() = name
}