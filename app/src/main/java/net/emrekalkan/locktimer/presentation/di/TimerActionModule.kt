package net.emrekalkan.locktimer.presentation.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.emrekalkan.locktimer.presentation.ui.screen.preferences.actions.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TimerActionModule {

    @Singleton
    @Provides
    fun provideTimerActions(
        stopAudioVideoAction: StopAudioVideoAction,
        lockScreenAction: LockScreenAction,
        disableBluetoothAction: DisableBluetoothAction,
        disableWifiAction: DisableWifiAction
    ): List<TimerAction> {
        return listOf(
            stopAudioVideoAction,
            lockScreenAction,
            disableBluetoothAction,
            disableWifiAction
        )
    }

    @Singleton
    @Provides
    fun provideStopAudioVideoAction(@ApplicationContext context: Context): StopAudioVideoAction {
        return StopAudioVideoAction(context)
    }

    @Singleton
    @Provides
    fun provideLockScreenAction(@ApplicationContext context: Context): LockScreenAction {
        return LockScreenAction(context)
    }

    @Singleton
    @Provides
    fun provideDisableBluetoothAction(@ApplicationContext context: Context): DisableBluetoothAction {
        return DisableBluetoothAction(context)
    }
}