package com.marius.spendings.ui.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.marius.spendings.database.UserRepository
import com.marius.spendings.databinding.FragmentHomeBinding
import com.marius.spendings.models.User
import com.marius.spendings.ui.home.BudgetItemAdapter
import com.marius.spendings.viewmodels.HomeActivityViewModel
import com.marius.spendings.viewmodels.HomeTabViewModel

/**
 * An abstract pager fragment containing the budget overview of a specific criterion.
 */
abstract class HomeTabFragment : Fragment() {

    @Suppress("PrivatePropertyName", "unused")
    private val TAG = "HomeTabFragment"

    /**
     * Returns the ViewModel of this fragment.
     * Meant to be overridden by subclasses.
     *
     * @return ViewModel for the fragment
     */
    protected abstract fun getViewModel(): HomeTabViewModel

    // ViewModel to be used by this fragment.
    private val homeTabViewModel: HomeTabViewModel by lazy {
        getViewModel()
    }

    // ViewModel of the parent activity
    private val homeActivityViewModel: HomeActivityViewModel by activityViewModels()

    // ViewBinding
    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!

    // RecyclerView's adapter, wrapped as BudgetItemAdapter
    private var budgetItemsAdapter: BudgetItemAdapter?
        get() = binding.budgetList.adapter as BudgetItemAdapter?
        set(value) {
            if (binding.budgetList.adapter == null)
                binding.budgetList.adapter = value
        }

    // Id of the fragment, used by the activity ViewModel
    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getString(ARG_ID) ?: ""
        homeTabViewModel.setUser(arguments?.getParcelable(ARG_CURRENT_USER) ?: User("", "", ""))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.run {
            // set up data binding
            lifecycleOwner = this@HomeTabFragment
            viewmodel = homeTabViewModel
            executePendingBindings()
        }

        homeTabViewModel.budgetItems.observe(viewLifecycleOwner, Observer { budgetItems ->
            if (budgetItemsAdapter == null) {
                // Instantiate the adapter
                budgetItemsAdapter = BudgetItemAdapter()
            }

            // Add the list to the adapter
            budgetItemsAdapter?.budgetItems = budgetItems

            // Inform the activity ViewModel of the new list
            homeActivityViewModel.setItems(budgetItems, id)
        })
        homeTabViewModel.isLoading.observe(viewLifecycleOwner, Observer { loading ->
            // Inform the activity ViewModel of the new loading state
            homeActivityViewModel.setLoading(loading, id)
        })

        homeActivityViewModel.source.observe(viewLifecycleOwner, Observer { source ->
            // The ViewModel's source had changed, check if it refers to this fragment
            if (source == id) {
                // Inform the ViewModel of this fragment's state
                homeActivityViewModel.setItems(
                    homeTabViewModel.budgetItems.value ?: ArrayList(),
                    id
                )
                homeActivityViewModel.setLoading(homeTabViewModel.isLoading.value ?: true, id)
            }
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
         * The fragment argument representing the title of the fragment.
         */
        private const val ARG_ID = "title"

        /**
         * Get a bundle containing the instance arguments.
         *
         * @param name the title of the fragment.
         */
        @JvmStatic
        protected fun getBundle(name: String) = Bundle().apply {
            putParcelable(ARG_CURRENT_USER, UserRepository.currentUser)
            putString(ARG_ID, name)
        }
    }
}