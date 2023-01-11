package net.emrekalkan.locktimer.presentation.ui.screen.preferences.actions

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DisableBluetoothAction @Inject constructor(
    @ApplicationContext private val context: Context
) : TimerAction {
    override fun perform() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val isBluetoothPermissionGranted = ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED
            if (isBluetoothPermissionGranted.not()) return
        }

        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter.isEnabled) {
            bluetoothAdapter.disable()
        }
    }
}