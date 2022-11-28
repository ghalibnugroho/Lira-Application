package com.wantobeme.lira.views.anggota.qrscanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wantobeme.lira.model.QRCode
import com.wantobeme.lira.repository.KatalogRepository
import com.wantobeme.lira.views.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QRScanViewModel @Inject constructor(
    private val katalogRepository: KatalogRepository
): ViewModel(){

    private val _qrScan = MutableStateFlow<Resource<QRCode>?>(null)
    val qrScan: StateFlow<Resource<QRCode>?> = _qrScan

    fun qrCheck(qrCode: String?) = viewModelScope.launch {
        _qrScan.value = Resource.Loading
        val result = katalogRepository.getNomorQRCode(qrCode!!)
        _qrScan.value = result
    }

}