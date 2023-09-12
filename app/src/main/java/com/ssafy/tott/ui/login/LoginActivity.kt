package com.ssafy.tott.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.tott.databinding.ActivityLoginBinding
import com.ssafy.tott.ui.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initButton()
    }

    private fun initButton() {
        binding.btnLoginLogin.setOnClickListener { view ->
            // TODO: 로그인 버튼 구현
        }
        binding.btnRegisterLogin.setOnClickListener { view ->
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}