package com.example.casaroom.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.casaroom.fragment.AslListBlankFragment
import com.example.casaroom.roomDB.assortiment.IsFolderDB

class TabAdapter(fm: FragmentActivity, private val tab: List<IsFolderDB>): FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return tab.size
    }

    override fun createFragment(position: Int): Fragment {
        val isFolder = tab[position].IDAsl
        return AslListBlankFragment.newInstance(isFolder)
    }

}