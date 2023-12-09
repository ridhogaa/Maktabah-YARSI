package com.maktabah.maktabahyarsi.ui.register

import android.app.Activity
import android.app.Activity.RESULT_OK
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
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.gson.JsonObject
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.data.network.api.model.auth.RegisterRequestBody
import com.maktabah.maktabahyarsi.databinding.FragmentRegisterBinding
import com.maktabah.maktabahyarsi.utils.getDataJwt
import com.maktabah.maktabahyarsi.utils.safeNavigate
import com.maktabah.maktabahyarsi.wrapper.proceedWhen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var client: GoogleSignInClient

    private val signInResultHandler =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    val jsonObject = JsonObject()
                    jsonObject.addProperty("token", account.idToken!!)
                    viewModel.loginWithGoogle(jsonObject)
                    observeResultRegisterWithGoogle()
                } catch (e: ApiException) {
                    Log.w("TAG", "Google sign in failed", e)
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

    private fun registerWithGoogle(): Unit = with(binding) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        client = GoogleSignIn.getClient(requireActivity(), gso)
        btnRegisterGoogle.setOnClickListener {
            signInResultHandler.launch(client.signInIntent)
        }
    }

    private fun observeResultRegisterWithGoogle() = with(binding) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.loginGoogleResponse.collectLatest {
                    it.proceedWhen(
                        doOnSuccess = { success ->
                            pbLoadingGoogle.isVisible = false
                            btnRegisterGoogle.isVisible = true
                            btnRegisterGoogle.isEnabled = false
                            Toast.makeText(
                                requireContext(),
                                success.payload?.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            success.payload?.data?.accessToken?.token?.let { token ->
                                viewModel.setUserTokenPref(token)
                                viewModel.setUserIdPref(token.getDataJwt().getString("id"))
                            }
                            viewModel.updateVisitorCounter()
                            Toast.makeText(
                                requireContext(),
                                success.payload?.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            navigateToHome()
                        },
                        doOnLoading = {
                            pbLoadingGoogle.isVisible = true
                            btnRegisterGoogle.isVisible = false
                        },
                        doOnError = {
                            pbLoadingGoogle.isVisible = false
                            btnRegisterGoogle.isVisible = true
                            btnRegisterGoogle.isEnabled = true
                            Toast.makeText(
                                requireContext(),
                                "Sign Failed: ${it.exception?.message.orEmpty()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }
        }
    }

    private fun navigateToLogin() {
        // TODO : navigate to Login
        findNavController().safeNavigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
    }

    private fun navigateToHome() {
        // TODO : navigate to Home
        findNavController().safeNavigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
    }

    private fun observeResult() = with(binding) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerResponse.collectLatest {
                    it.proceedWhen(
                        doOnSuccess = { success ->
                            pbLoading.isVisible = false
                            btnRegister.isVisible = true
                            btnRegister.isEnabled = false
                            Toast.makeText(
                                requireContext(),
                                success.payload?.message,
                                Toast.LENGTH_SHORT
                            ).show()
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
                    navigateToLogin()
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
                Toast.makeText(requireContext(), "Password tidak matching!", Toast.LENGTH_SHORT).show()
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