package com.wantobeme.lira.viewmodel

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wantobeme.lira.model.KatalogCover
import com.wantobeme.lira.model.KatalogDetail
import com.wantobeme.lira.model.RKatalog
import com.wantobeme.lira.model.RKatalogDetail
import com.wantobeme.lira.network.RetroAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class KatalogViewModel : ViewModel() {
    // backing properties Katalog Response
    private var _katalogResponse by mutableStateOf(listOf<KatalogCover>())
    var katalogResponse: List<KatalogCover> = emptyList()
        get() = _katalogResponse
    // backing properties Katalog Loading
    private var _loadKatalog by mutableStateOf(false)
    var loadKatalog: Boolean = false
        get() = _loadKatalog
    // backing properties Katalog ErrorMessage
    var errorMessage: String by mutableStateOf("")

    companion object{
        val apiService = RetroAPI.getInstance()
    }

//    init{
//        getKatalogCoverList()
//    }

    fun getKatalogCoverList() {
        viewModelScope.launch(Dispatchers.IO) {
            _loadKatalog = true
            try {
                val katalogList = apiService.getKatalog()
                _katalogResponse = katalogList.data
                Log.i("Retrofit Response", "${_katalogResponse}")
                Log.i("Retrofit Response", "UI ${katalogResponse}")
                _loadKatalog = false
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e("Retrofit.Response:gagal", "KatalogVM ${errorMessage}")
                _loadKatalog = false
            }
        }
    }

//    fun getKatalogList(){
//        viewModelScope.launch(Dispatchers.IO) {
//            try{
//                val katalogList = apiService.getKatalog()
//                katalogCoverListResponse = katalogList
//                Resource.Success(data = katalogCoverListResponse)
//            }catch (e: Exception){
//                Resource.Error(message = e.message.toString())
//            }
//        }
//        viewModelScope.launch{
//            katalogState.value = KatalogState(isLoading = true)
//            try{
//
//            }catch (e: Exception){
//
//            }
//        }
//    }



}


