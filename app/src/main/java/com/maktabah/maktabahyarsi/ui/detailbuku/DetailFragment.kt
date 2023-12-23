package com.maktabah.maktabahyarsi.ui.detailbuku

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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.data.network.api.model.book.DataItemBook
import com.maktabah.maktabahyarsi.data.network.api.model.book.DataItemBookById
import com.maktabah.maktabahyarsi.databinding.FragmentDetailBinding
import com.maktabah.maktabahyarsi.utils.loadImage
import com.maktabah.maktabahyarsi.utils.safeNavigate
import com.maktabah.maktabahyarsi.utils.showSnackBar
import com.maktabah.maktabahyarsi.wrapper.proceedWhen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()
    private val navArgs: DetailFragmentArgs by navArgs()
    private var isFavorite by Delegates.notNull<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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
        isFavorite(navArgs.id)
    }

    private fun setObserveDataBook() = with(binding) {
        lifecycleScope.launch {
            repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                viewModel.bookResponse.collectLatest {
                    it.proceedWhen(doOnSuccess = { result ->
                        listDetailBuku.contentDetailBuku.contentDetailBuku.isVisible = true
                        listDetailBuku.shimmerContentDetailBukuLayout.isVisible = false
                        btnFavorite.visibility = View.VISIBLE
                        btnMulaiMembaca.visibility = View.VISIBLE
                        starFavorite.visibility = View.VISIBLE
                        result.payload?.let { payload ->
                            bindToViewBook(payload.data)
                            selectFavorite(payload.data)
                        }
                    }, doOnLoading = {
                        listDetailBuku.contentDetailBuku.contentDetailBuku.isVisible = false
                        listDetailBuku.shimmerContentDetailBukuLayout.isVisible = true
                        btnFavorite.visibility = View.INVISIBLE
                        btnMulaiMembaca.visibility = View.INVISIBLE
                        starFavorite.visibility = View.INVISIBLE
                    }, doOnError = { err ->
                        listDetailBuku.contentDetailBuku.contentDetailBuku.isVisible = false
                        listDetailBuku.shimmerContentDetailBukuLayout.isVisible = true
                        btnFavorite.visibility = View.INVISIBLE
                        btnMulaiMembaca.visibility = View.INVISIBLE
                        starFavorite.visibility = View.INVISIBLE
                    })
                }
            }
        }
        viewModel.isFavorite.observe(viewLifecycleOwner) {
            if (it == true) {
                starFavorite.setImageResource(R.drawable.favoritebook_gold)
            } else {
                starFavorite.setImageResource(R.drawable.favoritebook_green)
            }
            isFavorite = it
        }
    }

    private fun bindToViewBook(data: DataItemBookById) =
        with(binding.listDetailBuku.contentDetailBuku) {
            coverBuku.loadImage(coverBuku.context, data.imageUrl)
            judulBuku.text = data.title
            tvPencipta.text = data.creator
            tvJumlahBukuTerbaca.text = data.total.toString()
            tvJumlahHalaman.text = data.page.toString()
            tvTahunRilis.text = data.publisher
            tvDescriptionBuku.text = data.description
        }

    private fun selectFavorite(data: DataItemBookById) = with(binding) {
        viewModel.getUserTokenPrefFlow.observe(viewLifecycleOwner) { token ->
            btnFavorite.setOnClickListener {
                if (token.isNotEmpty()) {
                    if (!isFavorite) {
                        viewModel.addFavorite(
                            data.id, data.title, data.description, data.page, data.imageUrl, true
                        )
                        starFavorite.setImageResource(R.drawable.favoritebook_gold)
                        isFavorite = true
                        showSnackBar(requireView(), "Berhasil menambahkan ke favorite")
                    } else {
                        viewModel.removeFavorite(
                            data.id, data.title, data.description, data.page, data.imageUrl, true
                        )
                        starFavorite.setImageResource(R.drawable.favoritebook_green)
                        isFavorite = false
                        showSnackBar(requireView(), "Berhasil menghapus dari favorite")
                    }
                } else {
                    it.findNavController()
                        .safeNavigate(DetailFragmentDirections.actionDetailFragmentToGuestDialogFragment())
                }
            }
            btnMulaiMembaca.setOnClickListener {
                if (token.isNotEmpty()) {
                    viewModel.addOrUpdateHistory(
                        data.id,
                        data.title,
                        data.description,
                        data.page,
                        data.creator,
                        data.imageUrl,
                    )
                    navigateToContentBook(data.id)
                } else {
                    navigateToContentBook(data.id)
                }
            }
        }
    }

    private fun navigateToContentBook(id: String) {
        viewModel.updateTotalReadingBook(id)
        findNavController().safeNavigate(
            DetailFragmentDirections.actionDetailFragmentToContentBukuFragment(
                id
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}