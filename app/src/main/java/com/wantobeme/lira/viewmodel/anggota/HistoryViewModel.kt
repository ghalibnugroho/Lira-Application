package com.wantobeme.lira.viewmodel.anggota

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wantobeme.lira.model.Guest
import com.wantobeme.lira.model.SirkulasiLoan
import com.wantobeme.lira.model.SirkulasiLoanItems
import com.wantobeme.lira.repository.AuthRepository
import com.wantobeme.lira.repository.SirkulasiRepository
import com.wantobeme.lira.views.anggota.AnggotaActivity
import com.wantobeme.lira.views.petugas.PetugasActivity
import com.wantobeme.lira.views.utils.Resource
import com.wantobeme.lira.views.utils.startNewActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sirkulasiRepository: SirkulasiRepository
): ViewModel(){

    val _historyLoan = MutableStateFlow<Resource<List<SirkulasiLoan>>?>(null)
    val historyLoan: StateFlow<Resource<List<SirkulasiLoan>>?> = _historyLoan

    val _historyLoanItems = MutableStateFlow<Resource<List<SirkulasiLoanItems>>?>(null)
    val historyLoanItems: StateFlow<Resource<List<SirkulasiLoanItems>>?> = _historyLoanItems

    var myMemberNo by mutableStateOf(Guest())

    fun getMemberNo() : Guest{
        viewModelScope.launch {
            authRepository.getDataUser().collect(){ preferences ->
                if (preferences != null) {
                    myMemberNo = preferences
                }
            }
        }
        return myMemberNo
    }

    fun getHistoryLoan(memberNo: String?) = viewModelScope.launch {
        _historyLoan.value = Resource.Loading
        delay(1000)
        val result = sirkulasiRepository.getCollectionLoansAnggota(memberNo!!)
        _historyLoan.value = result
        Log.i("HistoryLoan Result", "$result")
        Log.i("HistoryLoan State", "${_historyLoan.value}")
    }

    fun getHistoryLoanItems(collectionLoanId: String) = viewModelScope.launch{
        _historyLoanItems.value = Resource.Loading
        delay(700)
        val result = sirkulasiRepository.getCollectionLoanItemsAnggota(collectionLoanId)
        _historyLoanItems.value = result
    }

}