package net.emrekalkan.locktimer.presentation.ui.screen.admin

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import net.emrekalkan.locktimer.R

class LockScreenAdminReceiver : DeviceAdminReceiver() {

    override fun onEnabled(context: Context, intent: Intent) {
        context.showToast(context.getString(R.string.admin_permission_enabled))
    }

    override fun onDisabled(context: Context, intent: Intent) {
        context.showToast(context.getString(R.string.admin_permission_disabled))
    }

    override fun onDisableRequested(context: Context, intent: Intent): CharSequence {
        return context.getString(R.string.admin_disable_request_description)
    }

    private fun Context.showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}