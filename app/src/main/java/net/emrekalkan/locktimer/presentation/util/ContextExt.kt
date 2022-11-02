package net.emrekalkan.locktimer.presentation.util

import android.content.Context
import net.emrekalkan.locktimer.presentation.base.BaseActivity

val Context.baseActivity: BaseActivity
    get() = this as BaseActivity