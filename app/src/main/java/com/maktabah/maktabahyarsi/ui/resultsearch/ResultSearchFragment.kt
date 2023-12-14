package com.maktabah.maktabahyarsi.ui.resultsearch

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.databinding.FragmentResultSearchBinding
import com.maktabah.maktabahyarsi.ui.resultsearch.adapter.BookAdapter
import com.maktabah.maktabahyarsi.ui.resultsearch.adapter.SectionsPagerAdapter
import com.maktabah.maktabahyarsi.ui.resultsearch.adapter.WordAdapter
import com.maktabah.maktabahyarsi.utils.safeNavigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultSearchFragment : Fragment() {

    private var _binding: FragmentResultSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ResultSearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultSearchBinding.inflate(layoutInflater, container, false)
        handleOnBackPressed()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateBack()
        setUpSectionsPagerAndSetQuery()
    }

    private fun navigateBack() = with(binding) {
        icBack.setOnClickListener {
            findNavController().safeNavigate(ResultSearchFragmentDirections.actionResultSearchFragmentToHomeFragment())
        }
    }

    private fun setUpSectionsPagerAndSetQuery() = binding.run {
        val bundle = Bundle()
        showSoftKeyboard(etSearch)
        etSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // Panggil metode pencarian saat pengguna menekan tombol "Submit" pada keyboard
                val searchQuery = etSearch.text.toString().trim()
                bundle.putString("QUERY", searchQuery)
                findNavController().navigate(R.id.action_resultSearchFragment_self, bundle)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        tvCari.setOnClickListener {
            bundle.putString("QUERY", etSearch.text.toString().trim())
            findNavController().navigate(R.id.action_resultSearchFragment_self, bundle)
        }
        val sectionsPagerAdapter = SectionsPagerAdapter(this@ResultSearchFragment, arguments)
        pager.adapter = sectionsPagerAdapter
        TabLayoutMediator(tabLayout, pager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleOnBackPressed() {
        val callbacks: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().safeNavigate(ResultSearchFragmentDirections.actionResultSearchFragmentToHomeFragment())
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callbacks)
    }

    private fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val inputMethodManager: InputMethodManager =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1, R.string.tab_text_2
        )
    }
}