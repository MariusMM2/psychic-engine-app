package com.marius.spendings.ui.home.activities

import android.animation.LayoutTransition
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.marius.spendings.R
import com.marius.spendings.databinding.ActivityHomeBinding
import com.marius.spendings.ui.home.SectionsPagerAdapter
import com.marius.spendings.ui.home.fragments.HomeTabFragment
import com.marius.spendings.utils.ZoomOutPageTransformer
import com.marius.spendings.viewmodels.HomeActivityViewModel

/**
 * The main Activity of the app, presenting the different budget displays
 * in their own pager tab.
 *
 * @see HomeTabFragment the fragment used in each tab
 * @see SectionsPagerAdapter the [FragmentPagerAdapter] used to display the fragments
 */
class HomeActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    @Suppress("PrivatePropertyName")
    private val TAG = "HomeActivity"

    // String list of all tab names
    private val tabs by lazy { resources.getStringArray(R.array.tab_titles) }

    // ViewModel of this activity
    private val homeActivityViewModel: HomeActivityViewModel by viewModels()

    // ViewBinding
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater).apply {
            setContentView(root)
            // set up data binding
            lifecycleOwner = this@HomeActivity
            viewmodel = homeActivityViewModel
            executePendingBindings()

            // Enable transition animation for wrapping FrameLayouts
            balanceTotalFramePlaceholder.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
            balanceTotalFrameBg.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
            balanceTotalFrame.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

            viewPager.addOnPageChangeListener(this@HomeActivity)
            viewPager.adapter = SectionsPagerAdapter(this@HomeActivity, supportFragmentManager)
            viewPager.setPageTransformer(true, ZoomOutPageTransformer())
            tabs.setupWithViewPager(viewPager)
        }

        onPageSelected(0)
    }

    // Informs the ViewModel of page changes
    override fun onPageSelected(position: Int) {
        Log.d(TAG, "onPageSelected: $position")
        homeActivityViewModel.setItemsSource(tabs[position])
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }
}