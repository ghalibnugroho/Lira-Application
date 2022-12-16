package com.wantobeme.lira.viewmodel.petugas

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsc.form_builder.ChoiceState
import com.dsc.form_builder.FormState
import com.dsc.form_builder.TextFieldState
import com.dsc.form_builder.Validators
import com.wantobeme.lira.model.Guest
import com.wantobeme.lira.model.RPelanggaran
import com.wantobeme.lira.model.StatusMessage
import com.wantobeme.lira.repository.AuthRepository
import com.wantobeme.lira.repository.SirkulasiRepository
import com.wantobeme.lira.views.uiState.PelanggaranState
import com.wantobeme.lira.views.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PelanggaranViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sirkulasiRepository: SirkulasiRepository
): ViewModel(){

    private val _addPelanggaranFlow = MutableStateFlow<Resource<StatusMessage>?>(null)
    val addPelanggaranFlow: StateFlow<Resource<StatusMessage>?> = _addPelanggaranFlow

    private val _allPelanggaranFlow = MutableStateFlow<Resource<RPelanggaran>?>(null)
    val allPelanggaranFlow: StateFlow<Resource<RPelanggaran>?> = _allPelanggaranFlow

    private val _pelanggaranAnggotaFlow = MutableStateFlow<Resource<RPelanggaran>?>(null)
    val pelanggaranAnggotaFlow: StateFlow<Resource<RPelanggaran>?> = _pelanggaranAnggotaFlow

    var myMemberNo by mutableStateOf(Guest())

    val addPelanggaranFormState = FormState(
        fields = listOf(
            ChoiceState(
                name = "jenisPelanggaran",
                validators = listOf(
                    Validators.Required()
                ),
            ),
            TextFieldState(
                name = "jumlahDenda",
                validators = listOf(
                    Validators.Required()
                )
            )
        )
    )

    fun getAllPelanggaran() = viewModelScope.launch {
        _allPelanggaranFlow.value = Resource.Loading
        val result = sirkulasiRepository.getAllPelanggaran()
        _allPelanggaranFlow.value = result
        Log.i("Pelanggaran - ALL", "${_allPelanggaranFlow.value}")
    }

    fun getPelanggaranAnggota(memberNo: String) = viewModelScope.launch {
        _pelanggaranAnggotaFlow.value = Resource.Loading
        val result = sirkulasiRepository.getPelanggaranAnggota(memberNo)
        _pelanggaranAnggotaFlow.value = result
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



    fun addPelanggaran(memberNo: String?, loanId: String?, collectionId: String?, pelanggaranState: PelanggaranState) = viewModelScope.launch {
        _addPelanggaranFlow.value = Resource.Loading
        val result = sirkulasiRepository.addPelanggaran(memberNo, loanId, collectionId, pelanggaranState)
        _addPelanggaranFlow.value = result
    }
}