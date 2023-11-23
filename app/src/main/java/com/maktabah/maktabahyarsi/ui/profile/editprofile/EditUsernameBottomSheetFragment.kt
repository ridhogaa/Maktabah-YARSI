package com.maktabah.maktabahyarsi.ui.profile.editprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
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
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.data.network.api.model.user.UpdatePasswordRequestBody
import com.maktabah.maktabahyarsi.data.network.api.model.user.UpdateUsernameRequestBody
import com.maktabah.maktabahyarsi.databinding.FragmentEditProfileBinding
import com.maktabah.maktabahyarsi.databinding.FragmentEditUsernameBottomSheetBinding
import com.maktabah.maktabahyarsi.utils.safeNavigate
import com.maktabah.maktabahyarsi.wrapper.proceedWhen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditUsernameBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentEditUsernameBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditUsernameBottomSheetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditUsernameBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUsername()
    }

    private fun updateUsername() {
        with(binding) {
            btnSimpan.setOnClickListener {
                if (validateInput()) {
                    viewModel.updateUsernameUserById(
                        UpdateUsernameRequestBody(
                            etUsername.text.toString().trim(),
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
        findNavController().safeNavigate(EditUsernameBottomSheetFragmentDirections.actionEditUsernameBottomSheetFragmentToEditProfileFragment())


    private fun validateInput(): Boolean {
        var flag = true
        with(binding) {
            if (etUsername.text.toString().isEmpty()) {
                flag = false
                tilUsername.error = getString(R.string.error_field_empty)
                etUsername.requestFocus()
            }
            if (tilUsername.isErrorEnabled) {
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