package net.emrekalkan.locktimer.presentation.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.emrekalkan.locktimer.domain.usecase.DisableAdminPreferences
import net.emrekalkan.locktimer.presentation.ui.screen.admin.DeviceAdminState
import net.emrekalkan.locktimer.presentation.util.countdown.CountDownTimer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCountDownTimer() = CountDownTimer()

    @Singleton
    @Provides
    fun provideDeviceAdminState(
        @ApplicationContext context: Context,
        disableAdminPreferences: DisableAdminPreferences
    ): DeviceAdminState {
        return DeviceAdminState(context, disableAdminPreferences)
    }
}