package com.maktabah.maktabahyarsi.ui.detail_buku

import android.content.Context
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        readMore()
    }

    private fun readMore() {
        val getDesc = binding.tvDescriptionBuku.text.toString()
        val words = getDesc.split(" ")
        if (words.size > 30) { // Ubah angka 30 sesuai dengan jumlah kata yang diinginkan
            val truncatedText = words.subList(0, 50).joinToString(" ") + " ... "
            val spannable = SpannableString(truncatedText + "Read more")
            spannable.setSpan(
                ForegroundColorSpan(getColorWrapper(requireContext(), R.color.green_maktabah)),
                truncatedText.length,
                truncatedText.length + 9,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            binding.tvDescriptionBuku.text = spannable
            binding.tvDescriptionBuku.setOnClickListener {

            }
        }
    }


    private fun getColorWrapper(context: Context, colorId: Int): Int {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            context.getColor(colorId)
        } else {
            context.resources.getColor(colorId)
        }
    }



}