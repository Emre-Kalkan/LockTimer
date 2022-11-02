package net.emrekalkan.locktimer.presentation.ui.screen.schedule

import net.emrekalkan.locktimer.presentation.util.DateFormatter
import net.emrekalkan.locktimer.presentation.util.toIntOrZero

sealed class SchedulerOption {
    data class SpecificOption(val timeInMinutes: Int) : SchedulerOption()
    data class CustomOption(val timeInMinutes: Int = 0) : SchedulerOption()

    val timeInMillis: Long
        get() = when (this) {
            // TODO(wtf?)
            is CustomOption -> DateFormatter.minutesToMillis(timeInMinutes)
            is SpecificOption -> DateFormatter.minutesToMillis(timeInMinutes)
        }

    fun isEqual(timeText: String): Boolean {
        val timeInMinutes = timeText.toIntOrZero()
        return DateFormatter.minutesToMillis(timeInMinutes) == timeInMillis
    }

    companion object {
        val defaults: List<SchedulerOption>
            get() = listOf(
                SpecificOption(5),
                SpecificOption(15),
                SpecificOption(30),
                SpecificOption(60),
                SpecificOption(90),
                CustomOption()
            )
    }
}
