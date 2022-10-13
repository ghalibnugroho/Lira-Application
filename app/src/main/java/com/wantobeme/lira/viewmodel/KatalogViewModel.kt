package com.wantobeme.lira.viewmodel

import android.annotation.SuppressLint
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
class KatalogViewModel : ViewModel() {
    // backing properties Katalog Response
    private var _katalogResponse = MutableStateFlow<KatalogUiState>(KatalogUiState.Empty)
    var katalogResponse: StateFlow<KatalogUiState> = _katalogResponse
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
                _katalogResponse.value = KatalogUiState.Loaded(
                    KatalogUIModelApi(
                        title = katalogList.title,
                        status = katalogList.status,
                        message = katalogList.message,
                        data = katalogList.data.map {
                            KatalogUIModel(
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
                Log.d("fetchW", "${(_katalogResponse.value as KatalogUiState.Loaded).data}")
            }
            catch (e: Exception) {
                error(e)
            }
        }
    }

    fun error(e: Exception){
        _katalogResponse.value = KatalogUiState.Error(
            "Catch Block Message, ${e.message.toString()}"
        )
    }

    sealed class KatalogUiState {
        object Empty : KatalogUiState()
        object Loading : KatalogUiState()
        class Loaded(val data: KatalogUIModelApi) : KatalogUiState()
        class Error(val message: String) : KatalogUiState()
    }
    data class KatalogUIModelApi(
        val title: String = "",
        val status: String = "",
        val message: String = "",
        val data: List<KatalogUIModel> = listOf()
    )

    data class KatalogUIModel(
        val id: String,
        val bibid: String,
        val title: String,
        val author: String,
        val publishYear: String,
        val coverURL: String,
        val quantity: Int,
    )


}


