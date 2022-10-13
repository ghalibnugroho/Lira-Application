package com.wantobeme.lira.repository

import com.wantobeme.lira.model.RKatalog
import com.wantobeme.lira.network.ApiServices
import javax.inject.Inject

class MainRepository @Inject constructor(private val api:ApiServices) {


    suspend fun getKatalog():Resource<RKatalog> {
        return try{
            val result = api.getKatalog()
            Resource.Success(data = result)
        }catch (e: Exception){
            Resource.Error(message = e.message.toString())
        }
    }


}