package com.ssafy.tott.ui.searchfilter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.slider.RangeSlider
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.ssafy.tott.R
import com.ssafy.tott.databinding.FragmentSearchFilterBinding
import com.ssafy.tott.ui.map.SearchMapActivity
import com.ssafy.tott.ui.map.SearchMapViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFilterFragment() : Fragment() {
    private val searchMapViewModel: SearchMapViewModel by activityViewModels()
    private val searchFilterViewModel: SearchFilterViewModel by viewModels()
    private var _binding: FragmentSearchFilterBinding? = null
    private val binding get() = _binding!!
    private val callback: OnBackPressedCallback by lazyOf(initOnBackPressedCallback())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchFilterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.autoTextViewBuiltSearchFilter.setAutoTextView(
            builtArray, "전체"
        )
        binding.rangeSliderAreaSearchFilter.initRangeSlider(
            SearchMapViewModel.MIN_AREA.toFloat(),
            SearchMapViewModel.MAX_AREA.toFloat(),
            AREA_RANGE_SEPARATION,
            searchMapViewModel.areaList,
        ) // 단위 평
        binding.rangeSliderPriceSearchFilter.initRangeSlider(
            SearchMapViewModel.MIN_PRICE.toFloat(),
            SearchMapViewModel.MAX_PRICE.toFloat(),
            PRICE_RANGE_SEPARATION,
            searchMapViewModel.priceList,
        ) // 단위 천만
        binding.rangeSliderPriceSearchFilter.setOnTouchListener { v, event ->
            val rangeSlider = v as RangeSlider
            Log.d(this::class.simpleName, "onViewCreated: ${v.values}")
            if (event?.action == MotionEvent.ACTION_UP) {
                Log.d(this::class.simpleName, "onViewCreated: action up ${v.values}")
                searchFilterViewModel.setMaxPrice(rangeSlider.values[1].toInt())
            }
            false
        }
        initObserve()
    }

    private fun initObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            searchFilterViewModel.districtMap.observe(
                viewLifecycleOwner
            ) {
                val array = it.keys.toTypedArray()
                binding.autoTextViewAddress1SearchFilter.setAutoTextView(
                    array, array.firstOrNull() ?: "선택"
                ) { searchFilterViewModel.selectDistrict(it) }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            searchFilterViewModel.uiError.flowWithLifecycle(
                viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED
            ).collect {
                if (it != null) {
                    Snackbar.make(binding.root, it.message ?: "오류 발생", Snackbar.LENGTH_LONG).show()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            searchFilterViewModel.legalDongMap.observe(
                viewLifecycleOwner
            ) {
                Log.d("SearchFilterFragment", "observe legalDongMap: $it")
                val array = it.keys.toTypedArray()
                binding.autoTextViewAddress2SearchFilter.setAutoTextView(
                    array, array.firstOrNull() ?: "선택"
                )
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            searchFilterViewModel.creditLine.flowWithLifecycle(lifecycle).collect {
                binding.tvMaxRentSearchFilter.text =
                    resources.getString(R.string.creditLine_filter, it)
            }
        }
    }

    private fun AutoCompleteTextView.setAutoTextView(
        array: Array<String>,
        value: String,
        listener: ((str: String?) -> Unit)? = null,
    ) {
        setText(value)
        val textView = this as? MaterialAutoCompleteTextView ?: return
        textView.setSimpleItems(array)
        textView.setOnItemClickListener { adapterView, view, i, l ->
            if (listener != null) {
                listener((view as? TextView)?.text.toString())
            }
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
                context.getString(R.string.MAX_RANGE_BAR)
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
        val districtCode = searchFilterViewModel.districtMap.value?.get(districtName) ?: -1
        val legalDongCode = searchFilterViewModel.legalDongMap.value?.get(legalDongName) ?: -1
        val built =
            binding.autoTextViewBuiltSearchFilter.editableText.toString().takeWhile { it.isDigit() }
                .takeIf { it.isNotBlank() }?.toInt() ?: 1000
        val priceList = binding.rangeSliderPriceSearchFilter.values
        val areaList = binding.rangeSliderAreaSearchFilter.values
        val typeIds = binding.btnGroupHouseTypeSearchFilter.checkedButtonIds
        val types = typeIds.filter { it != View.NO_ID }
            .map { binding.root.findViewById<Button>(it).text.toString() }
        searchMapViewModel.saveFilterSetting(
            districtCode, legalDongCode, built, priceList, areaList, types
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
        private val builtArray = arrayOf("전체", "5년전", "10년전", "15년전")

        private const val PRICE_RANGE_SEPARATION = 3f
        private const val AREA_RANGE_SEPARATION = 3f
    }
}