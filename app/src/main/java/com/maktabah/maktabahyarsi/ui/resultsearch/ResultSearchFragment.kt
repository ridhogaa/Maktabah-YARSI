package com.maktabah.maktabahyarsi.ui.resultsearch

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maktabah.maktabahyarsi.R

class ResultSearchFragment : Fragment() {

    companion object {
        fun newInstance() = ResultSearchFragment()
    }

    private lateinit var viewModel: ResultSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ResultSearchViewModel::class.java)
        // TODO: Use the ViewModel
    }

}