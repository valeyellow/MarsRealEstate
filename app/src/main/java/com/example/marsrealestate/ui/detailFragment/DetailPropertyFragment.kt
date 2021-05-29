package com.example.marsrealestate.ui.detailFragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.marsrealestate.R
import com.example.marsrealestate.data.MarsData
import com.example.marsrealestate.databinding.FragmentPropertyDetailBinding

class DetailPropertyFragment : Fragment(R.layout.fragment_property_detail) {
    private lateinit var clickedProperty: MarsData

    companion object {
        @JvmStatic
        fun newInstance(item: MarsData) = DetailPropertyFragment().apply {
            arguments = Bundle().apply {
                putParcelable("clickedItem", item)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPropertyDetailBinding.bind(view)
        arguments?.getParcelable<MarsData>("clickedItem")?.let { item ->
            clickedProperty = item
        }
        binding.apply {
            Glide
                .with(this@DetailPropertyFragment)
                .load(clickedProperty.img_src)
                .centerCrop()
                .into(detailPropertyIv)

            detailPropertyPriceTv.text = "$" + clickedProperty.price.toString()
            detailPropertyTypeTv.text = clickedProperty.type.toString()
        }
    }
}