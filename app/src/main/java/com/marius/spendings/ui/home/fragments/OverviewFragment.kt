package com.marius.spendings.ui.home.fragments

import androidx.fragment.app.viewModels
import com.marius.spendings.viewmodels.HomeTabViewModel
import com.marius.spendings.viewmodels.OverviewTabViewModel

/**
 * A pager fragment containing the budget items of a general overview.
 */
class OverviewFragment : HomeTabFragment() {

    // ViewModel of this fragment
    private val viewModel: OverviewTabViewModel by viewModels()

    override fun getViewModel(): HomeTabViewModel {
        return viewModel
    }

    companion object {

        /**
         * Returns a new instance of this fragment.
         */
        @JvmStatic
        fun newInstance(name: String): OverviewFragment {
            return OverviewFragment().apply {
                arguments = getBundle(name)
            }
        }
    }
}