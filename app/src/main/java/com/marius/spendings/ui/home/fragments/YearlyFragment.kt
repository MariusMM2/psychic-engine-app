package com.marius.spendings.ui.home.fragments

import androidx.fragment.app.viewModels
import com.marius.spendings.viewmodels.HomeTabViewModel
import com.marius.spendings.viewmodels.YearlyTabViewModel

/**
 * A pager fragment containing the budget items of a specific year.
 */
class YearlyFragment : HomeTabFragment() {

    // ViewModel of this fragment
    private val _viewModel: YearlyTabViewModel by viewModels()

    override fun getViewModel(): HomeTabViewModel {
        return _viewModel
    }

    companion object {

        /**
         * Returns a new instance of this fragment.
         */
        @JvmStatic
        fun newInstance(name: String): YearlyFragment {
            return YearlyFragment().apply {
                arguments = getBundle(name)
            }
        }
    }
}