package com.ssafy.tott.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.ssafy.tott.databinding.ActivityLoginBinding
import com.ssafy.tott.ui.home.HomeActivity
import com.ssafy.tott.ui.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initButton()
        initObserve()
    }

    private fun initButton() {
        binding.btnLoginLogin.setOnClickListener {
            val id = binding.editTextIdLogin.text.toString()
            val password = binding.editTextPwLogin.text.toString()
            viewModel.login(id, password)
        }
        binding.btnRegisterLogin.setOnClickListener { view ->
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun initObserve() {
        lifecycleScope.launch {
            viewModel.loginState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collect {
                when (it) {
                    is LoginViewModel.UiState.Loading -> {

                    }

                    is LoginViewModel.UiState.Success -> {
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                        finish()
                    }

                    is LoginViewModel.UiState.Error -> {
                        Snackbar.make(
                            binding.root, it.throwable.message ?: "오류 발생", Snackbar.LENGTH_LONG
                        ).show()
                    }

                    is LoginViewModel.UiState.Wait -> {

                    }
                }
            }
        }
    }
}