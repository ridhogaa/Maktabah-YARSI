package com.maktabah.maktabahyarsi.ui.profile.editprofile

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.data.network.api.model.user.GetUserByIdResponse
import com.maktabah.maktabahyarsi.databinding.FragmentEditProfileBinding
import com.maktabah.maktabahyarsi.databinding.FragmentProfileBinding
import com.maktabah.maktabahyarsi.ui.profile.ProfileViewModel
import com.maktabah.maktabahyarsi.ui.resultsearch.ResultSearchFragmentDirections
import com.maktabah.maktabahyarsi.utils.safeNavigate
import com.maktabah.maktabahyarsi.wrapper.proceedWhen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(layoutInflater, container, false)
        handleOnBackPressed()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        observeCurrentUser()
        actionBack()
        showEditUsernameAndShowEditPassword()
        onEditImageClicked()
    }

    private fun actionBack() = with(binding) {
        iconBack.setOnClickListener {
            findNavController().safeNavigate(EditProfileFragmentDirections.actionEditProfileFragmentToProfileFragment())
        }
    }

    private fun onEditImageClicked() = binding.run {
        backgroundFoto.setOnClickListener {
            Toast.makeText(requireContext(), "Sedang Dikembangkan!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getData() {
        viewModel.getUserById()
    }

    private fun observeCurrentUser() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userResponse.collectLatest {
                    it.proceedWhen(
                        doOnSuccess = { success ->
                            success.payload?.let { data ->
                                setData(data)
                            }
                        },
                        doOnError = { error ->
                            Toast.makeText(
                                requireContext(),
                                "${error.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }
        }
    }

    private fun handleOnBackPressed() {
        val callbacks: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().safeNavigate(EditProfileFragmentDirections.actionEditProfileFragmentToProfileFragment())
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callbacks)
    }

    private fun setData(data: GetUserByIdResponse) = with(binding) {
        tvUsernameData.text = data.data.username
        tvEmailData.text = data.data.email
    }

    private fun showEditUsernameAndShowEditPassword() = with(binding) {
        groupUsername.setOnClickListener {
            findNavController().safeNavigate(EditProfileFragmentDirections.actionEditProfileFragmentToEditUsernameBottomSheetFragment())
        }
        groupPassword.setOnClickListener {
            findNavController().safeNavigate(EditProfileFragmentDirections.actionEditProfileFragmentToEditPasswordBottomSheetFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}