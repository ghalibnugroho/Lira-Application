package com.wantobeme.lira.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.wantobeme.lira.model.Katalog
import com.wantobeme.lira.model.KatalogDetail
import com.wantobeme.lira.repository.KatalogRepository
import com.wantobeme.lira.views.uimodel.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KatalogViewModel @Inject constructor(
    private val katalogRepository: KatalogRepository
    ): ViewModel()
{
    private val _katalogResponse = MutableStateFlow<Resource<List<Katalog>>?>(null)
    val katalogResponse: StateFlow<Resource<List<Katalog>>?> = _katalogResponse

    init {
        viewModelScope.launch {
            getKatalogList()
        }
    }

    fun getKatalogList() = viewModelScope.launch {
        _katalogResponse.value = Resource.Loading
        val result = katalogRepository.getKatalog()
        _katalogResponse.value = result
        Log.i("Katalog Result", "${result}")
        Log.i("Katalog State", "${_katalogResponse.value}")
    }
}






