package net.emrekalkan.locktimer.presentation.ui.screen.schedule

import net.emrekalkan.locktimer.presentation.util.DateFormatter

sealed class SchedulerOption {
    data class SpecificOption(val timeInMinutes: Int) : SchedulerOption()
    object CustomOption : SchedulerOption()

    val timeInMillis: Long
        get() = when (this) {
            CustomOption -> 0 // TODO
            is SpecificOption -> DateFormatter.minutesToMillis(timeInMinutes)
        }

    companion object {
        val defaults: List<SchedulerOption>
            get() = listOf(
                SpecificOption(5),
                SpecificOption(15),
                SpecificOption(30),
                SpecificOption(60),
                SpecificOption(90),
            )
    }
}
