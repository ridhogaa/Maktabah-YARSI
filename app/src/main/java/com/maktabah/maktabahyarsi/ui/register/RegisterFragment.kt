package com.maktabah.maktabahyarsi.ui.register

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.firebase.auth.GoogleAuthProvider
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.data.network.api.model.auth.RegisterRequestBody
import com.maktabah.maktabahyarsi.databinding.FragmentRegisterBinding
import com.maktabah.maktabahyarsi.utils.proceedWhen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var oneTapClient: SignInClient
    private lateinit var signUpRequest: BeginSignInRequest

    private val signInResultHandler =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { activityResult: ActivityResult ->
            when (activityResult.resultCode) {
                RESULT_OK -> {
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
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        handleOnBackPressed()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login()
        register()
        registerWithGoogle()
    }

    private fun register() = with(binding) {
        btnRegister.setOnClickListener {
            if (validateInput()) {
                viewModel.register(
                    RegisterRequestBody(
                        etPassword.text.toString().trim(),
                        etEmail.text.toString().trim(),
                        etUsername.text.toString().trim()
                    )
                )
                observeResult()
            }
        }
    }

    private fun registerWithGoogle() = with(binding) {
        oneTapClient = Identity.getSignInClient(requireActivity())
        signUpRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()
        btnRegisterGoogle.setOnClickListener {
            oneTapClient.beginSignIn(signUpRequest)
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

    private fun navigateToLogin() {
        // TODO : navigate to Login
        Navigation.findNavController(requireView())
            .navigate(R.id.action_registerFragment_to_loginFragment)
    }

    private fun observeResult() = with(binding) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerResponse.collectLatest {
                    it.proceedWhen(
                        doOnSuccess = {
                            pbLoading.isVisible = false
                            btnRegister.isVisible = true
                            btnRegister.isEnabled = false
                            navigateToLogin()
                        },
                        doOnLoading = {
                            pbLoading.isVisible = true
                            btnRegister.isVisible = false
                        },
                        doOnError = {
                            pbLoading.isVisible = false
                            btnRegister.isVisible = true
                            btnRegister.isEnabled = true
                            Toast.makeText(
                                requireContext(),
                                "Register Failed: ${it.exception?.message.orEmpty()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }
        }
    }

    private fun login(): Unit = binding.tvLogin.setOnClickListener {
        navigateToLogin()
    }

    private fun handleOnBackPressed() {
        val callbacks: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callbacks)
    }

    private fun validateInput(): Boolean {
        var flag = true
        with(binding) {
            if (etUsername.text.toString().isEmpty()) {
                flag = false
                tilUsername.error = getString(R.string.error_field_empty)
                etUsername.requestFocus()
            }
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
            if (etConfirmPassword.text.toString().isEmpty()) {
                flag = false
                tilConfirmPassword.error = getString(R.string.error_field_empty)
                etConfirmPassword.requestFocus()
            }
            if (etPassword.text.toString() != etConfirmPassword.text.toString()) {
                flag = false
            }
            if (tilUsername.isErrorEnabled) {
                flag = false
            } else if (tilPassword.isErrorEnabled) {
                flag = false
            } else if (tilConfirmPassword.isErrorEnabled) {
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