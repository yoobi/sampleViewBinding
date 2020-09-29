package io.github.yoobi.sampleviewbinding.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.github.yoobi.sampleviewbinding.data.AppDatabase
import io.github.yoobi.sampleviewbinding.data.SearchEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(application: Application): AndroidViewModel(application) {

    private val searchDao = AppDatabase.getDatabase(application.applicationContext).searchDao()

    private val _termList = MutableLiveData<List<SearchEntity>>()
    val termList: LiveData<List<SearchEntity>>
        get() = _termList

    init {
        deleteExtrasTerms()
        getTerms()
    }

    fun insertTerm(term: String) {
        viewModelScope.launch(Dispatchers.IO) {
           searchDao.insert(SearchEntity(searchText = term))
        }
    }

    fun getTerms() {
        viewModelScope.launch {
            _termList.value = withContext(Dispatchers.IO) {
                searchDao.getSearch()
            }
        }
    }

    private fun deleteExtrasTerms() {
        viewModelScope.launch(Dispatchers.IO) {
            searchDao.deleteExtras()
        }
    }

    fun deleteAllTerms() {
        viewModelScope.launch(Dispatchers.IO) {
            searchDao.deleteAll()
        }
    }
}