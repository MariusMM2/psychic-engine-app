package com.marius.spendings.ui.home.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentPagerAdapter
import com.marius.spendings.databinding.ActivityHomeBinding
import com.marius.spendings.ui.home.SectionsPagerAdapter
import com.marius.spendings.ui.home.fragments.HomeTabFragment

/**
 * The main Activity of the app, presenting the different budget overviews
 * in their own pager tab.
 *
 * @see HomeTabFragment the fragment used in each tab
 * @see SectionsPagerAdapter the [FragmentPagerAdapter] used to organise the fragments
 */
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        binding.viewPager.adapter = SectionsPagerAdapter(this, supportFragmentManager)

        binding.tabs.setupWithViewPager(binding.viewPager)

        setContentView(binding.root)
    }
}