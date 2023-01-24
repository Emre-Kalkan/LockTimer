package net.emrekalkan.locktimer.presentation.ui.screen.preferences.actions

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import net.emrekalkan.locktimer.presentation.ui.screen.admin.DeviceAdminState
import javax.inject.Inject

class LockScreenAction @Inject constructor(
    @ApplicationContext private val context: Context,
    private val deviceAdminState: DeviceAdminState
) : TimerAction {
    override fun perform() {
        if (deviceAdminState.isAdmin.not()) return

        deviceAdminState.runDevicePolicyManager {
            lockNow()
        }
    }
}