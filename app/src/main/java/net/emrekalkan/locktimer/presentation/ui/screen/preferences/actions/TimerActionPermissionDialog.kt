@file:OptIn(ExperimentalPermissionsApi::class)

package net.emrekalkan.locktimer.presentation.ui.screen.preferences.actions

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import net.emrekalkan.locktimer.R
import net.emrekalkan.locktimer.presentation.util.Text
import net.emrekalkan.locktimer.presentation.util.composables.Dialog
import net.emrekalkan.locktimer.presentation.util.composables.DialogParams
import net.emrekalkan.locktimer.presentation.util.composables.OnDismiss

@Composable
fun TimerActionPermissionDialog(
    revokedPermissions: List<PermissionState>,
    onDismiss: OnDismiss
) {
    val context = LocalContext.current
    val dialogParams = DialogParams(
        title = Text.Res(R.string.dialog_title_permission_required),
        description = Text.Raw(getRevokedPermissionsRationale(LocalContext.current, revokedPermissions)),
        positive = R.string.dialog_settings to {
            val appSettingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                data = Uri.fromParts("package", context.packageName, null)
            }
            context.startActivity(appSettingsIntent)
        },
        negative = R.string.dialog_cancel to { onDismiss() }
    )

    Dialog(params = dialogParams)
}

private fun getRevokedPermissionsRationale(context: Context, revokedPermissions: List<PermissionState>): String {
    val requiredPermissionTitles = revokedPermissions.map { permissionState ->
        when (permissionState.permission) {
            Manifest.permission.BLUETOOTH_CONNECT -> context.getString(R.string.bluetooth)
            else -> ""
        }
    }

    if (requiredPermissionTitles.isEmpty()) return ""

    val joinedTitles = requiredPermissionTitles.joinToString(separator = ",", prefix = "\n")
    return context.getString(R.string.permissions_rationale, joinedTitles)
}