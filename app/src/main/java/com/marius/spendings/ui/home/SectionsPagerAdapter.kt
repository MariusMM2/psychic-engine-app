package com.marius.spendings.ui.home

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.marius.spendings.R
import com.marius.spendings.ui.home.fragments.*

/**
 * A [FragmentStatePagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(context: Context, fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val tabTitles = context.resources.getStringArray(R.array.tab_titles)
    private val tabTitleOverview = context.resources.getString(R.string.tab_name_overview)
    private val tabTitleRecent = context.resources.getString(R.string.tab_name_recent)
    private val tabTitleMonthly = context.resources.getString(R.string.tab_name_monthly)
    private val tabTitleYearly = context.resources.getString(R.string.tab_name_yearly)

    /**
     * Instantiate a [HomeTabFragment] for the given page [position]
     *
     * @param position of the [Fragment] in the adapter
     * @return a [HomeTabFragment] at [position]
     */
    override fun getItem(position: Int): Fragment {
        return when (tabTitles[position]) {
            tabTitleYearly -> YearlyFragment.newInstance(tabTitles[position])
            tabTitleMonthly -> MonthlyFragment.newInstance(tabTitles[position])
            tabTitleRecent -> RecentFragment.newInstance(tabTitles[position])
            tabTitleOverview -> OverviewFragment.newInstance(tabTitles[position])
            else -> throw IllegalStateException("'${tabTitles[position]}' is not a valid HomeTabFragment identifier")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }

    override fun getCount(): Int {
        return tabTitles.size
    }
}