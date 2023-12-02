package com.maktabah.maktabahyarsi.ui.home.category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.maktabah.maktabahyarsi.databinding.FragmentCategoryContentBinding
import com.maktabah.maktabahyarsi.ui.home.adapter.BookGridAdapter
import com.maktabah.maktabahyarsi.utils.safeNavigate
import com.maktabah.maktabahyarsi.wrapper.proceedWhen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryContentFragment : Fragment() {

    private var _binding: FragmentCategoryContentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CategoryContentViewModel by viewModels()
    private val navArgs: CategoryContentFragmentArgs by navArgs()
    private val bookGridAdapter: BookGridAdapter by lazy {
        BookGridAdapter {
            navigateToDetail(it.id)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryContentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        setRecyclerViewBook()
        toolBarAction()
    }

    private fun toolBarAction() = with(binding) {
        tvTitle.text = navArgs.name
        iconBack.setOnClickListener {
            navigateBack()
        }
    }

    private fun navigateBack() = findNavController().popBackStack()

    private fun navigateToDetail(id: String) = findNavController().safeNavigate(
        CategoryContentFragmentDirections.actionCategoryContentFragmentToDetailFragment(id)
    )

    private fun getData() = with(viewModel) {
        getBooksByCategory(navArgs.id)
    }

    private fun setRecyclerViewBook() {
        binding.listCategoryContent.rvCategoryContent.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = this@CategoryContentFragment.bookGridAdapter
        }
        setObserveDataBook()
    }

    private fun setObserveDataBook() = with(binding.listCategoryContent) {
        lifecycleScope.launch {
            repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                viewModel.bookResponse.collectLatest {
                    it.proceedWhen(
                        doOnSuccess = { result ->
                            rvCategoryContent.isVisible = true
                            shimmerCategoryContent.isVisible = false
                            result.payload?.let { payload ->
                                bookGridAdapter.setData(payload.data)
                            }
                        },
                        doOnLoading = {
                            rvCategoryContent.isVisible = false
                            shimmerCategoryContent.isVisible = true
                        },
                        doOnError = { err ->
                            rvCategoryContent.isVisible = false
                            shimmerCategoryContent.isVisible = true
                        },
                        doOnEmpty = {
                            rvCategoryContent.isVisible = false
                            shimmerCategoryContent.isVisible = true
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