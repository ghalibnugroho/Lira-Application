package com.wantobeme.lira.viewmodel.guest

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsc.form_builder.FormState
import com.dsc.form_builder.TextFieldState
import com.dsc.form_builder.Validators
import com.wantobeme.lira.model.Guest
import com.wantobeme.lira.model.User
import com.wantobeme.lira.model.UserToken
import com.wantobeme.lira.repository.AuthRepository
import com.wantobeme.lira.views.uiState.KatalogState
import com.wantobeme.lira.views.uiState.LoginState
import com.wantobeme.lira.views.uiState.RegistrasiState
//import com.wantobeme.lira.views.uiState.UserState
import com.wantobeme.lira.views.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel(){

    private val _userToken = MutableStateFlow<Resource<User>?>(null)
    val userToken: StateFlow<Resource<User>?> = _userToken

    private val _guestRegistrasi = MutableStateFlow<Resource<Guest>?>(null)
    val guestRegistrasi: StateFlow<Resource<Guest>?> = _guestRegistrasi

    // login
    val loginFormState = FormState(
        fields = listOf(
            TextFieldState(
                name = "email",
                validators = listOf(
                    Validators.Email(),
                    Validators.Required()
                )
            ),
            TextFieldState(
                name = "password",
                validators = listOf(
                    Validators.Required()
                )
            )
        )
    )

    val registrasiState = FormState(
        fields = listOf(

        )
    )

    fun login(loginState: LoginState) = viewModelScope.launch {
        _userToken.value = Resource.Loading
        val result = authRepository.login(loginState.email,loginState.password)
        Log.i("Login - AuthViewModel Result", "${result}")
        _userToken.value = result
    }

    fun registrasi(registrasiState: RegistrasiState) = viewModelScope.launch {

    }

}