package net.emrekalkan.locktimer.domain.model

import android.Manifest
import android.os.Build

sealed class TimerActionPermission(
    open val permissions: List<String>
) {
    object Bluetooth : TimerActionPermission(listOf()) {
        override val permissions: List<String>
            get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                listOf(Manifest.permission.BLUETOOTH_CONNECT)
            } else {
                listOf()
            }
    }
}
