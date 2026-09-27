package ru.mtuci.drivenext

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class NoConnectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_connection)

        findViewById<Button>(R.id.retryButton).setOnClickListener {
            if (isNetworkAvailable()) {
                val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                val onboardingDone = prefs.getBoolean("onboarding_done", false)
                val next = if (!onboardingDone) OnboardingActivity::class.java
                else MainActivity::class.java
                startActivity(Intent(this, next))
                finish()
            }
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val caps = cm.getNetworkCapabilities(network) ?: return false
        return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}