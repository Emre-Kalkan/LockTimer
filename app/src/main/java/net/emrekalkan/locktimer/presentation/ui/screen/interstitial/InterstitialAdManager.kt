package net.emrekalkan.locktimer.presentation.ui.screen.interstitial

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import net.emrekalkan.locktimer.R
import net.emrekalkan.locktimer.presentation.util.dice.Dice
import net.emrekalkan.locktimer.presentation.util.dice.DiceChance

object InterstitialAdManager {

    private const val TAG = "InterstitialAdManager"

    private var interstitialAd: InterstitialAd? = null

    private var isLoading: Boolean = false

    private var isLocked: Boolean = true

    fun tryLoad(context: Context) {
        if (isLoading || interstitialAd != null) return

        Log.d(TAG, "tryLoad")
        isLoading = true
        val id = context.getString(R.string.admob_interstitial_id)
        val adRequest = AdRequest.Builder().build()
        val callback = object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(p0: LoadAdError) {
                interstitialAd = null
                isLoading = false
                tryLoad(context)
                Log.e(TAG, p0.message)
            }

            override fun onAdLoaded(ad: InterstitialAd) {
                interstitialAd = ad
                isLoading = false
                Log.d(TAG, "onAdLoaded")
            }
        }
        InterstitialAd.load(context, id, adRequest, callback)
    }

    fun tryShow(context: Context) {
        Log.d(TAG, "tryShow")
        val activity = context as? Activity
        if (canShowAd().not() || activity == null) return

        show(activity)
    }

    fun tryShow(context: Context, diceChance: DiceChance, onComplete: () -> Unit) {
        Log.d(TAG, "tryShow")
        val activity = context as? Activity
        if (canShowAd(diceChance).not() || activity == null) {
            onComplete()
            return
        }

        show(activity, onComplete)
    }

    fun unlock() {
        isLocked = false
    }

    private fun canShowAd(diceChance: DiceChance = DiceChance.MEDIUM): Boolean {
        val diceResult = Dice.roll(diceChance)
        return isLocked.not()
            .and(diceResult)
            .and(interstitialAd != null)
    }

    private fun show(activity: Activity, onComplete: () -> Unit = {}) {
        Log.d(TAG, "show")
        interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                interstitialAd = null
                onComplete()
                tryLoad(activity)
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                interstitialAd = null
                onComplete()
                tryLoad(activity)
            }
        }
        interstitialAd?.show(activity)
    }
}