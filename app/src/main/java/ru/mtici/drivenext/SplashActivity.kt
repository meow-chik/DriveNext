package ru.mtuci.drivenext

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)

            if (!isNetworkAvailable()) {
                startActivity(Intent(this@SplashActivity, NoConnectionActivity::class.java))
            } else {
                val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                val onboardingDone = prefs.getBoolean("onboarding_done", false)
                if (!onboardingDone) {
                    startActivity(Intent(this@SplashActivity, OnboardingActivity::class.java))
                } else {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                }
            }
            finish()
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val caps = cm.getNetworkCapabilities(network) ?: return false
        return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}