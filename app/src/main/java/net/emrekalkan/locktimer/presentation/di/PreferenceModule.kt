package net.emrekalkan.locktimer.presentation.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.emrekalkan.locktimer.data.local.PreferenceDataStore
import net.emrekalkan.locktimer.presentation.ui.screen.preferences.availability.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext context: Context) = PreferenceDataStore(context)

    @Singleton
    @Provides
    fun providePreferencesAvailability(
        lockScreenAvailability: LockScreenAvailability,
        stopAudioVideoAvailability: StopAudioVideoAvailability,
        bluetoothAvailability: DisableBluetoothAvailability,
        disableWifiAvailability: DisableWifiAvailability,
        cellularDataAvailability: DisableCellularDataAvailability,
    ): List<PreferenceAvailability> {
        return listOf(lockScreenAvailability, stopAudioVideoAvailability, bluetoothAvailability, disableWifiAvailability, cellularDataAvailability)
    }

    @Singleton
    @Provides
    fun provideLockScreenAvailability(@ApplicationContext context: Context): LockScreenAvailability {
        return LockScreenAvailability(context)
    }

    @Singleton
    @Provides
    fun provideStopAudioVideoAvailability(): StopAudioVideoAvailability {
        return StopAudioVideoAvailability()
    }

    @Singleton
    @Provides
    fun provideDisableBluetoothAvailability(
        @ApplicationContext context: Context
    ): DisableBluetoothAvailability {
        return DisableBluetoothAvailability(context)
    }

    @Singleton
    @Provides
    fun provideDisableCellularDataAvailability(): DisableCellularDataAvailability {
        return DisableCellularDataAvailability()
    }

    @Singleton
    @Provides
    fun provideDisableWifiAvailability(): DisableWifiAvailability {
        return DisableWifiAvailability()
    }
}