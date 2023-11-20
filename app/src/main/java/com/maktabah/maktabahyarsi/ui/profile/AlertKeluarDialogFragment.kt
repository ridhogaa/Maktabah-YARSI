package com.maktabah.maktabahyarsi.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.maktabah.maktabahyarsi.databinding.FragmentAlertKeluarBinding
import com.maktabah.maktabahyarsi.databinding.FragmentProfileBinding
import com.maktabah.maktabahyarsi.utils.safeNavigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlertKeluarDialogFragment : DialogFragment() {
    private var _binding: FragmentAlertKeluarBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlertKeluarBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            tvYa.setOnClickListener {
                viewModel.removeSession()
                findNavController().safeNavigate(AlertKeluarDialogFragmentDirections.actionAlertKeluarDialogFragmentToLoginFragment())
            }
            tvTidak.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}