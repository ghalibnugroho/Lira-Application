package com.wantobeme.lira.viewmodel.petugas

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.wantobeme.lira.model.KatalogDetail
import com.wantobeme.lira.model.SirkulasiLoan
import com.wantobeme.lira.repository.SirkulasiRepository
import com.wantobeme.lira.viewmodel.guest.KatalogDetailViewModel
import com.wantobeme.lira.views.utils.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class SirkulasiLoanViewModel @AssistedInject constructor(
    private val sirkulasiRepository: SirkulasiRepository,
    @Assisted private val memberNo: String?
): ViewModel(){

    private val _sirkulasiLoanResponse = MutableStateFlow<Resource<List<SirkulasiLoan>>?>(null)
    val sirkulasiLoanResponse: StateFlow<Resource<List<SirkulasiLoan>>?> = _sirkulasiLoanResponse

    init {
        getSirkulasiLoanMember(memberNo)
    }

    @AssistedFactory
    interface Factory {
        fun create(memberNo: String?): SirkulasiLoanViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideSirkulasiLoanViewModelFactory(assistedFactory: SirkulasiLoanViewModel.Factory, memberNo: String?): ViewModelProvider.Factory =
            object: ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return assistedFactory.create(memberNo) as T
                }
            }
    }

    fun getSirkulasiLoanMember(memberNo: String?) = viewModelScope.launch {
        _sirkulasiLoanResponse.value = Resource.Loading
//        delay(1000)
        val result = sirkulasiRepository.getCollectionLoansAnggota(memberNo!!)
        _sirkulasiLoanResponse.value = result
        Log.i("SirkulasiLoan Result", "$result")
        Log.i("SirkulasiLoan State", "${_sirkulasiLoanResponse.value}")
    }

}