package com.example.marsrealestate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.example.marsrealestate.databinding.ActivityMainBinding
import com.example.marsrealestate.ui.SharedViewModel
import com.example.marsrealestate.ui.detailFragment.DetailPropertyFragment
import com.example.marsrealestate.ui.marsPropertyList.PropertyListFragment
import com.example.marsrealestate.utils.MarsApiFilter

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

        viewModel.sharedViewModelEvents.observe(this) { event ->
            when (event) {
                is SharedViewModel.SharedViewModelEvent.NavigateToDetailFragment -> {
                    // navigate to DetailPropertyFragment
                    val detailPropertyFragment = DetailPropertyFragment.newInstance(event.item)
                    supportFragmentManager
                        .beginTransaction()
                        .replace(
                            R.id.fragment_container,
                            detailPropertyFragment,
                            "detailPropertyFrag"
                        ).addToBackStack("propertyList")
                        .commit()
                }
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    // invoking the viewModel's method to update the filter val and make a n/w call to fetch properties
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.updateFilter(
            when (item.itemId) {
                R.id.item_show_buy -> MarsApiFilter.SHOW_BUY
                R.id.item_show_rent -> MarsApiFilter.SHOW_RENT
                else -> MarsApiFilter.SHOW_ALL
            }
        )
        return super.onOptionsItemSelected(item)
    }
}