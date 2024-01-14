package com.maktabah.maktabahyarsi.ui.resultsearch.buku

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.databinding.FragmentBukuBinding
import com.maktabah.maktabahyarsi.ui.resultsearch.ResultSearchFragmentDirections
import com.maktabah.maktabahyarsi.ui.resultsearch.adapter.BookAdapter
import com.maktabah.maktabahyarsi.ui.resultsearch.adapter.WordAdapter
import com.maktabah.maktabahyarsi.ui.resultsearch.kata.KataViewModel
import com.maktabah.maktabahyarsi.utils.safeNavigate
import com.maktabah.maktabahyarsi.wrapper.proceedWhen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BukuFragment : Fragment() {

    private var _binding: FragmentBukuBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BukuViewModel by viewModels()
    private val bookAdapter: BookAdapter by lazy {
        BookAdapter(
            {
                findNavController().safeNavigate(
                    ResultSearchFragmentDirections.actionResultSearchFragmentToDetailFragment(
                        it.id
                    )
                )
            },
                arguments?.getString("QUERY") ?: ""
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBukuBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        setRecyclerViewWord()
    }

    private fun getData() = with(viewModel) {
        arguments?.let {
            search(it.getString("QUERY") ?: "")
        }
    }

    private fun setRecyclerViewWord() {
        binding.rvSearchBuku.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = this@BukuFragment.bookAdapter
        }
        setObserveDataBook()
    }

    private fun setObserveDataBook() = with(binding) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.search.collectLatest {
                    it.proceedWhen(
                        doOnSuccess = { result ->
                            rvSearchBuku.isVisible = true
                            imgNoContent.isVisible = false
                            pbLoading.isVisible = false
                            tvUps.isVisible = false
                            tvNoContent.isVisible = false
                            result.payload?.let { payload ->
                                bookAdapter.setData(payload.data)
                            }
                        },
                        doOnLoading = {
                            rvSearchBuku.isVisible = false
                            imgNoContent.isVisible = false
                            pbLoading.isVisible = true
                            tvUps.isVisible = false
                            tvNoContent.isVisible = false
                        },
                        doOnError = { err ->
                            rvSearchBuku.isVisible = false
                            imgNoContent.isVisible = true
                            pbLoading.isVisible = false
                            tvUps.isVisible = true
                            tvNoContent.isVisible = true
                            Toast.makeText(requireContext(), err.exception.toString(), Toast.LENGTH_SHORT).show()
                        },
                        doOnEmpty = {
                            rvSearchBuku.isVisible = false
                            imgNoContent.isVisible = true
                            pbLoading.isVisible = false
                            tvUps.isVisible = true
                            tvNoContent.isVisible = true
                            tvNoContent.text =
                                "Buku yang anda cari belum ada, Silahkan cari buku!"
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