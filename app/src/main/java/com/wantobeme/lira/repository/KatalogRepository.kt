package com.wantobeme.lira.repository

import com.wantobeme.lira.model.*
import com.wantobeme.lira.network.ApiServices
import com.wantobeme.lira.views.uiState.KoleksiState
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

    suspend fun getKatalogByKodeQR(kodeQR: String): Resource<Loaning>{
        return try{
            val data = api.getKatalogByKodeQR(kodeQR)
            if (data.title.contains("/")){
                val arr = Pattern.compile("/").split(data.title)
                data.title = arr[0]
            }
            if(data.publishLocation.isBlank()) data.publishLocation = "-"
            Resource.Success(result = data)
        }catch (exception: Exception){
            Resource.Failure(exception = exception)
        }
    }

    suspend fun getKoleksiKatalog(id: String): Resource<List<Koleksi>>{
        return try{
            val data = api.getKoleksiKatalog(id).data
            Resource.Success(result = data)
        }catch (exception: Exception){
            Resource.Failure(exception)
        }
    }

    suspend fun addKatalogCollection(katalogId: String, koleksiState: KoleksiState): Resource<StatusMessage>{
        return try{
            val data = api.addCollection(katalogId, koleksiState.nomorQRCode, koleksiState.nomorKoleksi)
            Resource.Success(result = data)
        }catch (exception: Exception){
            Resource.Failure(exception)
        }
    }

    suspend fun deleteKatalogColletion(collectionId: String): Resource<StatusMessage>{
        return try{
            val data = api.deleteKoleksi(collectionId)
            Resource.Success(data)
        }catch (exception: Exception){
            Resource.Failure(exception)
        }
    }

    suspend fun getNomorQRCode(kodeQR: String): Resource<QRCode>{
        return try{
            val data = api.getKodeQR(kodeQR)
            Resource.Success(result = data)
        }catch (exception: Exception){
            Resource.Failure(exception)
        }
    }

}