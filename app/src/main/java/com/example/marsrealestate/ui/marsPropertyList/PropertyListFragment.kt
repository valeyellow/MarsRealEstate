package com.example.marsrealestate.ui.marsPropertyList

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.marsrealestate.R
import com.example.marsrealestate.adapters.MarsPropertyAdapter
import com.example.marsrealestate.data.MarsData
import com.example.marsrealestate.databinding.FragmentMarsPropertyListBinding
import com.example.marsrealestate.ui.SharedViewModel
import com.example.marsrealestate.utils.Resource


class PropertyListFragment : Fragment(R.layout.fragment_mars_property_list),
    MarsPropertyAdapter.OnItemClickListener {
    private lateinit var viewModel: SharedViewModel
    private lateinit var marsPropertyAdapter: MarsPropertyAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        marsPropertyAdapter = MarsPropertyAdapter(this)
        val binding = FragmentMarsPropertyListBinding.bind(view)

        binding.apply {
            propertyRecyclerView.apply {
                adapter = marsPropertyAdapter
                layoutManager = GridLayoutManager(requireContext(), 2)
                setHasFixedSize(true)
            }
        }

        viewModel.properties.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> showHideProgressBar(binding, visibility = true)
                is Resource.Success -> {
                    showHideProgressBar(binding, visibility = false)
                    response.data?.let { marsProperty ->
                        marsPropertyAdapter.differ.submitList(marsProperty)
                    }
                }
                is Resource.Error -> {
                    showHideProgressBar(binding, visibility = false)
                    binding.apply {
                        connectionErrorIv.visibility = View.VISIBLE
                    }
                    response.message?.let { errorMsg ->
                        Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }

    private fun showHideProgressBar(binding: FragmentMarsPropertyListBinding, visibility: Boolean) {
        binding.apply {
            when (visibility) {
                true -> progressBar.visibility = View.VISIBLE
                false -> progressBar.visibility = View.INVISIBLE
            }
        }
    }


    override fun onItemClick(marsDataItem: MarsData) {
        viewModel.onItemClick(marsDataItem)
    }
}