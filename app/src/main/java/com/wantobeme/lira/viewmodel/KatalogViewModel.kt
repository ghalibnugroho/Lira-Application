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
import com.wantobeme.lira.repository.MainRepository
import com.wantobeme.lira.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KatalogViewModel @Inject constructor(private val mainRepository: MainRepository): ViewModel() {
    // backing properties Katalog Response
//    private var _katalogResponse by mutableStateOf(listOf<KatalogCover>())
//    var katalogResponse: List<KatalogCover> = emptyList()
//        get() = _katalogResponse
//
//    var errorMessage: String by mutableStateOf("")
//
//    companion object{
//        val apiService = RetroAPI.getInstance()
//    }


//    fun getKatalogCoverList() {
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val katalogList = apiService.getKatalog()
//                _katalogResponse = katalogList.data
//                Log.i("Retrofit Response", "${_katalogResponse}")
//                Log.i("Retrofit Response", "UI ${katalogResponse}")
//            }
//            catch (e: Exception) {
//                errorMessage = e.message.toString()
//                Log.e("Retrofit.Response:gagal", "KatalogVM ${errorMessage}")
//            }
//        }
//    }

    fun getKatalogList(){
        viewModelScope.launch {
//            list = KatalogState(isLoading = true)
            try{
                val result = mainRepository.getKatalog()
                when(result){
                    is Resource.Error->{
                        list = KatalogState(error = "Something Error, vm try: {block: Resource}")
                        Log.i("ViewModel Report", "Resource.Error try block")
                    }
                    is Resource.Success->{
                        result.response?.data?.let {
                            list = KatalogState(data = it)
                        }
                    }
                }
            }catch (e: Exception){
                list = KatalogState(error = "Something Error, vm catch: {block: Resource}")
                Log.i("ViewModel Report", "Resource.Error catch block")
            }
        }
    }
    var list by mutableStateOf(KatalogState())

    data class KatalogState(
        val isLoading:Boolean=false,
        val data:List<KatalogCover> = emptyList(),
        val error:String=""
    )


}


