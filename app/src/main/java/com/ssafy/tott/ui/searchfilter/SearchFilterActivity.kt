package com.ssafy.tott.ui.searchfilter

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import com.ssafy.tott.R
import com.ssafy.tott.databinding.ActivitySearchFilterBinding

class SearchFilterActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchFilterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDropDown()
        initToolbar()
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbarSearchFilter)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_search_filter, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            Log.d(this::class.simpleName, "onOptionsItemSelected: home")
            true
        }

        R.id.action_saveFilter -> {
            Log.d(this::class.simpleName, "onOptionsItemSelected: save")
            true
        }

        else -> {
            Log.d(
                this::class.simpleName, "onOptionsItemSelected: else -> ${item.itemId}"
            )
            super.onOptionsItemSelected(item)
        }
    }

    private fun initDropDown() {
        initDropDown(binding.inputLayoutAddress1SearchFilter, addressArray1)
        initDropDown(binding.inputLayoutAddress2SearchFilter, addressArray2)
        initDropDown(binding.inputLayoutBuiltSearchFilter, builtArray)
    }

    private fun initDropDown(inputLayout: TextInputLayout, array: Array<String>) {
        val textView = inputLayout.editText as? MaterialAutoCompleteTextView ?: return
        textView.setSimpleItems(array)
        textView.setOnItemClickListener { adapterView, view, i, l ->
            //TODO 아이템 선택 리스너 구현
        }
    }

    companion object {
        //TODO 드롭다운 임시 데이터
        private val addressArray1 = arrayOf("강남구", "서초구", "중구")
        private val addressArray2 = arrayOf("역삼1동", "논현1동", "신사동")
        private val builtArray = arrayOf("전체", "5년전", "10년전", "15년전")

        const val TAG_DISTRICT_NAME = "TAG_DISTRICT_NAME"
    }
}