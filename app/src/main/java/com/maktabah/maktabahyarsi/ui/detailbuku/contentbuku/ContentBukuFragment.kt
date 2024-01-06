package com.maktabah.maktabahyarsi.ui.detailbuku.contentbuku

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.maktabah.maktabahyarsi.data.network.api.model.book.DataItemBookById
import com.maktabah.maktabahyarsi.databinding.FragmentContentBukuBinding
import com.maktabah.maktabahyarsi.ui.detailbuku.contentbuku.adapter.ContentAdapter
import com.maktabah.maktabahyarsi.ui.detailbuku.contentbuku.adapter.LoadingStateAdapter
import com.maktabah.maktabahyarsi.ui.profile.editprofile.EditProfileFragmentDirections
import com.maktabah.maktabahyarsi.utils.safeNavigate
import com.maktabah.maktabahyarsi.wrapper.proceedWhen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContentBukuFragment : Fragment() {

    private var _binding: FragmentContentBukuBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ContentBukuViewModel by viewModels()
    private val navArgs: ContentBukuFragmentArgs by navArgs()
    private val contentAdapter: ContentAdapter by lazy {
        ContentAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentBukuBinding.inflate(inflater, container, false)
        handleOnBackPressed()
        return binding.root
    }

    private fun handleOnBackPressed() {
        val callbacks: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.removeHighlight()
                    findNavController().popBackStack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callbacks)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolBarAction()
        getData()
        setRecyclerViewContent()
    }

    private fun toolBarAction() = with(binding) {
        iconBack.setOnClickListener {
            viewModel.removeHighlight()
            findNavController().popBackStack()
        }
    }

    private fun getData() = with(viewModel) {
        getContentDetail(navArgs.id, navArgs.page)
        getBooksById(navArgs.id)
        getHighlightText.observe(viewLifecycleOwner) {
            contentAdapter.setText(it.orEmpty())
        }
    }

    private fun setRecyclerViewContent() {
        binding.run {
            vpContent.apply {
                adapter =
                    this@ContentBukuFragment.contentAdapter.withLoadStateFooter(footer = LoadingStateAdapter { contentAdapter.retry() })
            }
            contentAdapter.addLoadStateListener {
                when (it.source.refresh) {
                    is LoadState.NotLoading -> {
                        vpContent.isVisible = true
                        errorMsg.isVisible = false
                        pbLoading.isVisible = false
                    }

                    is LoadState.Loading -> {
                        vpContent.isVisible = false
                        errorMsg.isVisible = false
                        pbLoading.isVisible = true
                    }

                    is LoadState.Error -> {
                        vpContent.isVisible = false
                        errorMsg.isVisible = true
                        pbLoading.isVisible = false
                        val errorState = it.source.refresh as LoadState.Error
                        Toast.makeText(
                            requireContext(),
                            errorState.error.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            setObserveDataContentDetail()
            setObserveDataBook()
        }
    }

    private fun setObserveDataContentDetail() = binding.run {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.contentDetailResponse.collectLatest {
                    it.proceedWhen(
                        doOnSuccess = { result ->
                            vpContent.isVisible = true
                            errorMsg.isVisible = false
                            pbLoading2.isVisible = false
                            result.payload?.let { data ->
                                contentAdapter.submitData(lifecycle, data)
                            }
                        },
                        doOnError = { e ->
                            vpContent.isVisible = false
                            errorMsg.isVisible = true
                            pbLoading2.isVisible = false
                            errorMsg.text = "Check ur network : ${e.message.orEmpty()}"
                        },
                        doOnLoading = {
                            vpContent.isVisible = false
                            errorMsg.isVisible = false
                            pbLoading2.isVisible = true
                        }
                    )
                }
            }
        }
    }

    private fun setObserveDataBook() = binding.run {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.bookResponse.collectLatest {
                    it.proceedWhen(
                        doOnSuccess = { result ->
                            errorMsg.isVisible = false
                            pbLoading.isVisible = false
                            oleh.isVisible = true
                            tvTitle.isVisible = true
                            pencipta.isVisible = true
                            result.payload?.let { payload ->
                                bindToViewBook(payload.data)
                            }
                        },
                        doOnError = { e ->
                            vpContent.isVisible = false
                            errorMsg.isVisible = true
                            pbLoading.isVisible = false
                            oleh.isVisible = false
                            tvTitle.isVisible = false
                            pencipta.isVisible = false
                            errorMsg.text = "Check ur network : ${e.message.orEmpty()}"
                        },
                        doOnLoading = {
                            vpContent.isVisible = false
                            errorMsg.isVisible = true
                            pbLoading.isVisible = true
                            oleh.isVisible = false
                            tvTitle.isVisible = false
                            pencipta.isVisible = false
                        }
                    )
                }
            }
        }
    }

    private fun bindToViewBook(data: DataItemBookById) {
        with(binding) {
            judulBuku.text = data.title
            pencipta.text = data.creator
            viewModel.getUserTokenPrefFlow.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    viewModel.addOrUpdateHistory(
                        data.id,
                        data.title,
                        data.description,
                        data.page,
                        data.creator,
                        data.imageUrl,
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