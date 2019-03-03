package com.example.p2

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.p2.fragment.FavoritesFragment
import com.example.p2.fragment.HomeFragment

class PagerAdapter(private val context: Context, fm: FragmentManager): FragmentPagerAdapter(fm) {
    override fun getItem(p0: Int): Fragment {
        return if (p0 == 0) {
            HomeFragment(context)
        } else {
            FavoritesFragment(context)
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position == 0) {
            context.getString(R.string.TopTracks)
        } else {
            context.getString(R.string.playlist)
        }
    }
}