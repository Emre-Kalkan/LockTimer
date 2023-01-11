package net.emrekalkan.locktimer.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.emrekalkan.locktimer.presentation.util.countdown.CountDownTimer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCountDownTimer() = CountDownTimer()
}