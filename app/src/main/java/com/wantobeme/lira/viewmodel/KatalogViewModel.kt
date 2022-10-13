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
    private var _katalogResponse = mutableStateOf<KatalogUiState>(KatalogUiState.Empty)
    var katalogResponse: State<KatalogUiState> = _katalogResponse
    // backing properties Katalog ErrorMessage
    var errorMessage: String by mutableStateOf("")

    companion object{
        val apiService = RetroAPI.getInstance()
    }

    init{
        getKatalogCoverList()
    }

    fun getKatalogCoverList() {
        _katalogResponse.value = KatalogUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val katalogList = apiService.getKatalog()
                Log.d("KatalogResponse", "${katalogList}")
                _katalogResponse.value = KatalogUiState.Loaded(
                    RKatalog(
                        title = katalogList.title,
                        status = katalogList.status,
                        message = katalogList.message,
                        data = katalogList.data.map {
                            KatalogCover(
                                id = it.id,
                                bibid = it.bibid,
                                title = it.title,
                                author = it.author,
                                publishYear = it.publishYear,
                                coverURL = it.coverURL,
                                quantity = it.quantity
                            )
                        }
                    )
                )
                Log.d("KatalogResponseUI", "${_katalogResponse.value}")
//                _katalogResponse = katalogList.data
//                Log.i("Retrofit Response", "${_katalogResponse}")
//                Log.i("Retrofit Response", "UI ${katalogResponse}")
            }
            catch (e: Exception) {
                _katalogResponse.value = KatalogUiState.Error(
                    "Something Wrong inside, Catch Block Message :)"
                )
//                errorMessage = e.message.toString()
//                Log.e("Retrofit.Response:gagal", "KatalogVM ${errorMessage}")
            }
        }
    }


    sealed class KatalogUiState {
        object Empty : KatalogUiState()
        object Loading : KatalogUiState()
        class Loaded(val data: RKatalog) : KatalogUiState()
        class Error(val message: String) : KatalogUiState()
    }



}


