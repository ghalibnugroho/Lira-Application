package com.wantobeme.lira.repository

import com.wantobeme.lira.model.*
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
                if(it.tanggalPinjam.contains(" ")){
                    val arr = Pattern.compile(" ").split(it.tanggalPinjam)
                    it.tanggalPinjam = arr[0]
                }
                if(it.tanggalDikembalikan==null){
                    it.tanggalDikembalikan = "-"
                }else{
                    if(it.tanggalDikembalikan?.contains(" ") == true){
                        val arr = Pattern.compile(" ").split(it.tanggalDikembalikan)
                        it.tanggalDikembalikan = arr[0]
                    }
                }

                if(it.tanggalBatasPinjam.contains(" ")){
                    val arr = Pattern.compile(" ").split(it.tanggalBatasPinjam)
                    it.tanggalBatasPinjam = arr[0]
                }
            }
            Resource.Success(result = result)
        }catch (exception: Exception){
            Resource.Failure(exception = exception)
        }
    }

    suspend fun pinjamBuku(collectionId: String, memberNo: String): Resource<StatusMessage>{
        return try{
            val result = api.pinjamBuku(collectionId, memberNo)
            Resource.Success(result = result)
        }catch (exception: Exception){
            Resource.Failure(exception)
        }
    }

//    suspend fun validasiPeminjaman(collectionId: String): Resource<StatusMessage>{
//        return try{
//            Resource.Success
//        }catch (exception: Exception){
//            Resource.Failure(exception)
//        }
//    }

    suspend fun abortPeminjaman(collectionloanId: String, collectionId: String): Resource<StatusMessage>{
        return try{
            val result = api.abortPeminjaman(collectionloanId, collectionId)
            Resource.Success(result)
        }catch (exception: Exception){
            Resource.Failure(exception)
        }
    }


}