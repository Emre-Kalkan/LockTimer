package net.emrekalkan.locktimer.presentation.ui.screen.preferences.actions

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import net.emrekalkan.locktimer.presentation.util.extensions.devicePolicyManager
import net.emrekalkan.locktimer.presentation.util.extensions.isAdminActive
import javax.inject.Inject

class LockScreenAction @Inject constructor(
    @ApplicationContext private val context: Context
) : TimerAction {
    override fun perform() {
        if (context.isAdminActive().not()) return

        context.devicePolicyManager {
            lockNow()
        }
    }
}