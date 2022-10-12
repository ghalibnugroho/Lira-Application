package com.wantobeme.lira.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
    lateinit var katalogCoverListResponse: RKatalog
    lateinit var katalogDetailListResponse: RKatalogDetail
    var katalogCoverResponse: List<KatalogCover> by mutableStateOf(listOf())
    var katalogDetailResponse: List<KatalogDetail> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")

    companion object{
        val apiService = RetroAPI.getInstance()
    }

//    init{
//        getKatalogCoverList()
//    }

    fun getKatalogCoverList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val katalogList = apiService.getKatalog()
                katalogCoverListResponse = katalogList
                katalogCoverResponse = katalogCoverListResponse.data
                Log.i("Retrofit Response List","${katalogCoverListResponse}")
                Log.i("Retrofit Response", "${katalogCoverResponse}")
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e("Retrofit.Response:gagal", "KatalogVM ${errorMessage}")
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


