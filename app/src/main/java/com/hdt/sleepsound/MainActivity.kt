package com.hdt.sleepsound

import android.os.Bundle
import android.view.LayoutInflater
import com.hdt.sleepsound.base.BaseActivity
import com.hdt.sleepsound.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun isDisplayCutout(): Boolean = true

    override fun inflateBinding(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun updateUI(savedInstanceState: Bundle?) {

    }
}