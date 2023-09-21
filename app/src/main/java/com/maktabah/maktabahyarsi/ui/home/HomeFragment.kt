package com.maktabah.maktabahyarsi.ui.home

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.databinding.FragmentHomeBinding
import com.maktabah.maktabahyarsi.databinding.FragmentSplashScreenBinding
import com.maktabah.maktabahyarsi.ui.home.adapter.CategoryAdapter
import com.maktabah.maktabahyarsi.ui.home.slider.SliderAdapter
import com.maktabah.maktabahyarsi.ui.splashscreen.SplashScreenViewModel
import com.maktabah.maktabahyarsi.utils.DataCategory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    lateinit var vpSlider: ViewPager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vpSlider = view.findViewById(R.id.viewPager)

        val arraySlider = ArrayList<Int>()
        arraySlider.add(R.drawable.img_example)
        arraySlider.add(R.drawable.example)

        val sliderAdapter = SliderAdapter(arraySlider, requireActivity())
        vpSlider.adapter = sliderAdapter
        binding.rvCategory.apply {
            adapter = CategoryAdapter(DataCategory.listDataCategory)
            layoutManager = GridLayoutManager(requireContext(), 4)
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}