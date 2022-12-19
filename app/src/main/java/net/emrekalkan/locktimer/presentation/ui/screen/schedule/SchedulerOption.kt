package net.emrekalkan.locktimer.presentation.ui.screen.schedule

import net.emrekalkan.locktimer.presentation.util.DateFormatter

sealed class SchedulerOption(
    open val timeInMinutes: Int
) {
    data class SpecificOption(override val timeInMinutes: Int) : SchedulerOption(timeInMinutes)
    data class CustomOption(override val timeInMinutes: Int = 0) : SchedulerOption(timeInMinutes)

    val timeInMillis: Long
        get() = DateFormatter.minutesToMillis(timeInMinutes)

    companion object {
        val defaults: List<SchedulerOption>
            get() = listOf(
                SpecificOption(timeInMinutes = 5),
                SpecificOption(timeInMinutes = 15),
                SpecificOption(timeInMinutes = 30),
                SpecificOption(timeInMinutes = 60),
                SpecificOption(timeInMinutes = 90),
                CustomOption(timeInMinutes = 0)
            )
    }
}
