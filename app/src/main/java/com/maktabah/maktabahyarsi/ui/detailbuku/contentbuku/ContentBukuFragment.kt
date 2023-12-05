package com.maktabah.maktabahyarsi.ui.detailbuku.contentbuku

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.sidesheet.SideSheetBehavior
import com.google.android.material.sidesheet.SideSheetCallback
import com.google.android.material.sidesheet.SideSheetDialog
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.databinding.DaftarIsiSideSheetDialogBinding
import com.maktabah.maktabahyarsi.databinding.FragmentContentBukuBinding
import com.maktabah.maktabahyarsi.databinding.FragmentDetailBinding
import com.maktabah.maktabahyarsi.ui.detailbuku.DetailFragmentArgs
import com.maktabah.maktabahyarsi.ui.detailbuku.DetailViewModel
import com.maktabah.maktabahyarsi.ui.detailbuku.contentbuku.adapter.ContentAdapter
import com.maktabah.maktabahyarsi.ui.detailbuku.contentbuku.adapter.ContentIsiBukuAdapter
import com.maktabah.maktabahyarsi.wrapper.proceedWhen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class ContentBukuFragment : Fragment() {

    private var _binding: FragmentContentBukuBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ContentBukuViewModel by viewModels()
    private val navArgs: ContentBukuFragmentArgs by navArgs()
    private val contentAdapter: ContentAdapter by lazy {
        ContentAdapter()
    }
    private lateinit var contentViewModel: ContentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentBukuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        showSideSheet()
        getIsiContent()
    }

    private fun getData() = with(viewModel) {
        getContentsBook(navArgs.id)
    }

    private fun setObserveDataContent() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.contentResponse.collectLatest {
                    it.proceedWhen(
                        doOnSuccess = { result ->
                            result.payload?.let { payload ->
                                contentAdapter.setData(payload.data.sub)
                            }
                        },
                        doOnLoading = {
                        },
                        doOnError = { err ->
                        }
                    )
                }
            }
        }
    }

    private fun showSideSheet() = with(binding) {
        iconNav.setOnClickListener {
            val sideSheetDialog = SideSheetDialog(requireContext())

            sideSheetDialog.behavior.addCallback(object : SideSheetCallback() {
                override fun onStateChanged(sheet: View, newState: Int) {
                    if (newState == SideSheetBehavior.STATE_DRAGGING) {
                        sideSheetDialog.behavior.state = SideSheetBehavior.STATE_EXPANDED
                    }
                }

                override fun onSlide(sheet: View, slideOffset: Float) {
                }
            })

            val inflater =
                DaftarIsiSideSheetDialogBinding.inflate(LayoutInflater.from(requireContext()))

            inflater.rvDaftarIsi.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = this@ContentBukuFragment.contentAdapter
            }
            setObserveDataContent()

            sideSheetDialog.setCancelable(false)
            sideSheetDialog.setCanceledOnTouchOutside(true)
            sideSheetDialog.setContentView(inflater.root)
            sideSheetDialog.show()
        }
    }

    private fun getIsiContent() {
        val recyclerView: RecyclerView = binding.rvContent
        val layoutManager = LinearLayoutManager(requireContext())
        val adapter = ContentIsiBukuAdapter(emptyList())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        //ganti cara dapet contentID
        val contentId = ""

        contentViewModel = ViewModelProvider(this).get(ContentViewModel::class.java)

        contentViewModel.contentData.observe(viewLifecycleOwner, Observer { getContentResponse ->
            getContentResponse?.let {
                if (it.data != null) {
                    Log.d("ContentBukuFragment", "Data received: ${it.data}")
                    adapter.updateData(listOf(it.data))
                } else {
                    Log.e("ContentBukuFragment", "Data is null in the response.")
                }
            }
        })

        contentViewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->

        })

        contentViewModel.getContent(contentId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}