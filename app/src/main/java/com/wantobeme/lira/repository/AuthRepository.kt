package com.wantobeme.lira.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.auth0.android.jwt.JWT
import com.wantobeme.lira.model.Guest
import com.wantobeme.lira.model.GuestRegistrasi
import com.wantobeme.lira.network.ApiServices
import com.wantobeme.lira.views.uiState.RegistrasiState
import com.wantobeme.lira.views.utils.Resource
import kotlinx.coroutines.flow.map
import javax.inject.Inject

const val falseToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZGVudGl0YXMiOiIiLCJlbWFpbCI6IiIsInJvbGUiOjB9.QLdoAgUf2rfE-aWXHp8SwMYm3Px2tA9DhKCdfFZgekE"

class AuthRepository @Inject constructor(
    private val api: ApiServices,
    private val context: Context
) {

    companion object{
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "userData")
        val USER_IDENTITAS_KEY = stringPreferencesKey("user_identitas")
        val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        val USER_ROLE_KEY = intPreferencesKey("user_role")
    }

    suspend fun login(email: String, password: String): Resource<Guest> {
        return try{
            val response = api.login(email, password)
            val jwt = if(response.token!=null) JWT(response.token) else JWT(falseToken)
            Log.i("JWT Token", "$jwt")
            val data = if(response.token!=null) Guest(
                identitas = jwt.getClaim("identitas").asString()!!,
                email = jwt.getClaim("email").asString()!!,
                role = jwt.getClaim("role").asInt()!!
            ) else Guest(
                identitas = "",
                email = "",
                role = 0
            )
            saveDataUser(data)
            Log.i("JWT Data", "${data}")
            Resource.Success(result = data)
        }catch (exception: Exception){
            Resource.Failure(exception = exception)
        }
    }
    /*
        saveDatauser to DataStore \\ if want to delete all preferences use preferences.clear()
     */
    suspend fun saveDataUser(guest: Guest){
        context.dataStore.edit { preferences ->
            preferences[USER_IDENTITAS_KEY] = guest.identitas
            preferences[USER_EMAIL_KEY] = guest.email
            preferences[USER_ROLE_KEY] = guest.role
        }
    }

    fun getDataUser() = context.dataStore.data.map { preferences ->
        Guest(
            identitas = preferences[USER_IDENTITAS_KEY]!!,
            email = preferences[USER_EMAIL_KEY]!!,
            role = preferences[USER_ROLE_KEY]!!
        )
    }

    suspend fun registrasi(registrasiState: RegistrasiState): Resource<GuestRegistrasi>{
        return try{
            val response = api.registrasi(
                email = registrasiState.email,
                password = registrasiState.password,
                nameLengkap = registrasiState.namaLengkap,
                jenisIdentitas = registrasiState.jenisIdentitas,
                nomorIdentitas = registrasiState.nomorIdentitas,
                alamatLengkap = registrasiState.alamatLengkap,
                jenisKelamin = registrasiState.jenisKelamin,
                no_hp = registrasiState.no_hp,
                institusi = registrasiState.institusi
            )
            Resource.Success(result = response)
        }catch (exception: Exception){
            Resource.Failure(exception = exception)
        }
    }

    suspend fun logout(){
        context.dataStore.edit { preferences ->
            preferences[USER_IDENTITAS_KEY] = ""
            preferences[USER_EMAIL_KEY] = ""
            preferences[USER_ROLE_KEY] = 0
        }
    }

}