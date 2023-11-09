package com.maktabah.maktabahyarsi.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.databinding.FragmentHomeBinding
import com.maktabah.maktabahyarsi.ui.home.adapter.CategoryAdapter
import com.maktabah.maktabahyarsi.ui.home.slider.ModelSlider
import com.maktabah.maktabahyarsi.ui.home.slider.SliderAdapter
import com.maktabah.maktabahyarsi.utils.DataCategory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arraySlider = arrayListOf(
        ModelSlider(R.drawable.img_example),
        ModelSlider(R.drawable.example)
        )

        val sliderAdapter = SliderAdapter(arraySlider)
        binding.carouselRecyclerView.adapter = sliderAdapter

        val lm = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.carouselRecyclerView.layoutManager = lm
        binding.carouselRecyclerView.adapter = sliderAdapter


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}