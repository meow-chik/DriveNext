package ru.mtuci.drivenext

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class OnboardingActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var nextButton: Button
    private lateinit var indicatorLayout: LinearLayout

    private val pages by lazy {
        listOf(
            OnboardingFragment.newInstance(
                R.drawable.onboarding_1,
                "Аренда автомобилей",
                "Открой для себя удобный и доступный способ передвижения"
            ),
            OnboardingFragment.newInstance(
                R.drawable.onboarding_2,
                "Безопасно и удобно",
                "Арендуй автомобиль и наслаждайся его удобством"
            ),
            OnboardingFragment.newInstance(
                R.drawable.onboarding_3,
                "Лучшие предложения",
                "Выбирай понравившееся среди сотен доступных автомобилей"
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        viewPager = findViewById(R.id.viewPager)
        nextButton = findViewById(R.id.nextButton)
        indicatorLayout = findViewById(R.id.indicatorLayout)

        viewPager.adapter = OnboardingAdapter(this, pages)
        setupIndicators(pages.size, 0)
        updateButtonForPage(0)

        findViewById<TextView>(R.id.skipButton).setOnClickListener { finishOnboarding() }

        nextButton.setOnClickListener {
            val current = viewPager.currentItem
            if (current < pages.size - 1) viewPager.currentItem = current + 1
            else finishOnboarding()
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                setupIndicators(pages.size, position)
                updateButtonForPage(position)
            }
        })
    }

    private fun updateButtonForPage(position: Int) {
        nextButton.text = if (position == pages.size - 1) "Поехали" else "Далее"
    }

    private fun setupIndicators(count: Int, selected: Int) {
        indicatorLayout.removeAllViews()
        for (i in 0 until count) {
            val dot = ImageView(this).apply {
                setImageResource(if (i == selected) R.drawable.dot_active else R.drawable.dot_inactive)
                val size = (8 * resources.displayMetrics.density).toInt()
                layoutParams = LinearLayout.LayoutParams(size, size).apply {
                    marginStart = (4 * resources.displayMetrics.density).toInt()
                    marginEnd   = (4 * resources.displayMetrics.density).toInt()
                }
            }
            indicatorLayout.addView(dot)
        }
    }

    private fun finishOnboarding() {
        getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            .edit().putBoolean("onboarding_done", true).apply()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}