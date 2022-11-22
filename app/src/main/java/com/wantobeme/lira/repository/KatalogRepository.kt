package com.wantobeme.lira.repository

import com.wantobeme.lira.model.Katalog
import com.wantobeme.lira.model.KatalogDetail
import com.wantobeme.lira.model.Koleksi
import com.wantobeme.lira.network.ApiServices
import com.wantobeme.lira.views.utils.Resource
import java.util.regex.Pattern
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

    suspend fun getKatalogDetail(id: String): Resource<KatalogDetail> {
        return try{
            val data = api.getDetailKatalog(id).data
            if (data.title.contains("/")){
                val arr = Pattern.compile("/").split(data.title)
                data.title = arr[0]
            }
            if(data.publishLocation.isBlank()) data.publishLocation = "-"
            if(data.isbn.isBlank()) data.isbn = "-"
            if(data.subject.isBlank()) data.subject = "-"
            Resource.Success(result = data)
        }catch (exception: Exception){
            Resource.Failure(exception = exception)
        }
    }

    suspend fun searchKatalogsList(param: String): Resource<List<Katalog>> {
        return try{
            val data = api.searchKatalogs(param).data
            Resource.Success(result = data)
        }catch (exception: Exception){
            Resource.Failure(exception = exception)
        }
    }

    suspend fun addKatalogCollection(koleksi: Koleksi){

    }

}