package com.hdt.sleepsound.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding
import com.hdt.sleepsound.utils.PreferenceHelper
import com.hdt.sleepsound.utils.extensions.hideSystemBar
import com.hdt.sleepsound.utils.extensions.setFullScreen
import org.koin.android.ext.android.inject

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    protected val preferenceHelper: PreferenceHelper by inject()
    protected lateinit var binding: VB
        private set

    protected abstract fun inflateBinding(layoutInflater: LayoutInflater): VB
    abstract fun updateUI(savedInstanceState: Bundle?)
    open fun isDisplayCutout(): Boolean = false

    open fun loadAd() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = inflateBinding(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val insetTypes =
                if (!isDisplayCutout()) WindowInsetsCompat.Type.displayCutout() or WindowInsetsCompat.Type.systemBars()
                else WindowInsetsCompat.Type.systemBars()
            val systemBars = insets.getInsets(insetTypes)
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        window.setFullScreen()
        window.hideSystemBar()
        updateUI(savedInstanceState)
        loadAd()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        window.hideSystemBar()
    }
}
