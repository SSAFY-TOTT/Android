package com.ssafy.tott.ui.searchfilter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import com.ssafy.tott.databinding.ActivitySearchFilterBinding

class SearchFilterActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchFilterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDropDown()
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
    }
}