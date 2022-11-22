package com.wantobeme.lira.viewmodel.petugas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wantobeme.lira.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel(){

    fun logout()= viewModelScope.launch{
        authRepository.logout()
    }


}