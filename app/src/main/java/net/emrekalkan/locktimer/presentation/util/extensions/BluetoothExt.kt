package net.emrekalkan.locktimer.presentation.util.extensions

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context

fun Context.hasBluetooth(): Boolean {
    return getBluetoothAdapter() != null
}

fun Context.getBluetoothAdapter(): BluetoothAdapter? {
    val manager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    return manager.adapter
}