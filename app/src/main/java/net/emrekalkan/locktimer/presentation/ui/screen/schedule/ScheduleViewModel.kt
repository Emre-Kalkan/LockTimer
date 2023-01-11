package net.emrekalkan.locktimer.presentation.ui.screen.schedule

import net.emrekalkan.locktimer.presentation.base.BaseViewModel
import net.emrekalkan.locktimer.presentation.base.Event
import net.emrekalkan.locktimer.presentation.base.State
import net.emrekalkan.locktimer.presentation.ui.screen.schedule.ScheduleViewModel.ScheduleEvent
import net.emrekalkan.locktimer.presentation.ui.screen.schedule.ScheduleViewModel.ScheduleUiState
import net.emrekalkan.locktimer.presentation.util.countdown.CountDownAction

class ScheduleViewModel : BaseViewModel<ScheduleUiState, ScheduleEvent>(ScheduleUiState()) {

    fun onOptionClicked(option: SchedulerOption?) {
        setState {
            copy(selectedOption = option)
        }
    }

    fun getCountDownAction(): CountDownAction? {
        val option = currentUiState.selectedOption ?: return null

        return CountDownAction.Start(timeInMillis = option.timeInMillis)
    }

    data class ScheduleUiState(
        val options: List<SchedulerOption> = SchedulerOption.defaults,
        val selectedOption: SchedulerOption? = null
    ) : State

    sealed class ScheduleEvent: Event
}