package com.maktabah.maktabahyarsi.ui.detail_buku

import android.content.Context
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
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
import coil.load
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.data.network.api.model.book.DataItemBook
import com.maktabah.maktabahyarsi.databinding.FragmentDetailBinding
import com.maktabah.maktabahyarsi.ui.home.lihatsemua.LihatSemuaFragmentArgs
import com.maktabah.maktabahyarsi.wrapper.proceedWhen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()
    private val navArgs: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        setObserveDataBook()
        toolBarAction()
    }

    private fun toolBarAction() = with(binding) {
        iconBack.setOnClickListener {
            navigateBack()
        }
    }

    private fun navigateBack() = findNavController().popBackStack()

    private fun getData() = with(viewModel) {
        getBooksById(navArgs.id)
    }

    private fun setObserveDataBook() = with(binding) {
        lifecycleScope.launch {
            repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                viewModel.bookResponse.collectLatest {
                    it.proceedWhen(
                        doOnSuccess = { result ->
                            listDetailBuku.contentDetailBuku.contentDetailBuku.isVisible = true
                            listDetailBuku.shimmerContentDetailBukuLayout.isVisible = false
                            result.payload?.let { payload ->
                                bindToViewBook(payload.data[0])
                            }
                            btnFavorite.visibility = View.VISIBLE
                            btnMulaiMembaca.visibility = View.VISIBLE
                            starFavorite.visibility = View.VISIBLE
                        },
                        doOnLoading = {
                            listDetailBuku.contentDetailBuku.contentDetailBuku.isVisible = false
                            listDetailBuku.shimmerContentDetailBukuLayout.isVisible = true
                            btnFavorite.visibility = View.INVISIBLE
                            btnMulaiMembaca.visibility = View.INVISIBLE
                            starFavorite.visibility = View.INVISIBLE
                        },
                        doOnError = { err ->
                            listDetailBuku.contentDetailBuku.contentDetailBuku.isVisible = false
                            listDetailBuku.shimmerContentDetailBukuLayout.isVisible = true
                            btnFavorite.visibility = View.INVISIBLE
                            btnMulaiMembaca.visibility = View.INVISIBLE
                            starFavorite.visibility = View.INVISIBLE
                        }
                    )
                }
            }
        }
    }

    private fun bindToViewBook(data: DataItemBook) =
        with(binding.listDetailBuku.contentDetailBuku) {
            coverBuku.load(data.imageUrl)
            judulBuku.text = data.title
            tvPencipta.text = data.creator
            tvJumlahBukuTerbaca.text = data.total.toString()
            tvJumlahHalaman.text = data.page.toString()
            tvTahunRilis.text = data.total.toString()
            tvDescriptionBuku.text = data.description
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}