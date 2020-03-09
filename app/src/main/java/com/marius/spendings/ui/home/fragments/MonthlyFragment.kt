package com.marius.spendings.ui.home.fragments

import androidx.fragment.app.viewModels
import com.marius.spendings.viewmodels.HomeTabViewModel
import com.marius.spendings.viewmodels.MonthlyTabViewModel

/**
 * A pager fragment containing the budget items of a specific month.
 */
class MonthlyFragment : HomeTabFragment() {

    // ViewModel of this fragment
    private val viewModel: MonthlyTabViewModel by viewModels()

    override fun getViewModel(): HomeTabViewModel {
        return viewModel
    }

    companion object {

        /**
         * Returns a new instance of this fragment.
         */
        @JvmStatic
        fun newInstance(name: String): MonthlyFragment {
            return MonthlyFragment().apply {
                arguments = getBundle(name)
            }
        }
    }
}