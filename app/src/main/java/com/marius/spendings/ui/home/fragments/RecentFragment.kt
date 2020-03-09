package com.marius.spendings.ui.home.fragments

import androidx.fragment.app.viewModels
import com.marius.spendings.viewmodels.HomeTabViewModel
import com.marius.spendings.viewmodels.RecentTabViewModel

/**
 * A pager fragment containing the budget items recently added.
 */
class RecentFragment : HomeTabFragment() {

    // ViewModel of this fragment
    private val viewModel: RecentTabViewModel by viewModels()

    override fun getViewModel(): HomeTabViewModel {
        return viewModel
    }

    companion object {

        /**
         * Returns a new instance of this fragment.
         */
        @JvmStatic
        fun newInstance(name: String): RecentFragment {
            return RecentFragment().apply {
                arguments = getBundle(name)
            }
        }
    }
}