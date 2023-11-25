package com.maktabah.maktabahyarsi.ui.riwayat

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.databinding.FragmentRiwayatBinding
import com.maktabah.maktabahyarsi.ui.riwayat.adapter.RiwayatAdapter
import com.maktabah.maktabahyarsi.utils.safeNavigate
import com.maktabah.maktabahyarsi.wrapper.proceedWhen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RiwayatFragment : Fragment() {

    private var _binding: FragmentRiwayatBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RiwayatViewModel by viewModels()
    private val riwayatAdapter: RiwayatAdapter by lazy {
        RiwayatAdapter { data ->
            viewModel.addOrUpdateHistory(
                data.id,
                data.title,
                data.desc,
                data.page,
                data.creator,
                data.imageUrl,
            )
            findNavController().safeNavigate(
                RiwayatFragmentDirections.actionRiwayatFragmentToContentBukuFragment(
                    data.id
                )
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRiwayatBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        setRecyclerViewHistory()
    }

    private fun getData() = with(viewModel) {
        getHistoryBooks()
    }

    private fun setRecyclerViewHistory() {
        binding.listRiwayatBuku.rvRiwayatBuku.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@RiwayatFragment.riwayatAdapter
        }
        setObserverToken()
    }

    private fun setObserverToken() {
        viewModel.getUserTokenPrefFlow.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                setObserveDataHistory()
            } else {
                with(binding.listRiwayatBuku) {
                    rvRiwayatBuku.isVisible = false
                    pbLoading.isVisible = false
                    imgNoContent.setImageResource(R.drawable.alert_triangle)
                    tvNoContent.text =
                        "Anda masuk hanya sebagai tamu, login terlebih dahulu supaya kamu bisa memakai fitur ini!"
                    imgNoContent.isVisible = true
                    tvUps.isVisible = true
                    tvNoContent.isVisible = true
                }
            }
        }
    }

    private fun setObserveDataHistory() = with(binding.listRiwayatBuku) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.historyBookResponse.collectLatest {
                    it.proceedWhen(
                        doOnSuccess = { result ->
                            rvRiwayatBuku.isVisible = true
                            pbLoading.isVisible = false
                            imgNoContent.isVisible = false
                            tvUps.isVisible = false
                            tvNoContent.isVisible = false
                            result.payload?.let { payload ->
                                riwayatAdapter.setData(payload)
                            }
                        },
                        doOnLoading = {
                            rvRiwayatBuku.isVisible = false
                            pbLoading.isVisible = true
                            imgNoContent.isVisible = false
                            tvUps.isVisible = false
                            tvNoContent.isVisible = false
                        },
                        doOnError = { err ->
                            rvRiwayatBuku.isVisible = false
                            pbLoading.isVisible = true
                            imgNoContent.isVisible = false
                            tvUps.isVisible = false
                            tvNoContent.isVisible = false
                        },
                        doOnEmpty = { empty ->
                            rvRiwayatBuku.isVisible = false
                            pbLoading.isVisible = false
                            imgNoContent.isVisible = true
                            tvUps.isVisible = true
                            tvNoContent.isVisible = true
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