package com.ssafy.tott.ui.register

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.ssafy.tott.databinding.ActivityRegisterBinding
import com.ssafy.tott.domain.model.RegisterUser
import com.ssafy.tott.ui.register.RegisterViewModel.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnCertRegister.setOnClickListener {
            viewModel.makeCertNum(
                RegisterUser(
                    id = binding.editTextIdRegister.text?.toString() ?: "",
                    password = binding.editTextPwRegister.text?.toString() ?: "",
                    password2 = binding.editTextPw2Register.text?.toString() ?: "",
                    phone = binding.editTextPhoneRegister.text?.toString() ?: "",
                    accountNum = binding.editTextAccountNumRegister.text?.toString() ?: "",
                )
            )
        }

        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collect {
                when (it) {
                    is UiState.Error -> {
                        Snackbar.make(
                            binding.root, it.throwable.message ?: "오류 발생", Snackbar.LENGTH_SHORT
                        ).show()

                    }

                    is UiState.Loading -> {
                        Snackbar.make(
                            binding.root, "로딩중", Snackbar.LENGTH_SHORT
                        ).show()
                    }

                    is UiState.Success -> {
                        Snackbar.make(
                            binding.root, "인증 번호 전송", Snackbar.LENGTH_SHORT
                        ).show()
                        binding.editTextCertNumRegister.isEnabled = true
                    }
                }

            }
        }


    }
}