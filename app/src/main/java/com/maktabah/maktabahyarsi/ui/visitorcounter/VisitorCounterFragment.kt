package com.maktabah.maktabahyarsi.ui.visitorcounter

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maktabah.maktabahyarsi.R

class VisitorCounterFragment : Fragment() {

    companion object {
        fun newInstance() = VisitorCounterFragment()
    }

    private lateinit var viewModel: VisitorCounterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_visitor_counter, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VisitorCounterViewModel::class.java)
        // TODO: Use the ViewModel
    }

}