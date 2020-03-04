package com.marius.spendings.ui.home

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.marius.spendings.R
import com.marius.spendings.ui.home.fragments.HomeTabFragment

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(context: Context, fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val tabTitles = context.resources.getStringArray(R.array.tab_titles)

    /**
     * Instantiate the [Fragment] for the given page [position]
     *
     * @param position of the [Fragment] in the [FragmentPagerAdapter]
     * @return a [HomeTabFragment] at [position]
     */
    override fun getItem(position: Int): Fragment {
        return HomeTabFragment.newInstance()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }

    override fun getCount(): Int {
        return tabTitles.size
    }
}