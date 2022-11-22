package com.wantobeme.lira.viewmodel.petugas

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.wantobeme.lira.model.SirkulasiLoan
import com.wantobeme.lira.model.SirkulasiLoanItems
import com.wantobeme.lira.repository.AuthRepository
import com.wantobeme.lira.repository.SirkulasiRepository
import com.wantobeme.lira.views.utils.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SirkulasiLoanItemsViewModel @AssistedInject constructor(
    private val sirkulasiRepository: SirkulasiRepository,
    private val authRepository: AuthRepository,
    @Assisted private val collectionId: String?
): ViewModel(){

    private val _sirkulasiLoanItemsResponse = MutableStateFlow<Resource<List<SirkulasiLoanItems>>?>(null)
    val sirkulasiLoanItemsResponse: StateFlow<Resource<List<SirkulasiLoanItems>>?> = _sirkulasiLoanItemsResponse

    init {
        getSirkulasiLoanItems(collectionId)
    }

    @AssistedFactory
    interface Factory {
        fun create(collectionId: String?): SirkulasiLoanItemsViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideSirkulasiLoanItemsViewModelFactory(assistedFactory: SirkulasiLoanItemsViewModel.Factory, collectionId: String?): ViewModelProvider.Factory =
            object: ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return assistedFactory.create(collectionId) as T
                }
            }
    }

    fun getSirkulasiLoanItems(collectionId: String?) = viewModelScope.launch {
        _sirkulasiLoanItemsResponse.value = Resource.Loading
        val result = sirkulasiRepository.getCollectionLoanItemsAnggota(collectionId!!)
        _sirkulasiLoanItemsResponse.value = result
        Log.i("SirkulasiLoan Result", "$result")
        Log.i("SirkulasiLoan State", "${_sirkulasiLoanItemsResponse.value}")
    }
}