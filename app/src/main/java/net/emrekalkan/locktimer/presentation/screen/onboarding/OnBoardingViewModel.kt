package net.emrekalkan.locktimer.presentation.screen.onboarding

import dagger.hilt.android.lifecycle.HiltViewModel
import net.emrekalkan.locktimer.presentation.base.BaseViewModel
import net.emrekalkan.locktimer.presentation.base.State
import net.emrekalkan.locktimer.presentation.screen.onboarding.OnBoardingViewModel.OnBoardingState
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor() : BaseViewModel<OnBoardingState>(OnBoardingState()) {

    fun onAdminResult(adminEnabled: Boolean) {
        setState {
            copy(adminEnabled = adminEnabled)
        }
    }

    data class OnBoardingState(
        val title: String = "On Boarding Screen",
        val adminEnabled: Boolean = false
    ) : State
}