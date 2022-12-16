package com.wantobeme.lira.viewmodel.petugas

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wantobeme.lira.model.Guest
import com.wantobeme.lira.model.Presensi
import com.wantobeme.lira.model.RPresensi
import com.wantobeme.lira.repository.AuthRepository
import com.wantobeme.lira.repository.PresensiRepository
import com.wantobeme.lira.views.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DaftarPresensiViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val presensiRepository: PresensiRepository
): ViewModel(){

    var myMemberNo by mutableStateOf(Guest())

    private val _allPresensi = MutableStateFlow<Resource<RPresensi>?>(null)
    val allPresensi: StateFlow<Resource<RPresensi>?> = _allPresensi

    private val _presensiAnggota = MutableStateFlow<Resource<List<Presensi>>?>(null)
    val presensiAnggota: StateFlow<Resource<List<Presensi>>?> = _presensiAnggota

    fun getAllPresensiAnggota() = viewModelScope.launch {
        _allPresensi.value = Resource.Loading
        val result = presensiRepository.getAllDaftarPresensi()
        _allPresensi.value = result
        Log.i("ALL Presensi","${result}")
    }

    fun getPresensiAnggota(memberNo: String) = viewModelScope.launch{
        _presensiAnggota.value = Resource.Loading
        val result = presensiRepository.getPresensiByNomorAnggota(memberNo)
        _presensiAnggota.value = result
        Log.i("Presensi Anggota", "${result}")
    }

    fun getMemberNo() : Guest {
        viewModelScope.launch {
            authRepository.getDataUser().collect(){ preferences ->
                if (preferences != null) {
                    myMemberNo = preferences
                }
            }
        }
        return myMemberNo
    }
}