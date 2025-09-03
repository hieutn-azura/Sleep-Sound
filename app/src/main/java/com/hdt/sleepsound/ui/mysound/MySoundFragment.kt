package com.hdt.sleepsound.ui.mysound

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.hdt.sleepsound.R
import com.hdt.sleepsound.base.BaseFragment
import com.hdt.sleepsound.databinding.FragmentMySoundBinding
import com.hdt.sleepsound.utils.extensions.onBackPressed
import com.hdt.sleepsound.utils.extensions.popBackStack

class MySoundFragment : BaseFragment<FragmentMySoundBinding>() {

    private val navArg by navArgs<MySoundFragmentArgs>()
    private val page by lazy { navArg.page }

    private lateinit var adapter: MySoundAdapter

    override fun isDisplayCutout(): Boolean = true

    override fun inflateBinding(inflater: LayoutInflater): FragmentMySoundBinding {
        return FragmentMySoundBinding.inflate(inflater)
    }

    override fun updateUI(view: View, savedInstanceState: Bundle?) {
        adapter = MySoundAdapter(this)
        binding.vpMySound.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.vpMySound) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.record)
                else -> getString(R.string.mix_sound)
            }
        }.attach()

        binding.tabLayout.post {
            val tabStrip = binding.tabLayout.getChildAt(0) as ViewGroup
            for (i in 0 until tabStrip.childCount) {
                val tabView = tabStrip.getChildAt(i)
                val params = tabView.layoutParams as ViewGroup.MarginLayoutParams
                tabView.layoutParams = params
            }
        }

        binding.vpMySound.setCurrentItem(page, false)
    }

    override fun initListener() {
        super.initListener()

        binding.btnBack.setOnClickListener {
            popBackStack(R.id.homeFragment)
        }

        onBackPressed {
            popBackStack(R.id.homeFragment)
        }
    }
}