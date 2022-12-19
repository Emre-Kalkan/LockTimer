package net.emrekalkan.locktimer.presentation.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.emrekalkan.locktimer.data.local.PreferenceDataStore
import net.emrekalkan.locktimer.presentation.ui.screen.schedule.count_down.CountDownTimer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCountDownTimer() = CountDownTimer()

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext context: Context) = PreferenceDataStore(context)
}