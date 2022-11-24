package com.wantobeme.lira.viewmodel.petugas

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wantobeme.lira.model.Presensi
import com.wantobeme.lira.repository.PresensiRepository
import com.wantobeme.lira.views.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DaftarPresensiViewModel @Inject constructor(
    private val presensiRepository: PresensiRepository
): ViewModel(){

    private val _allPresensi = MutableStateFlow<Resource<List<Presensi>>?>(null)
    val allPresensi: StateFlow<Resource<List<Presensi>>?> = _allPresensi

    fun getAllPresensiAnggota() = viewModelScope.launch {
        _allPresensi.value = Resource.Loading
        val result = presensiRepository.getAllDaftarPresensi()
        _allPresensi.value = result
        Log.i("Presensi","${result}")
    }
}