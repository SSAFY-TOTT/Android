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
import com.ssafy.tott.domain.model.ExtraMoney
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExtraMoneyActivity : AppCompatActivity() {
    private val binding by lazy { ActivityExtraMoneyBinding.inflate(layoutInflater) }
    private val viewModel: ExtraMoneyViewModel by viewModels()
    private var clickedSaveButton: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initToolbar()
        initStateObserve()
        initBudget()
    }

    private fun initBudget() {
        lifecycleScope.launch {
            viewModel.extraMoneyList.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { list ->
                    list.getOrElse(0) { ExtraMoney("연소득", 0) }.let {
                        binding.editTextDescription.setText(it.name)
                        binding.editTextMoneyExtraMoney.setText(it.money.toString())
                    }
                    list.getOrNull(1)?.let {
                        binding.editTextDescription2.setText(it.name)
                        binding.editTextMoneyExtraMoney2.setText(it.money.toString())
                    } ?: return@collect
                    list.getOrNull(2)?.let {
                        binding.editTextDescription3.setText(it.name)
                        binding.editTextMoneyExtraMoney3.setText(it.money.toString())
                    } ?: return@collect
                    list.getOrNull(3)?.let {
                        binding.editTextDescription4.setText(it.name)
                        binding.editTextMoneyExtraMoney4.setText(it.money.toString())
                    } ?: return@collect
                }
        }
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbarExtraMoney)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_search_filter, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }

        R.id.action_saveFilter -> {
            saveExtraMoneyList()
            clickedSaveButton = true
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun saveExtraMoneyList() {
        viewModel.saveExtraMoneyList(
            binding.editTextDescription.text.toString() to
                    (binding.editTextMoneyExtraMoney.text.toString().toIntOrNull() ?: 0),
            binding.editTextDescription2.text.toString() to
                    (binding.editTextMoneyExtraMoney2.text.toString().toIntOrNull() ?: 0),
            binding.editTextDescription3.text.toString() to
                    (binding.editTextMoneyExtraMoney3.text.toString().toIntOrNull() ?: 0),
            binding.editTextDescription4.text.toString() to
                    (binding.editTextMoneyExtraMoney4.text.toString().toIntOrNull() ?: 0),
        )
    }

    private fun initStateObserve() {
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collect {
                when (it) {
                    is ExtraMoneyViewModel.UiState.Loading -> {

                    }

                    is ExtraMoneyViewModel.UiState.Success -> {
                        if (clickedSaveButton) {
                            finish()
                        }
                    }

                    is ExtraMoneyViewModel.UiState.Error -> {
                        Snackbar.make(
                            binding.root, it.throwable.message ?: "오류 발생", Snackbar.LENGTH_LONG
                        ).show()
                        clickedSaveButton = false
                    }

                    is ExtraMoneyViewModel.UiState.Wait -> {

                    }
                }
            }
        }
    }
}