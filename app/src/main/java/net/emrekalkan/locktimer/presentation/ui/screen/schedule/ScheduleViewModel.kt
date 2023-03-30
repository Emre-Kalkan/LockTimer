package net.emrekalkan.locktimer.presentation.ui.screen.schedule

import net.emrekalkan.locktimer.presentation.ui.base.BaseViewModel
import net.emrekalkan.locktimer.presentation.ui.base.Event
import net.emrekalkan.locktimer.presentation.ui.base.State
import net.emrekalkan.locktimer.presentation.ui.screen.schedule.ScheduleViewModel.ScheduleEvent
import net.emrekalkan.locktimer.presentation.ui.screen.schedule.ScheduleViewModel.ScheduleUiState
import net.emrekalkan.locktimer.presentation.util.countdown.CountDownAction

class ScheduleViewModel : BaseViewModel<ScheduleUiState, ScheduleEvent>(ScheduleUiState()) {

    fun onOptionClicked(option: SchedulerOption?) {
        setState {
            copy(selectedOption = option, isScheduled = false)
        }

        if (option !is SchedulerOption.CustomOption) {
            setEvent { ScheduleEvent.HideKeyboard }
        }
    }

    fun getCountDownAction(): CountDownAction? {
        val option = currentUiState.selectedOption ?: return null

        return CountDownAction.Start(timeInMillis = option.timeInMillis)
    }

    fun onCountDownStarted() {
        setState {
            copy(selectedOption = null, isScheduled = true)
        }
    }

    data class ScheduleUiState(
        val options: List<SchedulerOption> = SchedulerOption.defaults,
        val selectedOption: SchedulerOption? = null,
        val isScheduled: Boolean = false
    ) : State

    sealed class ScheduleEvent : Event {
        object HideKeyboard: ScheduleEvent()
    }
}