package com.maktabah.maktabahyarsi.ui.home

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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.databinding.FragmentHomeBinding
import com.maktabah.maktabahyarsi.ui.home.adapter.BookLinearAdapter
import com.maktabah.maktabahyarsi.ui.home.adapter.CategoryHomeAdapter
import com.maktabah.maktabahyarsi.ui.home.slider.ModelSlider
import com.maktabah.maktabahyarsi.ui.home.slider.SliderAdapter
import com.maktabah.maktabahyarsi.utils.safeNavigate
import com.maktabah.maktabahyarsi.wrapper.proceedWhen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private val categoryHomeAdapter: CategoryHomeAdapter by lazy {
        CategoryHomeAdapter(
            {
                if (it.subcategories.isNotEmpty()) {
                    navigateToCategory(it.id)
                }
            }, {
                navigateToCategory()
            }
        )
    }

    private val latestBookLinearAdapter: BookLinearAdapter by lazy {
        BookLinearAdapter()
    }

    private val recommendBookLinearAdapter: BookLinearAdapter by lazy {
        BookLinearAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        setUpSlider()
        setRecyclerViewCategory()
        setRecyclerViewLatestBook()
        setRecyclerViewRecommendBook()
        seeAllBookClickListener()
    }

    private fun seeAllBookClickListener() = with(binding) {
        chevronRightBukuTerbaru.setOnClickListener {
            navigateToAllBook("createdAt")
        }
        tvLihatSemuaBukuTerbaru.setOnClickListener {
            navigateToAllBook("createdAt")
        }
        chevronRightRekomendasi.setOnClickListener {
            navigateToAllBook("total")
        }
        tvLihatSemuaRekomendasi.setOnClickListener {
            navigateToAllBook("total")
        }
    }

    private fun navigateToCategory(id: String? = null) {
        findNavController().safeNavigate(
            HomeFragmentDirections.actionHomeFragmentToCategoryFragment(
                id
            )
        )
    }

    private fun navigateToAllBook(sort: String) {
        findNavController().safeNavigate(
            HomeFragmentDirections.actionHomeFragmentToLihatSemuaFragment(
                sort
            )
        )
    }

    private fun setUpSlider() = with(binding) {
        val arraySlider = arrayListOf(
            ModelSlider(R.drawable.img_example), ModelSlider(R.drawable.example)
        )

        val sliderAdapter = SliderAdapter(arraySlider)
        carouselRecyclerView.adapter = sliderAdapter

        val lm = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        carouselRecyclerView.layoutManager = lm
        carouselRecyclerView.adapter = sliderAdapter
    }

    private fun getData() = with(viewModel) {
        getAllCategory()
        getLatestBook()
        getRecommendedBook()
    }

    private fun setRecyclerViewCategory() {
        binding.listCategory.rvCategory.apply {
            layoutManager = GridLayoutManager(requireContext(), 4)
            adapter = this@HomeFragment.categoryHomeAdapter
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
                                categoryHomeAdapter.setData(payload.data)
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

    private fun setRecyclerViewLatestBook() {
        binding.listBukuTerbaru.rvBukuTerbaru.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = this@HomeFragment.latestBookLinearAdapter
        }
        setObserveDataLatestBook()
    }

    private fun setObserveDataLatestBook() = with(binding.listBukuTerbaru) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.latestBookResponse.collectLatest {
                    it.proceedWhen(
                        doOnSuccess = { result ->
                            rvBukuTerbaru.isVisible = true
                            shimmerBukuTerbaru.isVisible = false
                            result.payload?.let { payload ->
                                latestBookLinearAdapter.setData(payload.data)
                            }
                        },
                        doOnLoading = {
                            rvBukuTerbaru.isVisible = false
                            shimmerBukuTerbaru.isVisible = true
                        },
                        doOnError = { err ->
                            rvBukuTerbaru.isVisible = false
                            shimmerBukuTerbaru.isVisible = true
                        }
                    )
                }
            }
        }
    }

    private fun setRecyclerViewRecommendBook() {
        binding.listBukuRekomendasi.rvBukuRekomendasi.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = this@HomeFragment.recommendBookLinearAdapter
        }
        setObserveDataRecommendBook()
    }

    private fun setObserveDataRecommendBook() = with(binding.listBukuRekomendasi) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.recommendBookResponse.collectLatest {
                    it.proceedWhen(
                        doOnSuccess = { result ->
                            rvBukuRekomendasi.isVisible = true
                            shimmerBukuRekomendasi.isVisible = false
                            result.payload?.let { payload ->
                                recommendBookLinearAdapter.setData(payload.data)
                            }
                        },
                        doOnLoading = {
                            rvBukuRekomendasi.isVisible = false
                            shimmerBukuRekomendasi.isVisible = true
                        },
                        doOnError = { err ->
                            rvBukuRekomendasi.isVisible = false
                            shimmerBukuRekomendasi.isVisible = true
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