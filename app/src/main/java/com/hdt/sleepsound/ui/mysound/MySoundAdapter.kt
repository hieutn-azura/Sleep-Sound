package com.hdt.sleepsound.ui.mysound

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MySoundAdapter(
    fragment: Fragment,
) : FragmentStateAdapter(fragment) {

    private val listFragment =
        listOf(RecordSaveFragment(), MixSaveFragment())

    override fun getItemCount() = listFragment.size

    override fun createFragment(position: Int): Fragment {
        return listFragment[position]
    }
}