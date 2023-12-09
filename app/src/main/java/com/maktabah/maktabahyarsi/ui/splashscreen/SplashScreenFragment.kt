package com.maktabah.maktabahyarsi.ui.splashscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.maktabah.maktabahyarsi.databinding.FragmentSplashScreenBinding
import com.maktabah.maktabahyarsi.ui.onboarding.OnboardingFragmentDirections
import com.maktabah.maktabahyarsi.utils.isJwtExpired
import com.maktabah.maktabahyarsi.utils.safeNavigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenFragment : Fragment() {

    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SplashScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashScreenBinding.inflate(layoutInflater, container, false)
        Handler().postDelayed({
            lifecycleScope.launchWhenCreated {
                checkUserSession()
            }
        }, 1000L)
        return binding.root
    }

    private fun checkUserSession() = with(viewModel) {
        getOnboardingPref.observe(viewLifecycleOwner) { isDone ->
            getUserTokenPrefFlow.observe(viewLifecycleOwner) { token ->
                if (token.isNotEmpty() && isDone) {
                    if (token.isJwtExpired()) {
                        removeSession()
                        navigateToLogin()
                    } else {
                        navigateToMain()
                    }
                } else if (token.isEmpty() && isDone) {
                    navigateToLogin()
                } else if (token.isEmpty() && !isDone) {
                    navigateToOnboarding()
                }
            }
        }
    }

    private fun navigateToLogin() =
        findNavController().safeNavigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToLoginFragment())

    private fun navigateToMain() =
        findNavController().safeNavigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToHomeFragment())

    private fun navigateToOnboarding() =
        findNavController().safeNavigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToOnboardingFragment())

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}