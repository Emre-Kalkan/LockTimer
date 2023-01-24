package net.emrekalkan.locktimer.presentation.ui.screen.admin

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import net.emrekalkan.locktimer.R
import javax.inject.Inject

@AndroidEntryPoint
class LockScreenAdminReceiver : DeviceAdminReceiver() {

    @Inject
    lateinit var deviceAdminState: DeviceAdminState

    override fun onEnabled(context: Context, intent: Intent) {
        context.showToast(context.getString(R.string.admin_permission_enabled))
        deviceAdminState.onEnabled()
    }

    override fun onDisabled(context: Context, intent: Intent) {
        context.showToast(context.getString(R.string.admin_permission_disabled))
        deviceAdminState.onDisabled()
    }

    override fun onDisableRequested(context: Context, intent: Intent): CharSequence {
        return context.getString(R.string.admin_disable_request_description)
    }

    private fun Context.showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}