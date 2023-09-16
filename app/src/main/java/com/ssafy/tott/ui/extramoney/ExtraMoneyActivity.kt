package com.ssafy.tott.ui.extramoney

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.ssafy.tott.R
import com.ssafy.tott.databinding.ActivityExtraMoneyBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExtraMoneyActivity : AppCompatActivity() {
    private val binding by lazy { ActivityExtraMoneyBinding.inflate(layoutInflater) }
    private val viewModel: ExtraMoneyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initToolbar()
        initStateObserve()
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbarExtraMoney)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search_map, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }

        R.id.action_saveFilter -> {
            saveExtraMoneyList()
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun saveExtraMoneyList() {
        viewModel.saveExtraMoneyList(
            binding.editTextDescription.text.toString() to
                    binding.editTextMoneyExtraMoney.text.toString().toInt(),
            binding.editTextDescription2.text.toString() to
                    binding.editTextMoneyExtraMoney2.text.toString().toInt(),
            binding.editTextDescription3.text.toString() to
                    binding.editTextMoneyExtraMoney3.text.toString().toInt(),
            binding.editTextDescription4.text.toString() to
                    binding.editTextMoneyExtraMoney4.text.toString().toInt(),
        )
    }

    private fun initStateObserve() {
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collect {
                when (it) {
                    is ExtraMoneyViewModel.UiState.Loading -> {

                    }

                    is ExtraMoneyViewModel.UiState.Success -> {
                        finish()
                    }

                    is ExtraMoneyViewModel.UiState.Error -> {
                        Snackbar.make(
                            binding.root, it.throwable.message ?: "오류 발생", Snackbar.LENGTH_LONG
                        ).show()
                    }

                    is ExtraMoneyViewModel.UiState.Wait -> {

                    }
                }
            }
        }
    }
}