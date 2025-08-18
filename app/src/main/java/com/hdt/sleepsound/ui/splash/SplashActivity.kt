package com.hdt.sleepsound.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.hdt.sleepsound.base.BaseActivity
import com.hdt.sleepsound.MainActivity
import com.hdt.sleepsound.databinding.ActivitySplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity: BaseActivity<ActivitySplashBinding>() {

    override fun isDisplayCutout(): Boolean = true

    override fun inflateBinding(layoutInflater: LayoutInflater): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun updateUI(savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            delay(3000)
            navigateToNextScreen()
        }
    }

    private fun navigateToNextScreen() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}