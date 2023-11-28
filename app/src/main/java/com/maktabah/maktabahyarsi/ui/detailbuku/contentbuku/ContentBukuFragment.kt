package com.maktabah.maktabahyarsi.ui.detailbuku.contentbuku

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.sidesheet.SideSheetBehavior
import com.google.android.material.sidesheet.SideSheetCallback
import com.google.android.material.sidesheet.SideSheetDialog
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.databinding.DaftarIsiSideSheetDialogBinding
import com.maktabah.maktabahyarsi.databinding.FragmentContentBukuBinding
import com.maktabah.maktabahyarsi.databinding.FragmentDetailBinding
import com.maktabah.maktabahyarsi.ui.detailbuku.DetailFragmentArgs
import com.maktabah.maktabahyarsi.ui.detailbuku.DetailViewModel
import kotlin.properties.Delegates

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
        showSideSheet()
    }

    private fun showSideSheet() = with(binding) {
        iconNav.setOnClickListener {
            val sideSheetDialog = SideSheetDialog(requireContext())

// Standard side sheets have the following states:
// STATE_EXPANDED: The side sheet is visible at its maximum height
// and it is neither dragging nor settling (see below).
// STATE_HIDDEN: The side sheet is no longer visible and
// can only be re-shown programmatically.
// STATE_DRAGGING: The user is actively dragging the side sheet.
// STATE_SETTLING: The side sheet is settling to a specific height
// after a drag/swipe gesture. This will be the peek height, expanded height,
// or 0, in case the user action caused the side sheet to hide.

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


            sideSheetDialog.setCancelable(false)
            sideSheetDialog.setCanceledOnTouchOutside(true)
            sideSheetDialog.setContentView(inflater.root)
            sideSheetDialog.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}