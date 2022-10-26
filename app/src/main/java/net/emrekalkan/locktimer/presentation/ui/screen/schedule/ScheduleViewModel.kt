package net.emrekalkan.locktimer.presentation.ui.screen.schedule

import net.emrekalkan.locktimer.presentation.base.BaseViewModel
import net.emrekalkan.locktimer.presentation.base.State
import net.emrekalkan.locktimer.presentation.ui.screen.schedule.ScheduleViewModel.ScheduleUiState
import net.emrekalkan.locktimer.presentation.ui.screen.schedule.count_down.CountDownAction

class ScheduleViewModel : BaseViewModel<ScheduleUiState>(ScheduleUiState()) {

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
}