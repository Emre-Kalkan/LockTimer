package net.emrekalkan.locktimer.presentation.ui.screen.preferences.actions

import android.content.Context
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StopAudioVideoAction @Inject constructor(
    @ApplicationContext private val context: Context
) : TimerAction {
    override fun perform() {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val request = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN).build()
            audioManager.requestAudioFocus(request)
        } else {
            @Suppress("DEPRECATION")
            audioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
        }
    }
}