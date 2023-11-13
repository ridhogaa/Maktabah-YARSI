package com.maktabah.maktabahyarsi.ui.login

import android.app.Activity
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.data.network.api.model.auth.LoginRequestBody
import com.maktabah.maktabahyarsi.databinding.FragmentLoginBinding
import com.maktabah.maktabahyarsi.utils.safeNavigate
import com.maktabah.maktabahyarsi.wrapper.proceedWhen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    private val signInResultHandler =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { activityResult: ActivityResult ->
            when (activityResult.resultCode) {
                Activity.RESULT_OK -> {
                    try {
                        val idToken =
                            oneTapClient.getSignInCredentialFromIntent(activityResult.data).googleIdToken
                        when {
                            idToken != null -> {
                                // TODO - Got an ID token from Google. Use it to authenticate with Firebase
                                Log.d("TEST_TOKEN", "Got ID token. $idToken")
                            }

                            else -> {
                                Log.d("TEST_TOKEN", "No ID token!") // Shouldn't happen
                            }
                        }
                    } catch (e: ApiException) {
                        when (e.statusCode) {
                            CommonStatusCodes.CANCELED -> {
                                Log.d("TEST_TOKEN", "One-tap dialog was closed.")
                            }

                            CommonStatusCodes.NETWORK_ERROR -> {
                                Log.d("TEST_TOKEN", "One-tap encountered a network error.")
                            }

                            else -> {
                                Log.d(
                                    "TEST_TOKEN",
                                    "Couldn't get credential from result: ${e.message}"
                                )
                            }
                        }
                    }
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        guestLogin()
        signUp()
        loginWithGoogle()
        login()
    }

    private fun login() = with(binding) {
        btnLogin.setOnClickListener {
            if (validateInput()) {
                viewModel.login(
                    LoginRequestBody(
                        etPassword.text.toString().trim(),
                        etEmail.text.toString().trim()
                    )
                )
                observeResult()
            }
        }
    }

    private fun loginWithGoogle(): Unit = with(binding) {
        oneTapClient = Identity.getSignInClient(requireActivity())
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()
        btnLoginGoogle.setOnClickListener {
            oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(requireActivity()) { result ->
                    try {
                        signInResultHandler.launch(
                            IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                        )
                    } catch (e: IntentSender.SendIntentException) {
                        e.printStackTrace()
                    }
                }
                .addOnFailureListener(requireActivity()) { e ->
                    e.printStackTrace()
                }
        }
    }

    private fun observeResult() = with(binding) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginResponse.collectLatest {
                    it.proceedWhen(
                        doOnSuccess = { success ->
                            pbLoading.isVisible = false
                            btnLogin.isVisible = true
                            btnLogin.isEnabled = false
                            viewModel.setUserTokenPref(success.payload?.data?.accessToken?.token.toString())
                            viewModel.setUserIdPref(success.payload?.data?.accessToken?.id.toString())
                            navigateToMain()
                        },
                        doOnLoading = {
                            pbLoading.isVisible = true
                            btnLogin.isVisible = false
                        },
                        doOnError = {
                            pbLoading.isVisible = false
                            btnLogin.isVisible = true
                            btnLogin.isEnabled = true
                            Toast.makeText(
                                requireContext(),
                                "Login Failed: ${it.exception?.message.orEmpty()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }
        }
    }

    private fun navigateToMain() =
        findNavController().safeNavigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())


    private fun signUp(): Unit = binding.tvSignUp.setOnClickListener {
        findNavController().safeNavigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
    }

    private fun guestLogin(): Unit = binding.tvGuestLogin.setOnClickListener {
        findNavController().safeNavigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
    }

    private fun validateInput(): Boolean {
        var flag = true
        with(binding) {
            if (etEmail.text.toString().isEmpty()) {
                flag = false
                tilEmail.error = getString(R.string.error_field_empty)
                etEmail.requestFocus()
            }
            if (etPassword.text.toString().isEmpty()) {
                flag = false
                tilPassword.error = getString(R.string.error_field_empty)
                etPassword.requestFocus()
            }
            if (tilPassword.isErrorEnabled) {
                flag = false
            } else if (tilEmail.isErrorEnabled) {
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