package io.github.yoobi.sampleviewbinding.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.yoobi.sampleviewbinding.data.ResultCall
import io.github.yoobi.sampleviewbinding.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(val query: String?) : ViewModel() {

    private val _movies = MutableLiveData<List<ResultCall>>()
    val movies: LiveData<List<ResultCall>>
        get() = _movies

    init {
        getSearch(query)
    }

    private fun getSearch(query: String?) {
        viewModelScope.launch {
            try {
                val deferred = withContext(Dispatchers.IO) {
                    Api.tvMazeService.search(query)
                }
                _movies.value = deferred
            } catch (e: Exception) {
                Log.e("HomeViewModel", "${e.message}")
            }
        }
    }

}