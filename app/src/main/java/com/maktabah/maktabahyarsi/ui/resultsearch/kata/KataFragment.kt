package com.maktabah.maktabahyarsi.ui.resultsearch.kata

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.databinding.FragmentKataBinding
import com.maktabah.maktabahyarsi.databinding.FragmentResultSearchBinding
import com.maktabah.maktabahyarsi.ui.resultsearch.ResultSearchFragment
import com.maktabah.maktabahyarsi.ui.resultsearch.ResultSearchFragmentDirections
import com.maktabah.maktabahyarsi.ui.resultsearch.ResultSearchViewModel
import com.maktabah.maktabahyarsi.ui.resultsearch.adapter.SectionsPagerAdapter
import com.maktabah.maktabahyarsi.ui.resultsearch.adapter.WordAdapter
import com.maktabah.maktabahyarsi.utils.safeNavigate
import com.maktabah.maktabahyarsi.wrapper.proceedWhen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class KataFragment : Fragment() {

    private var _binding: FragmentKataBinding? = null
    private val binding get() = _binding!!
    private val viewModel: KataViewModel by viewModels()
    private val wordAdapter: WordAdapter by lazy {
        WordAdapter(
            {
                findNavController().safeNavigate(
                    ResultSearchFragmentDirections.actionResultSearchFragmentToContentBukuFragment(
                        it._source.listcontent
                    )
                )
            },
            arguments?.getString("QUERY") ?: ""
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKataBinding.inflate(layoutInflater, container, false)
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
        binding.rvSearchKata.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@KataFragment.wordAdapter
        }
        setObserveDataWord()
    }

    private fun setObserveDataWord() = with(binding) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.search.collectLatest {
                    it.proceedWhen(
                        doOnSuccess = { result ->
                            rvSearchKata.isVisible = true
                            imgNoContent.isVisible = false
                            pbLoading.isVisible = false
                            tvUps.isVisible = false
                            tvNoContent.isVisible = false
                            result.payload?.let { payload ->
                                wordAdapter.setData(payload.data)
                            }
                        },
                        doOnLoading = {
                            rvSearchKata.isVisible = false
                            imgNoContent.isVisible = false
                            pbLoading.isVisible = true
                            tvUps.isVisible = false
                            tvNoContent.isVisible = false
                        },
                        doOnError = { err ->
                            rvSearchKata.isVisible = false
                            imgNoContent.isVisible = true
                            pbLoading.isVisible = false
                            tvUps.isVisible = true
                            tvNoContent.isVisible = true
                        },
                        doOnEmpty = {
                            rvSearchKata.isVisible = false
                            imgNoContent.isVisible = true
                            pbLoading.isVisible = false
                            tvUps.isVisible = true
                            tvNoContent.isVisible = true
                            tvNoContent.text =
                                "Kata yang anda cari belum ada, Silahkan cari kata!"
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