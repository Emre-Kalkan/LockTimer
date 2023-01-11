package net.emrekalkan.locktimer.presentation.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.emrekalkan.locktimer.presentation.ui.screen.preferences.actions.DisableBluetoothAction
import net.emrekalkan.locktimer.presentation.ui.screen.preferences.actions.LockScreenAction
import net.emrekalkan.locktimer.presentation.ui.screen.preferences.actions.StopAudioVideoAction
import net.emrekalkan.locktimer.presentation.ui.screen.preferences.actions.TimerAction
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
    ): List<TimerAction> {
        return listOf(
            stopAudioVideoAction,
            lockScreenAction,
            disableBluetoothAction
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