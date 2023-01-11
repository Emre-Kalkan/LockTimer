package net.emrekalkan.locktimer.domain.model

import androidx.annotation.StringRes
import androidx.datastore.preferences.core.Preferences

data class TimerActionPreferenceModel(
    override val key: Preferences.Key<Boolean>,
    @StringRes override val titleRes: Int,
    override val value: Boolean = false,
    val requiresAdmin: Boolean = false,
    val actionPermission: TimerActionPermission? = null
) : PreferenceModel<Boolean>(key, titleRes, value)

