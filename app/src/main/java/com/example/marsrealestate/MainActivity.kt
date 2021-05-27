package com.example.marsrealestate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.marsrealestate.databinding.ActivityMainBinding
import com.example.marsrealestate.ui.SharedViewModel
import com.example.marsrealestate.ui.marsPropertyList.PropertyListFragment

lateinit var viewModel: SharedViewModel

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        val propertyListFragment = PropertyListFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, propertyListFragment, "propertyList").commit()
    }
}