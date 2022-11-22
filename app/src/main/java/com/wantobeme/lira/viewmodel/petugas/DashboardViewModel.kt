package com.wantobeme.lira.viewmodel.petugas

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wantobeme.lira.model.SirkulasiAnggota
import com.wantobeme.lira.repository.SirkulasiRepository
import com.wantobeme.lira.views.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


// get data from data class SirkulasiAnggota
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val sirkulasiRepository: SirkulasiRepository
): ViewModel(){

    init {
        getAnggotaList()
    }

    private val _anggotaResponse = MutableStateFlow<Resource<List<SirkulasiAnggota>>?>(null)
    val anggotaResponse: StateFlow<Resource<List<SirkulasiAnggota>>?> = _anggotaResponse

    fun getAnggotaList() = viewModelScope.launch {
        delay(1000)
        _anggotaResponse.value = Resource.Loading
        val result = sirkulasiRepository.getAnggota()
        _anggotaResponse.value = result
        Log.i("Dashboard Result", "$result")
        Log.i("Dashboard State", "${_anggotaResponse.value}")
    }


}