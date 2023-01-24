package net.emrekalkan.locktimer.presentation.ui.screen.admin

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import net.emrekalkan.locktimer.domain.usecase.DisableAdminPreferences

class DeviceAdminState(
    @ApplicationContext private val context: Context,
    private val disableAdminPreferences: DisableAdminPreferences
) {

    private val devicePolicyManager = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager

    private val adminComponent = ComponentName(context, LockScreenAdminReceiver::class.java)

    private val coroutineScope = CoroutineScope(Dispatchers.Default) + Job()

    private val _adminPermissionState = MutableStateFlow(isAdmin)
    val adminPermissionState: StateFlow<Boolean> = _adminPermissionState

    val isAdmin: Boolean
        get() = runDevicePolicyManager { isAdminActive(it) }

    fun onEnabled() {
        _adminPermissionState.value = true
    }

    fun onDisabled() {
        coroutineScope.launch {
            disableAdminPreferences()
            _adminPermissionState.emit(false)
        }
    }

    fun removeAdmin() {
        runDevicePolicyManager { removeActiveAdmin(it) }
    }

    fun <T> runDevicePolicyManager(run: DevicePolicyManager.(ComponentName) -> T): T {
        return devicePolicyManager.run(adminComponent)
    }

    fun uninstallApp() {
        removeAdmin()
        Intent(Intent.ACTION_DELETE).apply {
            data = Uri.parse("package:${context.applicationInfo.packageName}")
            context.startActivity(this)
        }
    }
}