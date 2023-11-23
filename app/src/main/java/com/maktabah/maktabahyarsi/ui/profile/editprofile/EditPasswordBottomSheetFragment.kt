package com.maktabah.maktabahyarsi.ui.profile.editprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.JsonObject
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.data.network.api.model.user.UpdatePasswordRequestBody
import com.maktabah.maktabahyarsi.databinding.FragmentEditPasswordBottomSheetBinding
import com.maktabah.maktabahyarsi.utils.safeNavigate
import com.maktabah.maktabahyarsi.wrapper.proceedWhen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditPasswordBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentEditPasswordBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditPasswordBottomSheetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditPasswordBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updatePassword()
    }

    private fun updatePassword() {
        with(binding) {
            btnSimpan.setOnClickListener {
                if (validateInput()) {
                    viewModel.updatePasswordUserById(
                        UpdatePasswordRequestBody(
                            etPasswordLama.text.toString().trim(),
                            etPasswordBaru.text.toString().trim()
                        )
                    )
                    observeResult()
                }
            }
        }
    }

    private fun observeResult() = with(binding) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.updateResponse.collectLatest {
                    it.proceedWhen(
                        doOnSuccess = { success ->
                            pbLoading.isVisible = false
                            btnSimpan.isVisible = true
                            btnSimpan.isEnabled = false
                            Toast.makeText(
                                requireContext(),
                                success.payload?.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            navigateToProfile()
                        },
                        doOnLoading = {
                            pbLoading.isVisible = true
                            btnSimpan.isVisible = false
                        },
                        doOnError = {
                            pbLoading.isVisible = false
                            btnSimpan.isVisible = true
                            btnSimpan.isEnabled = true
                            Toast.makeText(
                                requireContext(),
                                "Update Failed: ${it.exception?.message.orEmpty()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }
        }
    }

    private fun navigateToProfile() =
        findNavController().safeNavigate(EditPasswordBottomSheetFragmentDirections.actionEditPasswordBottomSheetFragmentToEditProfileFragment())

    private fun validateInput(): Boolean {
        var flag = true
        with(binding) {
            if (etPasswordLama.text.toString().isEmpty()) {
                flag = false
                tilPasswordLama.error = getString(R.string.error_field_empty)
                etPasswordLama.requestFocus()
            }
            if (etPasswordBaru.text.toString().isEmpty()) {
                flag = false
                tilPasswordBaru.error = getString(R.string.error_field_empty)
                etPasswordBaru.requestFocus()
            }
            if (tilPasswordLama.isErrorEnabled) {
                flag = false
            } else if (tilPasswordBaru.isErrorEnabled) {
                flag = false
            }
        }
        return flag
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}