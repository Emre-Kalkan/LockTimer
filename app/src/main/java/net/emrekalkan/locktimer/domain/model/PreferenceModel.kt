package net.emrekalkan.locktimer.domain.model

import androidx.annotation.StringRes
import androidx.datastore.preferences.core.Preferences

sealed class PreferenceModel<T>(
    open val key: Preferences.Key<Boolean>,
    @StringRes open val titleRes: Int,
    open val value: T?
)
