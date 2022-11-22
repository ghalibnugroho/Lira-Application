package com.wantobeme.lira.repository

import com.wantobeme.lira.model.SirkulasiAnggota
import com.wantobeme.lira.model.SirkulasiLoan
import com.wantobeme.lira.model.SirkulasiLoanItems
import com.wantobeme.lira.network.ApiServices
import com.wantobeme.lira.views.utils.Resource
import java.util.regex.Pattern
import javax.inject.Inject

class SirkulasiRepository @Inject constructor(
    private val api: ApiServices
){
    suspend fun getAnggota(): Resource<List<SirkulasiAnggota>> {
        return try {
            val result = api.getAnggota().data
            Resource.Success(result = result)
        }catch (exception: Exception){
            Resource.Failure(exception = exception)
        }
    }

    suspend fun getCollectionLoansAnggota(memberNo: String): Resource<List<SirkulasiLoan>>{
        return try{
            val result = api.getCollectionLoansAnggota(memberNo).data
            Resource.Success(result = result)
        }catch (exception: Exception){
            Resource.Failure(exception = exception)
        }
    }

    suspend fun getCollectionLoanItemsAnggota(collectionLoanId: String): Resource<List<SirkulasiLoanItems>>{
        return try{
            val result = api.getCollectionLoanItemsAnggota(collectionLoanId).data
            result.forEach {
                if (it.judul.contains("/")){
                    val arr = Pattern.compile("/").split(it.judul)
                    it.judul = arr[0]
                }
            }
            Resource.Success(result = result)
        }catch (exception: Exception){
            Resource.Failure(exception = exception)
        }
    }
}