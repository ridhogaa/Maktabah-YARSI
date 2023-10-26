package com.maktabah.maktabahyarsi.ui.resultsearch.kata

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maktabah.maktabahyarsi.R

class KataFragment : Fragment() {

    companion object {
        fun newInstance() = KataFragment()
    }

    private lateinit var viewModel: KataViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_kata, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(KataViewModel::class.java)
        // TODO: Use the ViewModel
    }

}