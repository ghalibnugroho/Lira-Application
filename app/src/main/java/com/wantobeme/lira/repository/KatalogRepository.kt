package com.wantobeme.lira.repository

import com.wantobeme.lira.model.Katalog
import com.wantobeme.lira.model.KatalogDetail
import com.wantobeme.lira.network.ApiServices
import com.wantobeme.lira.views.uimodel.Resource
import javax.inject.Inject

class KatalogRepository @Inject constructor(
    private val api:ApiServices ) {

    suspend fun getKatalog(): Resource<List<Katalog>> {
        return try{
            val result = api.getKatalog().data
            Resource.Success(result = result)
        }catch (exception: Exception){
            Resource.Failure(exception = exception)
        }
    }

    suspend fun getKatalogDetail(id: String): Resource<KatalogDetail>{
        return try{
            val result = api.getDetailKatalog(id).data
            Resource.Success(result = result)
        }catch (exception: Exception){
            Resource.Failure(exception = exception)
        }
    }


}