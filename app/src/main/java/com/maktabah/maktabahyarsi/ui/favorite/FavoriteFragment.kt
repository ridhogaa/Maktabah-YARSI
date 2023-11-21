package com.maktabah.maktabahyarsi.ui.favorite

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
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.databinding.FragmentFavoriteBinding
import com.maktabah.maktabahyarsi.ui.favorite.adapter.FavoriteAdapter
import com.maktabah.maktabahyarsi.utils.safeNavigate
import com.maktabah.maktabahyarsi.wrapper.proceedWhen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteViewModel by viewModels()
    private val favoriteAdapter: FavoriteAdapter by lazy {
        FavoriteAdapter(
            {
                findNavController().safeNavigate(
                    FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(
                        it.id
                    )
                )
            }, { data ->
                viewModel.removeFavorite(
                    data.id,
                    data.title,
                    data.desc,
                    data.page,
                    data.imageUrl,
                    data.isFavorite
                )
                getData()
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        setRecyclerViewFavorite()
    }

    private fun getData() = with(viewModel) {
        getFavoriteBooks()
    }

    private fun setRecyclerViewFavorite() {
        binding.listFavoriteBuku.rvFavoriteBuku.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = this@FavoriteFragment.favoriteAdapter
        }
        setObserverToken()
    }

    private fun setObserverToken() {
        viewModel.getUserTokenPrefFlow.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                setObserveDataFavorite()
            } else {
                with(binding.listFavoriteBuku) {
                    rvFavoriteBuku.isVisible = false
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

    private fun setObserveDataFavorite() = with(binding.listFavoriteBuku) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favBookResponse.collectLatest {
                    it.proceedWhen(
                        doOnSuccess = { result ->
                            rvFavoriteBuku.isVisible = true
                            pbLoading.isVisible = false
                            imgNoContent.isVisible = false
                            tvUps.isVisible = false
                            tvNoContent.isVisible = false
                            result.payload?.let { payload ->
                                favoriteAdapter.setData(payload)
                            }
                        },
                        doOnLoading = {
                            rvFavoriteBuku.isVisible = false
                            pbLoading.isVisible = true
                            imgNoContent.isVisible = false
                            tvUps.isVisible = false
                            tvNoContent.isVisible = false
                        },
                        doOnError = { err ->
                            rvFavoriteBuku.isVisible = false
                            pbLoading.isVisible = true
                            imgNoContent.isVisible = false
                            tvUps.isVisible = false
                            tvNoContent.isVisible = false
                        },
                        doOnEmpty = { empty ->
                            rvFavoriteBuku.isVisible = false
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