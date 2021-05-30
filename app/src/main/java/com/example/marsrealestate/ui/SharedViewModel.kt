package com.example.marsrealestate.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsrealestate.api.RetrofitInstance
import com.example.marsrealestate.data.MarsData
import com.example.marsrealestate.utils.MarsApiFilter
import com.example.marsrealestate.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import java.lang.Exception

private const val TAG = "MyViewModel"

private fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> = this

class SharedViewModel : ViewModel() {
    private val _properties: MutableLiveData<Resource<List<MarsData>>> = MutableLiveData()
    private val _sharedViewModelEvents: MutableLiveData<SharedViewModelEvent> = MutableLiveData()

    val properties = _properties.asLiveData()
    val sharedViewModelEvents = _sharedViewModelEvents.asLiveData()

    init {
        getProperties(filterVal = MarsApiFilter.SHOW_ALL.filterVal)
    }

    private fun getProperties(filterVal: String) = viewModelScope.launch {
        _properties.postValue(Resource.Loading())
        try {
            val response = RetrofitInstance.api.getProperties(filterVal)
            _properties.postValue(handleGetPropertiesResponse(response))
        } catch (e: Exception) {
            when (e) {
                is IOException -> _properties.postValue(Resource.Error("Some error occurred!"))
                else -> _properties.postValue(Resource.Error("Conversion Error!"))
            }
        }
    }

    private fun handleGetPropertiesResponse(response: Response<List<MarsData>>): Resource<List<MarsData>>? {
        if (response.isSuccessful) {
            response.body()?.let { res ->
                return Resource.Success(data = res)
            }
        }
        return Resource.Error(response.message())
    }

    fun onItemClick(item: MarsData) {
        _sharedViewModelEvents.postValue(SharedViewModelEvent.NavigateToDetailFragment(item))
    }

    fun updateFilter(filter: MarsApiFilter) = getProperties(filterVal = filter.filterVal)

    sealed class SharedViewModelEvent {
        data class NavigateToDetailFragment(val item: MarsData) : SharedViewModelEvent()

    }
}