package com.ssafy.tott.ui.searchfilter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.slider.RangeSlider
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.ssafy.tott.R
import com.ssafy.tott.databinding.FragmentSearchFilterBinding
import com.ssafy.tott.ui.map.SearchMapActivity
import com.ssafy.tott.ui.map.SearchMapViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFilterFragment : Fragment() {
    private val searchFilterViewModel: SearchMapViewModel by activityViewModels()
    private var _binding: FragmentSearchFilterBinding? = null
    private val binding get() = _binding!!
    private val callback: OnBackPressedCallback by lazyOf(initOnBackPressedCallback())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchFilterBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.autoTextViewAddress1SearchFilter.initAutoTextView(
            districtNames, searchFilterViewModel.getDistrictName() ?: "선택"
        )
        binding.autoTextViewAddress2SearchFilter.initAutoTextView(
            legalDongNames, searchFilterViewModel.getLegalDongName() ?: "선택"
        )
        binding.autoTextViewBuiltSearchFilter.initAutoTextView(
            builtArray, "전체"
        )
        binding.rangeSliderAreaSearchFilter.initRangeSlider(
            SearchMapViewModel.MIN_AREA.toFloat(),
            SearchMapViewModel.MAX_AREA.toFloat(),
            AREA_RANGE_SEPARATION,
            searchFilterViewModel.areaList,
        ) // 단위 평
        binding.rangeSliderPriceSearchFilter.initRangeSlider(
            SearchMapViewModel.MIN_PRICE.toFloat(),
            SearchMapViewModel.MAX_PRICE.toFloat(),
            PRICE_RANGE_SEPARATION,
            searchFilterViewModel.priceList,
        ) // 단위 천만
    }

    private fun AutoCompleteTextView.initAutoTextView(array: Array<String>, value: String) {
        setText(value)
        val textView = this as? MaterialAutoCompleteTextView ?: return
        textView.setSimpleItems(array)
        textView.setOnItemClickListener { adapterView, view, i, l ->
            //TODO 아이템 선택 리스너 구현
        }
    }

    private fun RangeSlider.initRangeSlider(
        from: Float,
        to: Float,
        separation: Float,
        initValue: List<Float>,
        unit: String? = null,
    ) {
        setMinSeparationValue(separation)
        valueFrom = from
        valueTo = to
        values = initValue
        setLabelFormatter { value ->
            if (value >= to) {
                "제한 없음"
            } else if (unit == null) {
                value.toInt().toString()
            } else {
                value.toInt().toString() + unit
            }
        }
    }

    fun savedFilterSetting() {
        val districtName = binding.autoTextViewAddress1SearchFilter.editableText.toString()
        val legalDongName = binding.autoTextViewAddress2SearchFilter.editableText.toString()
        val built = binding.autoTextViewBuiltSearchFilter.editableText.toString()
            .takeWhile { it.isDigit() }.takeIf { it.isNotBlank() }?.toInt() ?: 1000
        val priceList = binding.rangeSliderPriceSearchFilter.values
        val areaList = binding.rangeSliderAreaSearchFilter.values
        val types = binding.btnGroupHouseTypeSearchFilter.checkedButtonIds
        searchFilterViewModel.saveFilterSetting(
            districtName, legalDongName, built,
            priceList, areaList, types.map { it.toString() }
        )
        requireActivity().onBackPressed()
    }

    private fun initOnBackPressedCallback(): OnBackPressedCallback {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (requireActivity() as SearchMapActivity).menuInflate(R.menu.menu_search_map)
                val fragmentManager = requireActivity().supportFragmentManager
                fragmentManager.beginTransaction().remove(this@SearchFilterFragment).commit()
                Log.d("SearchFilterFragment", "handleOnBackPressed: remove Fragment")
                fragmentManager.popBackStack()
            }
        }
        return callback
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        //TODO 드롭다운 임시 데이터
        private val districtNames = arrayOf("강남구", "서초구", "중구")
        private val legalDongNames = arrayOf("역삼1동", "논현1동", "신사동")
        private val builtArray = arrayOf("전체", "5년전", "10년전", "15년전")

        private const val PRICE_RANGE_SEPARATION = 3f
        private const val AREA_RANGE_SEPARATION = 3f
    }
}