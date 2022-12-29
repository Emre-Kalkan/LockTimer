package net.emrekalkan.locktimer.presentation.ui.screen

abstract class Screen(val routeName: String) {

    val routeFormula: String
        get() = "$routeName$argsFormatted"

    open val args: List<String> = emptyList()

    private val argsFormatted: String
        get() {
            if (args.isEmpty()) return ""

            val formatted = args.joinToString(prefix = "{", postfix = "}") { it }
            return "/$formatted"
        }
}