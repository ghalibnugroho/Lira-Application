package com.wantobeme.lira.viewmodel.guest

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.wantobeme.lira.model.KatalogDetail
import com.wantobeme.lira.repository.KatalogRepository
import com.wantobeme.lira.views.utils.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class KatalogDetailViewModel @AssistedInject constructor(
    private val katalogRepository: KatalogRepository,
    @Assisted private val bookId: String?
): ViewModel() {

    private val _katalogDetailResponse = MutableStateFlow<Resource<KatalogDetail>?>(null)
    val katalogDetailResponse: StateFlow<Resource<KatalogDetail>?> = _katalogDetailResponse

    init {
        getKatalogDetail(bookId)
    }

    @AssistedFactory
    interface Factory {
        fun create(bookId: String?): KatalogDetailViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideKatalogDetailViewModelFactory(assistedFactory: Factory, bookId: String?): ViewModelProvider.Factory =
            object: ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return assistedFactory.create(bookId) as T
                }
            }
    }

    fun getKatalogDetail(id: String?) = viewModelScope.launch {
        _katalogDetailResponse.value = Resource.Loading
        val result = katalogRepository.getKatalogDetail(id!!)
        _katalogDetailResponse.value = result
        Log.i("Katalog Detail Result", "${result}")
        Log.i("Katalog Detail State", "${_katalogDetailResponse.value}")
    }
}