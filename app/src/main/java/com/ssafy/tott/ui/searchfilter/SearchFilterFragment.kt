package com.ssafy.tott.ui.searchfilter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ssafy.tott.R
import com.ssafy.tott.databinding.FragmentSearchFilterBinding
import com.ssafy.tott.ui.map.SearchMapActivity

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
}