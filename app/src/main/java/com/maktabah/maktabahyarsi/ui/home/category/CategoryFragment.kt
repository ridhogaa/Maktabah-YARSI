package com.maktabah.maktabahyarsi.ui.home.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.maktabah.maktabahyarsi.databinding.FragmentCategoryBinding
import com.maktabah.maktabahyarsi.ui.home.adapter.CategoryAdapter
import com.maktabah.maktabahyarsi.ui.home.adapter.SubCategoryAdapter
import com.maktabah.maktabahyarsi.utils.safeNavigate
import com.maktabah.maktabahyarsi.wrapper.proceedWhen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CategoryViewModel by viewModels()
    private val navArgs: CategoryFragmentArgs by navArgs()
    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter {
            if (it.subcategories.isNotEmpty()) {
                navigateSelf(it.id)
            } else {
                navigateToContentCategory(it.id, it.name)
            }
        }
    }
    private val subCategoryAdapter: SubCategoryAdapter by lazy {
        SubCategoryAdapter {
            navigateToContentCategory(it.id, it.name)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        iconBackClick()
    }

    private fun iconBackClick() = with(binding) {
        iconBack.setOnClickListener {
            navigateBack()
        }
    }

    private fun navigateSelf(id: String? = null) {
        findNavController().safeNavigate(
            CategoryFragmentDirections.actionCategoryFragmentSelf(
                id
            )
        )
    }

    private fun navigateToContentCategory(id: String, name: String) {
        findNavController().safeNavigate(
            CategoryFragmentDirections.actionCategoryFragmentToCategoryContentFragment(
                id, name
            )
        )
    }

    private fun navigateBack() = findNavController().popBackStack()

    private fun getData() = with(viewModel) {
        if (navArgs.id != null) {
            getSubCategoryByIdCategory(navArgs.id!!)
            setRecyclerViewSubCategory()
        } else {
            getAllCategory()
            setRecyclerViewCategory()
        }
    }

    private fun setRecyclerViewCategory() {
        binding.listCategory.rvCategory.apply {
            layoutManager = GridLayoutManager(requireContext(), 4)
            adapter = this@CategoryFragment.categoryAdapter
        }
        setObserveDataCategory()
    }

    private fun setObserveDataCategory() = with(binding.listCategory) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categoryResponse.collectLatest {
                    it.proceedWhen(
                        doOnSuccess = { result ->
                            rvCategory.isVisible = true
                            shimmerCategory.isVisible = false
                            result.payload?.let { payload ->
                                categoryAdapter.setData(payload.data)
                            }
                        },
                        doOnLoading = {
                            rvCategory.isVisible = false
                            shimmerCategory.isVisible = true
                        },
                        doOnError = { err ->
                            rvCategory.isVisible = false
                            shimmerCategory.isVisible = true
                        }
                    )
                }
            }
        }
    }

    private fun setRecyclerViewSubCategory() {
        binding.listCategory.rvCategory.apply {
            layoutManager = GridLayoutManager(requireContext(), 4)
            adapter = this@CategoryFragment.subCategoryAdapter
        }
        setObserveDataSubCategory()
    }

    private fun setObserveDataSubCategory() = with(binding.listCategory) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.subCategoryResponse.collectLatest {
                    it.proceedWhen(
                        doOnSuccess = { result ->
                            rvCategory.isVisible = true
                            shimmerCategory.isVisible = false
                            result.payload?.let { payload ->
                                subCategoryAdapter.setData(payload.data)
                            }
                        },
                        doOnLoading = {
                            rvCategory.isVisible = false
                            shimmerCategory.isVisible = true
                        },
                        doOnError = { err ->
                            rvCategory.isVisible = false
                            shimmerCategory.isVisible = true
                        }
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}