package net.emrekalkan.locktimer.presentation.ui.screen.admin

import android.app.Activity
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import net.emrekalkan.locktimer.R

class AddAdminDeviceContract: ActivityResultContract<ComponentName, Boolean>() {
    override fun createIntent(context: Context, input: ComponentName): Intent {
        return Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).apply {
            putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, input)
            putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, context.getString(R.string.admin_receiver_description))
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        return resultCode == Activity.RESULT_OK
    }
}