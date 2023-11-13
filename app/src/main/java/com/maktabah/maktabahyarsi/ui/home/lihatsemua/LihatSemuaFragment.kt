package com.maktabah.maktabahyarsi.ui.home.lihatsemua

import android.os.Bundle
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
import com.maktabah.maktabahyarsi.databinding.FragmentLihatSemuaBinding
import com.maktabah.maktabahyarsi.ui.home.lihatsemua.adapter.BookGridAdapter
import com.maktabah.maktabahyarsi.wrapper.proceedWhen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LihatSemuaFragment : Fragment() {

    private var _binding: FragmentLihatSemuaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LihatSemuaViewModel by viewModels()
    private val navArgs: LihatSemuaFragmentArgs by navArgs()
    private val bookGridAdapter: BookGridAdapter by lazy {
        BookGridAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLihatSemuaBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        setRecyclerViewBook()
        iconBackClick()
    }

    private fun iconBackClick() = with(binding) {
        icBack.setOnClickListener {
            navigateBack()
        }
    }

    private fun navigateBack() = findNavController().popBackStack()

    private fun getData() = with(viewModel) {
        getBook(navArgs.sort)
    }

    private fun setRecyclerViewBook() {
        binding.listLihatSemua.rvBuku.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = this@LihatSemuaFragment.bookGridAdapter
        }
        setObserveDataBook()
    }

    private fun setObserveDataBook() = with(binding.listLihatSemua) {
        lifecycleScope.launch {
            repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                viewModel.bookResponse.collectLatest {
                    it.proceedWhen(
                        doOnSuccess = { result ->
                            rvBuku.isVisible = true
                            shimmerBuku.isVisible = false
                            result.payload?.let { payload ->
                                bookGridAdapter.setData(payload.data)
                            }
                        }, doOnLoading = {
                            rvBuku.isVisible = false
                            shimmerBuku.isVisible = true
                        }, doOnError = { err ->
                            rvBuku.isVisible = false
                            shimmerBuku.isVisible = true
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