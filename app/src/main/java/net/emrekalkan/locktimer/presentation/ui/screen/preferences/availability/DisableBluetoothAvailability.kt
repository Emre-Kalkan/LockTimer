package net.emrekalkan.locktimer.presentation.ui.screen.preferences.availability

import android.content.Context
import net.emrekalkan.locktimer.R
import net.emrekalkan.locktimer.domain.model.PreferenceModel
import net.emrekalkan.locktimer.domain.model.TimerActionPermission
import net.emrekalkan.locktimer.domain.model.TimerActionPreferenceModel
import net.emrekalkan.locktimer.domain.model.TimerActionType
import net.emrekalkan.locktimer.presentation.util.extensions.hasBluetooth

class DisableBluetoothAvailability(private val context: Context) : PreferenceAvailability {
    override val actionType: TimerActionType
        get() = TimerActionType.PREF_DISABLE_BLUETOOTH

    override fun checkAvailability(): Boolean = context.hasBluetooth()

    override fun getPreferenceModel(): PreferenceModel<*> {
        return TimerActionPreferenceModel(key = actionType.key, titleRes = R.string.pref_title_disable_bluetooth, actionPermission = TimerActionPermission.Bluetooth)
    }
}