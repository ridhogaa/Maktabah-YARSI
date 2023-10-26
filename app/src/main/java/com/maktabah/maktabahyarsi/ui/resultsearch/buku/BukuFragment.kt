package com.maktabah.maktabahyarsi.ui.resultsearch.buku

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maktabah.maktabahyarsi.R

class BukuFragment : Fragment() {

    companion object {
        fun newInstance() = BukuFragment()
    }

    private lateinit var viewModel: BukuViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_buku, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BukuViewModel::class.java)
        // TODO: Use the ViewModel
    }

}