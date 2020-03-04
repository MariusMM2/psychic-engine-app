package com.marius.spendings.ui.home.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import com.marius.spendings.databinding.FragmentHomeBinding
import com.marius.spendings.models.BudgetItem
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

    private var budgetItemsAdapter: BudgetItemAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // set up data binding
        binding.lifecycleOwner = this
        binding.viewmodel = homeTabViewModel
        binding.executePendingBindings()

        homeTabViewModel.budgetItems.observe(viewLifecycleOwner, Observer<List<BudgetItem>> { budgetItems ->
            Log.d(TAG, budgetItems.size.toString())
            if (budgetItemsAdapter == null) {
                budgetItemsAdapter = BudgetItemAdapter()
                budgetItemsAdapter?.budgetItems = budgetItems
                budgetItemsAdapter?.notifyDataSetChanged()

                binding.budgetList.adapter = budgetItemsAdapter
            } else {
                val result: DiffUtil.DiffResult =
                    DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                        override fun getOldListSize(): Int {
                            return budgetItemsAdapter!!.budgetItems.size
                        }

                        override fun getNewListSize(): Int {
                            return budgetItems.size
                        }

                        override fun areItemsTheSame(
                            oldItemPosition: Int,
                            newItemPosition: Int
                        ): Boolean {
                            return budgetItemsAdapter!!.budgetItems[oldItemPosition].id ==
                                    budgetItems[newItemPosition].id
                        }

                        override fun areContentsTheSame(
                            oldItemPosition: Int,
                            newItemPosition: Int
                        ): Boolean {
                            val oldItem = budgetItemsAdapter!!.budgetItems[oldItemPosition]
                            val newItem = budgetItems[newItemPosition]
                            return oldItem == newItem
                        }
                    })

                budgetItemsAdapter!!.budgetItems = budgetItems
                result.dispatchUpdatesTo(budgetItemsAdapter!!)
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(): HomeTabFragment {
            return HomeTabFragment()
        }
    }
}