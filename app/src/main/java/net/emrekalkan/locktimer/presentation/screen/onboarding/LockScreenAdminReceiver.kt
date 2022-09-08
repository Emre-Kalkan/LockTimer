package net.emrekalkan.locktimer.presentation.screen.onboarding

import android.app.admin.DeviceAdminReceiver
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.plus
import javax.inject.Inject

class LockScreenAdminReceiver : DeviceAdminReceiver() {

    private val scope = CoroutineScope(Dispatchers.Default) + SupervisorJob()

    private val _adminEvents = MutableSharedFlow<DeviceAdminEvents>()
    val adminEvents: SharedFlow<DeviceAdminEvents> = _adminEvents

    override fun onEnabled(context: Context, intent: Intent) {
        context.showToast("Admin enabled")
    }

    override fun onDisabled(context: Context, intent: Intent) {
        context.showToast("Sample Device Admin: disabled")
    }

    override fun onDisableRequested(context: Context, intent: Intent): CharSequence {
        return "Pls do not disable..."
    }

    private fun Context.showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}