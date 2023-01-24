package net.emrekalkan.locktimer.presentation.ui.screen.admin

import dagger.hilt.android.lifecycle.HiltViewModel
import net.emrekalkan.locktimer.presentation.ui.base.BaseViewModel
import net.emrekalkan.locktimer.presentation.ui.base.Event
import net.emrekalkan.locktimer.presentation.ui.base.State
import net.emrekalkan.locktimer.presentation.ui.screen.admin.AdminPermissionViewModel.AdminPermissionScreenEvent
import net.emrekalkan.locktimer.presentation.ui.screen.admin.AdminPermissionViewModel.AdminPermissionScreenEvent.NavigateBack
import net.emrekalkan.locktimer.presentation.ui.screen.admin.AdminPermissionViewModel.AdminPermissionScreenState
import javax.inject.Inject

@HiltViewModel
class AdminPermissionViewModel @Inject constructor() : BaseViewModel<AdminPermissionScreenState, AdminPermissionScreenEvent>(AdminPermissionScreenState) {

    fun onAdminResult(isAdmin: Boolean) {
        if (isAdmin) {
            setEvent { NavigateBack }
        }
    }

    object AdminPermissionScreenState : State
    sealed class AdminPermissionScreenEvent : Event {
        object NavigateBack : AdminPermissionScreenEvent()
    }
}