package com.wantobeme.lira.viewmodel.guest

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wantobeme.lira.model.Katalog
import com.wantobeme.lira.repository.KatalogRepository
import com.wantobeme.lira.views.uiState.KatalogState
import com.wantobeme.lira.views.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class KatalogViewModel @Inject constructor(
    private val katalogRepository: KatalogRepository
): ViewModel() {

    private val _katalogResponse = MutableStateFlow<Resource<List<Katalog>>?>(null)
    val katalogResponse: StateFlow<Resource<List<Katalog>>?> = _katalogResponse

    var searchList by mutableStateOf(KatalogState())

    init {
        getKatalogList()
    }

    fun getKatalogList() = viewModelScope.launch {
        _katalogResponse.value = Resource.Loading
        val result = katalogRepository.getKatalog()
        _katalogResponse.value = result
        Log.i("Katalog Result", "${result}")
        Log.i("Katalog State", "${_katalogResponse.value}")
    }

    fun searchKatalogList(param: String) = viewModelScope.launch {
        searchList = KatalogState(isLoading = true)
        val data : Resource<List<Katalog>>
        try{
            runBlocking {
                val result = katalogRepository.searchKatalogsList(param)
                data = result
            }
//            val result = katalogRepository.searchKatalogsList(param)
            when(data){
                is Resource.Failure -> {
                    searchList = KatalogState(error = "Terjadi kesalahan")
                }
                is Resource.Success -> {
                    data.result.let {
                        searchList = KatalogState(data = it)
                    }
                }
            }
        }catch (exception: Exception){
            searchList = KatalogState(error = "Terjadi kesalahan pada catch block:")
        }
    }
}






