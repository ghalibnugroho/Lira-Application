package com.wantobeme.lira.viewmodel.petugas

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dsc.form_builder.FormState
import com.dsc.form_builder.TextFieldState
import com.dsc.form_builder.Validators
import com.wantobeme.lira.model.Koleksi
import com.wantobeme.lira.model.KoleksiOperation
import com.wantobeme.lira.repository.KatalogRepository
import com.wantobeme.lira.views.uiState.KoleksiState
import com.wantobeme.lira.views.utils.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class KoleksiViewModel @AssistedInject constructor(
    private val katalogRepository: KatalogRepository,
    @Assisted private val bookId: String?
): ViewModel(){

    private val _koleksiKatalog = MutableStateFlow<Resource<List<Koleksi>>?>(null)
    val koleksiKatalog: StateFlow<Resource<List<Koleksi>>?> = _koleksiKatalog

    private val _addKoleksiFlow = MutableStateFlow<Resource<KoleksiOperation>?>(null)
    val addKoleksiFlow: StateFlow<Resource<KoleksiOperation>?> = _addKoleksiFlow

    private val _deleteKoleksiFlow = MutableStateFlow<Resource<KoleksiOperation>?>(null)
    val deleteKoleksiFlow: StateFlow<Resource<KoleksiOperation>?> = _deleteKoleksiFlow

    val addKoleksiFormState = FormState(
        fields = listOf(
            TextFieldState(
                name = "nomorQRCode",
                validators = listOf(
                    Validators.Required()
                )
            ),
            TextFieldState(
                name = "nomorKoleksi",
                validators = listOf(
                    Validators.Required()
                )
            )
        )
    )

    init {
        getKoleksiKatalog(bookId)
    }

    @AssistedFactory
    interface Factory {
        fun create(bookId: String?): KoleksiViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideKoleksiViewModelFactory(assistedFactory: Factory, bookId: String?): ViewModelProvider.Factory =
            object: ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return assistedFactory.create(bookId) as T
                }
            }
    }

    fun getKoleksiKatalog(id: String?) = viewModelScope.launch {
        _koleksiKatalog.value = Resource.Loading
        delay(1000)
        val result = katalogRepository.getKoleksiKatalog(id!!)
        _koleksiKatalog.value = result
        Log.i("Koleksi Result", "${_koleksiKatalog.value}")
    }

    fun addKoleksiKatalog(katalogId: String?, koleksiState: KoleksiState) = viewModelScope.launch{
        _addKoleksiFlow.value = Resource.Loading
        delay(1000)
        val result = katalogRepository.addKatalogCollection(katalogId!!,koleksiState)
        _addKoleksiFlow.value = result
//        getKoleksiKatalog(id)
        Log.i("Tambah Koleksi Result", "${_addKoleksiFlow.value}")
    }

    fun deleteKoleksiKatalog(koleksiId: String) = viewModelScope.launch {
        _deleteKoleksiFlow.value = Resource.Loading
        delay(1000)
        val result = katalogRepository.deleteKatalogColletion(koleksiId)
        _deleteKoleksiFlow.value = result
//        getKoleksiKatalog(getVMParameter())
        Log.i("Delete Koleksi Result", "${_deleteKoleksiFlow.value}")
    }

    fun getVMParameter(): String?{
        return bookId
    }

}