package com.marius.spendings.ui.home.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.marius.spendings.database.UserRepository
import com.marius.spendings.databinding.FragmentHomeBinding
import com.marius.spendings.models.User
import com.marius.spendings.ui.home.BudgetItemAdapter
import com.marius.spendings.viewmodels.HomeTabViewModel

/**
 * A pager fragment containing the budget overview for a specific criterion.
 */
class HomeTabFragment : Fragment() {
    @Suppress("PrivatePropertyName")
    private val TAG = "HomeTabFragment"

    private val homeTabViewModel: HomeTabViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!

    private var budgetItemsAdapter: BudgetItemAdapter?
        get() = binding.budgetList.adapter as BudgetItemAdapter?
        set(value) {
            if (binding.budgetList.adapter == null)
                binding.budgetList.adapter = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeTabViewModel.setUser(arguments?.getParcelable(ARG_CURRENT_USER) ?: User("", "", ""))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // set up data binding
        binding.lifecycleOwner = this
        binding.viewmodel = homeTabViewModel
        binding.executePendingBindings()

        homeTabViewModel.budgetItems.observe(viewLifecycleOwner, Observer { budgetItems ->
            Log.d(TAG, budgetItems.size.toString())
            if (budgetItemsAdapter == null) {
                budgetItemsAdapter = BudgetItemAdapter()
            }

            budgetItemsAdapter?.budgetItems = budgetItems
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * The fragment argument representing the logged in user of the application.
         */
        private const val ARG_CURRENT_USER = "current_user"

        /**
         * Returns a new instance of this fragment.
         */
        @JvmStatic
        fun newInstance(): HomeTabFragment {
            return HomeTabFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CURRENT_USER, UserRepository.currentUser)
                }
            }
        }
    }
}