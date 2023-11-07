package com.maktabah.maktabahyarsi.ui.onboarding

import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.databinding.FragmentOnboardingBinding
import com.maktabah.maktabahyarsi.utils.safeNavigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class OnboardingFragment : Fragment() {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OnboardingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkDoneWithOnboarding()
        binding.tvDesc3.text = Html.fromHtml(resources.getString(R.string.desc_3))
        doneWithFab()
        doneWithTvLewati()
    }

    private fun checkDoneWithOnboarding() =
        viewModel.getOnboardingPref.observe(viewLifecycleOwner) {
            if (it == true) navigateToLogin()
        }

    private fun doneWithFab() = with(binding) {
        fabDone.setOnClickListener {
            viewModel.setOnboardingPref(true)
            navigateToLogin()
        }
    }

    private fun doneWithTvLewati() = with(binding) {
        tvLewati.setOnClickListener {
            viewModel.setOnboardingPref(true)
            navigateToLogin()
        }
    }

    private fun navigateToLogin() =
        findNavController().safeNavigate(OnboardingFragmentDirections.actionOnboardingFragmentToLoginFragment())

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}