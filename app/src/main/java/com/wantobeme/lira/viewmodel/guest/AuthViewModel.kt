package com.wantobeme.lira.viewmodel.guest

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsc.form_builder.ChoiceState
import com.dsc.form_builder.FormState
import com.dsc.form_builder.TextFieldState
import com.dsc.form_builder.Validators
import com.wantobeme.lira.model.Guest
import com.wantobeme.lira.model.GuestRegistrasi
import com.wantobeme.lira.repository.AuthRepository
import com.wantobeme.lira.views.uiState.LoginState
import com.wantobeme.lira.views.uiState.RegistrasiState
//import com.wantobeme.lira.views.uiState.UserState
import com.wantobeme.lira.views.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel(){

    private val _guestToken = MutableStateFlow<Resource<Guest>?>(null)
    val guestToken: StateFlow<Resource<Guest>?> = _guestToken

    val guestDataStore = mutableStateOf(Guest())

    private val _guestRegistrasi = MutableStateFlow<Resource<GuestRegistrasi>?>(null)
    val guestRegistrasi: StateFlow<Resource<GuestRegistrasi>?> = _guestRegistrasi

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
            ),
            TextFieldState(
                name = "nama_lengkap",
                validators = listOf(
                    Validators.Required()
                )
            ),
            ChoiceState(
                name = "jenis_identitas",
                validators = listOf(
                    Validators.Required()
                ),
            ),
            TextFieldState(
                name = "no_identitas",
                validators = listOf(
                    Validators.Required()
                )
            ),
            ChoiceState(
                name = "jenis_kelamin",
                validators = listOf(
                    Validators.Required()
                )
            ),
            TextFieldState(
                name = "alamat",
                validators = listOf(
                    Validators.Required()
                )
            ),
            TextFieldState(
                name = "no_hp",
                validators = listOf(
                    Validators.Phone(),
                    Validators.Required()
                )
            ),
            TextFieldState(
                name = "institusi",
                validators = listOf(
                    Validators.Required()
                )
            )
        )
    )

    fun login(loginState: LoginState) = viewModelScope.launch {
        _guestToken.value = Resource.Loading
        val result = authRepository.login(loginState.email,loginState.password)
        Log.i("Login - AuthViewModel Result", "${result}")
        Log.i("DataStore - User Data", "${authRepository.getDataUser()}")
        _guestToken.value = result
    }

    fun registrasi(registrasiState: RegistrasiState) = viewModelScope.launch {
        _guestRegistrasi.value = Resource.Loading
        val result = authRepository.registrasi(registrasiState)
        _guestRegistrasi.value = result
    }

    fun logout()= viewModelScope.launch{
        authRepository.logout()
    }

}