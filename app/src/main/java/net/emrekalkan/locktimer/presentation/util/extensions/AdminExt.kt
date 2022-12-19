package net.emrekalkan.locktimer.presentation.util.extensions

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import net.emrekalkan.locktimer.presentation.ui.screen.onboarding.LockScreenAdminReceiver

val Context.adminComponent: ComponentName
    get() = ComponentName(this, LockScreenAdminReceiver::class.java)

fun Context.uninstallApp() {
    removeAdmin()
    Intent(Intent.ACTION_DELETE).apply {
        data = Uri.parse("package:${applicationInfo.packageName}")
        startActivity(this)
    }
}

fun <T> Context.devicePolicyManager(run: DevicePolicyManager.(ComponentName) -> T): T {
    val devicePolicyManager = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
    return devicePolicyManager.run(adminComponent)
}

fun Context.removeAdmin() {
    devicePolicyManager { removeActiveAdmin(it) }
}

fun Context.isAdminActive(): Boolean {
    return devicePolicyManager { isAdminActive(it) }
}