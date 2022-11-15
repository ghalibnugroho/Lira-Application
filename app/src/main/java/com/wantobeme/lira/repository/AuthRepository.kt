package com.wantobeme.lira.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.auth0.android.jwt.JWT
import com.wantobeme.lira.model.User
import com.wantobeme.lira.model.UserToken
import com.wantobeme.lira.network.ApiServices
import com.wantobeme.lira.views.uiState.RegistrasiState
import com.wantobeme.lira.views.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

const val falseToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZGVudGl0YXMiOiIiLCJlbWFpbCI6IiIsInJvbGUiOjB9.QLdoAgUf2rfE-aWXHp8SwMYm3Px2tA9DhKCdfFZgekE"

class AuthRepository @Inject constructor(
    private val api: ApiServices
) {

    companion object{
        val identitas = stringPreferencesKey("IDENTITAS")
        val email = stringPreferencesKey("EMAIL")
        val role = stringPreferencesKey("ROLE")
    }

    suspend fun login(email: String, password: String): Resource<User> {
        return try{
            val response = api.login(email, password)
            val jwt = if(response.token!=null) JWT(response.token) else JWT(falseToken)
            Log.i("JWT Token", "$jwt")
            val data = if(response.token!=null) User(
                identitas = jwt.getClaim("identitas").asString()!!,
                email = jwt.getClaim("email").asString()!!,
                role = jwt.getClaim("role").asInt()!!
            ) else User(
                identitas = "",
                email = "",
                role = 0
            )
            Log.i("JWT Data", "${data}")
            Resource.Success(result = data)
        }catch (exception: Exception){
            Resource.Failure(exception = exception)
        }
    }



    suspend fun registrasi(registrasiState: RegistrasiState){

    }

}