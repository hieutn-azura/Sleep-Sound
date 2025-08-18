package com.hdt.sleepsound.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.hdt.sleepsound.base.BaseFragment
import com.hdt.sleepsound.databinding.FragmentHomeBinding

class HomeFragment: BaseFragment<FragmentHomeBinding>() {

    override fun isDisplayCutout(): Boolean = true

    override fun inflateBinding(inflater: LayoutInflater): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater)
    }

    override fun updateUI(view: View, savedInstanceState: Bundle?) {

    }
}