package com.maktabah.maktabahyarsi.ui.login

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.gson.JsonObject
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.data.network.api.model.auth.LoginRequestBody
import com.maktabah.maktabahyarsi.databinding.FragmentLoginBinding
import com.maktabah.maktabahyarsi.utils.getDataJwt
import com.maktabah.maktabahyarsi.utils.safeNavigate
import com.maktabah.maktabahyarsi.wrapper.proceedWhen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()
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
                    observeResultLoginWithGoogle()
                } catch (e: ApiException) {
                    Log.w("TAG", "Google sign in failed", e)
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
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        client = GoogleSignIn.getClient(requireActivity(), gso)
        btnLoginGoogle.setOnClickListener {
            signInResultHandler.launch(client.signInIntent)
        }
    }

    private fun observeResultLoginWithGoogle() = with(binding) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.loginGoogleResponse.collectLatest {
                    it.proceedWhen(
                        doOnSuccess = { success ->
                            pbLoadingGoogle.isVisible = false
                            btnLoginGoogle.isVisible = true
                            btnLoginGoogle.isEnabled = false
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
                            navigateToMain()
                        },
                        doOnLoading = {
                            pbLoadingGoogle.isVisible = true
                            btnLoginGoogle.isVisible = false
                        },
                        doOnError = {
                            pbLoadingGoogle.isVisible = false
                            btnLoginGoogle.isVisible = true
                            btnLoginGoogle.isEnabled = true
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

    private fun observeResult() = with(binding) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginResponse.collectLatest {
                    it.proceedWhen(
                        doOnSuccess = { success ->
                            pbLoading.isVisible = false
                            btnLogin.isVisible = true
                            btnLogin.isEnabled = false
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
        viewModel.updateVisitorCounter()
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