package com.wantobeme.lira.viewmodel.petugas

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.wantobeme.lira.model.Koleksi
import com.wantobeme.lira.model.QRCode
import com.wantobeme.lira.repository.KatalogRepository
import com.wantobeme.lira.views.utils.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QRViewModel @AssistedInject constructor(
    private val katalogRepository: KatalogRepository,
    @Assisted private val kodeQR: String?
): ViewModel(){

    init {
        getKodeQR(kodeQR)
    }

    private val _kodeQRFlow = MutableStateFlow<Resource<QRCode>?>(null)
    val kodeQRFlow: StateFlow<Resource<QRCode>?> = _kodeQRFlow

    @AssistedFactory
    interface Factory {
        fun create(kodeQR: String?): QRViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideQRViewModelFactory(assistedFactory: Factory, kodeQR: String?): ViewModelProvider.Factory =
            object: ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return assistedFactory.create(kodeQR) as T
                }
            }
    }

    fun getKodeQR(kodeQR: String?) = viewModelScope.launch{
        delay(500)
        _kodeQRFlow.value = Resource.Loading
        val result = kodeQR?.let { katalogRepository.getNomorQRCode(it) }
        _kodeQRFlow.value = result
        Log.i("QR Result", "${_kodeQRFlow.value }}")
    }


}