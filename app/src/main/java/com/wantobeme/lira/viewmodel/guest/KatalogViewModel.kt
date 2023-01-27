package com.wantobeme.lira.viewmodel.guest

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wantobeme.lira.LiraApp
import com.wantobeme.lira.model.Katalog
import com.wantobeme.lira.repository.AuthRepository
import com.wantobeme.lira.repository.KatalogRepository
import com.wantobeme.lira.views.anggota.AnggotaActivity
import com.wantobeme.lira.views.petugas.PetugasActivity
import com.wantobeme.lira.views.uiState.KatalogState
import com.wantobeme.lira.views.utils.Resource
import com.wantobeme.lira.views.utils.startNewActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class KatalogViewModel @Inject constructor(
    private val katalogRepository: KatalogRepository,
    private val authRepository: AuthRepository,
    private val context: LiraApp
): ViewModel() {

    private val _katalogResponse = MutableStateFlow<Resource<List<Katalog>>?>(null)
    val katalogResponse: StateFlow<Resource<List<Katalog>>?> = _katalogResponse

    var searchList by mutableStateOf(KatalogState())

    // this function exist because katalogscreen being first destination
    fun jumpActivity() = viewModelScope.launch {
        authRepository.getDataUser().collect(){ preferences ->
            if (preferences != null) {
                if(preferences.role == 1){
                    context.startNewActivity(PetugasActivity::class.java)
                }else if(preferences.role == 2){
                    context.startNewActivity(AnggotaActivity::class.java)
                }
            }
        }
    }

    fun getKatalogList() = viewModelScope.launch {
        _katalogResponse.value = Resource.Loading
        val result = katalogRepository.getKatalog()
        _katalogResponse.value = result
        Log.i("Katalog Result", "${result}")
        Log.i("Katalog State", "${_katalogResponse.value}")
    }

    fun searchKatalogList(param: String) = viewModelScope.launch {
        searchList = KatalogState(isLoading = true)
        val data : Resource<List<Katalog>>
        try{
            val result = katalogRepository.searchKatalogsList(param)
            data = result
//            val result = katalogRepository.searchKatalogsList(param)
            when(data){
                is Resource.Failure -> {
                    searchList = KatalogState(error = "Terjadi kesalahan jaringan")
                }
                is Resource.Success -> {
                    data.result.let {
                        delay(1000)
                        searchList = KatalogState(data = it)
                    }
                } 
            }
        }catch (exception: Exception){
            searchList = KatalogState(error = "Buku tidak ada")
        }
    }
}






