package com.wantobeme.lira.viewmodel.anggota

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wantobeme.lira.model.*
import com.wantobeme.lira.repository.AuthRepository
import com.wantobeme.lira.repository.KatalogRepository
import com.wantobeme.lira.repository.PresensiRepository
import com.wantobeme.lira.repository.SirkulasiRepository
import com.wantobeme.lira.views.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QRScanViewModel @Inject constructor(
    private val katalogRepository: KatalogRepository,
    private val authRepository: AuthRepository,
    private val sirkulasiRepository: SirkulasiRepository,
    private val presensiRepository: PresensiRepository
): ViewModel(){
    // load data when scanning
    private val _qrScan = MutableStateFlow<Resource<Loaning>?>(null)
    val qrScan: StateFlow<Resource<Loaning>?> = _qrScan

    // pressing accept button afterscan response
    private val _afterScan= MutableStateFlow<Resource<StatusMessage>?>(null)
    val afterScan: StateFlow<Resource<StatusMessage>?> = _afterScan

    // presensi/attendace response
    private val _presensiResponse= MutableStateFlow<Resource<StatusMessage>?>(null)
    val presensiResponse: StateFlow<Resource<StatusMessage>?> = _presensiResponse

    var myMemberNo by mutableStateOf(Guest())

    init{
        getMemberNo()
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

    fun qrCheck(qrCode: String?) = viewModelScope.launch {
        _qrScan.value = Resource.Loading
        delay(500)
        val result = katalogRepository.getKatalogByKodeQR(qrCode!!)
        _qrScan.value = result
    }

    fun pinjamBuku(collectionId: String, memberNo: String)=viewModelScope.launch {
        _afterScan.value = Resource.Loading
        delay(500)
        val result = sirkulasiRepository.pinjamBuku(collectionId,memberNo)
        _afterScan.value = result
    }

    fun presensiAnggota(memberNo: String)=viewModelScope.launch {
        _presensiResponse.value = Resource.Loading
        delay(2000)
        val result = presensiRepository.presensiAnggota(memberNo)
        _presensiResponse.value = result
    }

}