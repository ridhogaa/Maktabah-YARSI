package com.maktabah.maktabahyarsi.ui.detailbuku.contentbuku

//import com.maktabah.maktabahyarsi.ui.detailbuku.contentbuku.adapter.ContentIsiBukuAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.amrdeveloper.treeview.TreeNode
import com.amrdeveloper.treeview.TreeViewAdapter
import com.google.android.material.sidesheet.SideSheetBehavior
import com.google.android.material.sidesheet.SideSheetCallback
import com.google.android.material.sidesheet.SideSheetDialog
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.data.network.api.model.book.DataItemListContent
import com.maktabah.maktabahyarsi.data.network.api.model.book.GetContentResponse
import com.maktabah.maktabahyarsi.databinding.DaftarIsiSideSheetDialogBinding
import com.maktabah.maktabahyarsi.databinding.FragmentContentBukuBinding
import com.maktabah.maktabahyarsi.databinding.ItemDaftarIsiBinding
import com.maktabah.maktabahyarsi.ui.detailbuku.contentbuku.adapter.ListOfContentAdapter
import com.maktabah.maktabahyarsi.wrapper.proceedWhen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContentBukuFragment : Fragment() {

    private var _binding: FragmentContentBukuBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ContentBukuViewModel by viewModels()
    private val navArgs: ContentBukuFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentBukuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolBarAction()
        getData()
        setObserveDataContentDetail()
    }

    private fun toolBarAction() = with(binding) {
        iconBack.setOnClickListener {
            findNavController().popBackStack()
        }
        iconNav.setOnClickListener {
            showSideSheet()
        }
    }

    private fun getData() = with(viewModel) {
        getContentsBook(navArgs.id)
        getContentDetail(navArgs.id)
    }

    private fun setObserveDataContentDetail() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.contentDetailResponse.collectLatest {
                    it.proceedWhen(
                        doOnSuccess = { result ->
                            bindViewContent(result.payload)
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

    private fun bindViewContent(data: GetContentResponse?) = with(binding) {

    }

    private val treeViewAdapter: TreeViewAdapter by lazy {
        TreeViewAdapter { v: View?, layout: Int ->
            FileViewHolder(
                ItemDaftarIsiBinding.inflate(
                    LayoutInflater.from(requireContext())
                )
            )
        }
    }

    private fun showSideSheet() = with(binding) {
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
            isNestedScrollingEnabled = false
            adapter = testadapter
        }

        setObserveDataContent()


        val javaDirectory = TreeNode("Java", R.layout.item_daftar_isi)
        javaDirectory.addChild(TreeNode("FileJava1.java", R.layout.item_daftar_isi))
        javaDirectory.addChild(TreeNode("FileJava2.java", R.layout.item_daftar_isi))
        javaDirectory.addChild(TreeNode("FileJava3.java", R.layout.item_daftar_isi))

        val fileRoots: MutableList<TreeNode> = ArrayList()
        fileRoots.add(javaDirectory)

//        treeViewAdapter.updateTreeNodes(fileRoots)
//
//        treeViewAdapter.setTreeNodeClickListener { treeNode: TreeNode, nodeView: View? ->
//
//        }
//
//        treeViewAdapter.setTreeNodeLongClickListener { treeNode: TreeNode, nodeView: View? ->
//
//            true
//        }

        sideSheetDialog.setCancelable(false)
        sideSheetDialog.setCanceledOnTouchOutside(true)
        sideSheetDialog.setContentView(inflater.root)
        sideSheetDialog.show()
    }

    private val testadapter: ListOfContentAdapter by lazy {
        ListOfContentAdapter(
            {}
        )
    }

    private fun setObserveDataContent() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.contentResponse.collectLatest {
                    it.proceedWhen(
                        doOnSuccess = { result ->
                            result.payload?.let {
                                testadapter.setData(it.data)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}