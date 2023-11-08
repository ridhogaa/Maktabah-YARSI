package com.maktabah.maktabahyarsi.ui.onboarding

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.databinding.FragmentOnboardingBinding
import com.maktabah.maktabahyarsi.utils.safeNavigate
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OnboardingFragment : Fragment() {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OnboardingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(layoutInflater, container, false)
        checkDoneWithOnboardingAndIsLoggedIn()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvDesc3.text = Html.fromHtml(resources.getString(R.string.desc_3))
        doneWithFab()
        doneWithTvLewati()
    }

    private fun checkDoneWithOnboardingAndIsLoggedIn() =
        viewModel.getOnboardingPref.observe(viewLifecycleOwner) { isDone ->
            viewModel.getUserTokenPrefFlow.observe(viewLifecycleOwner) { token ->
                if (token.isNotEmpty() && isDone == true) {
                    navigateToMain()
                } else if (token.isEmpty() && isDone == true) {
                    navigateToLogin()
                }
            }
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

    private fun navigateToMain() =
        findNavController().safeNavigate(OnboardingFragmentDirections.actionOnboardingFragmentToHomeFragment())

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}