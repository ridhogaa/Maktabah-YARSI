package com.maktabah.maktabahyarsi.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.maktabah.maktabahyarsi.data.network.api.model.user.GetUserByIdResponse
import com.maktabah.maktabahyarsi.databinding.FragmentProfileBinding
import com.maktabah.maktabahyarsi.utils.safeNavigate
import com.maktabah.maktabahyarsi.wrapper.proceedWhen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        navigateToEditProfile()
        observeCurrentUser()
        navigateToAbout()
        navigateLogout()
    }

    private fun navigateToAbout() = with(binding) {
        groupAbout.setOnClickListener {

        }
    }

    private fun navigateLogout() = with(binding) {
        btnKeluar.setOnClickListener {
            viewModel.removeSession()
            findNavController().safeNavigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
        }
    }

    private fun getData() {
        viewModel.getUserById()
    }

    private fun observeCurrentUser() = with(binding) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userResponse.collectLatest {
                    it.proceedWhen(
                        doOnSuccess = { success ->
                            success.payload?.let { data ->
                                setData(data)
                            }
                        },
                        doOnLoading = {
                        },
                        doOnError = {

                        }
                    )
                }
            }
        }
    }

    private fun setData(data: GetUserByIdResponse) = with(binding) {
        tvNama.text = data.data.username
    }

    private fun navigateToEditProfile() = with(binding) {
        groupProfile.setOnClickListener {
            findNavController().safeNavigate(ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}