package com.wantobeme.lira.repository

import com.wantobeme.lira.model.*
import com.wantobeme.lira.network.ApiServices
import com.wantobeme.lira.views.uiState.PelanggaranState
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
                if(it.tanggalDikembalikan==null){
                    it.tanggalDikembalikan = "-"
                }else{
                    it.tanggalDikembalikan
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

    suspend fun validasiPeminjaman(collectionLoanId: String): Resource<StatusMessage>{
        return try{
            val result = api.validasiPeminjaman(collectionLoanId)
            Resource.Success(result = result)
        }catch (exception: Exception){
            Resource.Failure(exception)
        }
    }

    suspend fun abortPeminjaman(collectionloanId: String): Resource<StatusMessage>{
        return try{
            val result = api.abortPeminjaman(collectionloanId)
            Resource.Success(result)
        }catch (exception: Exception){
            Resource.Failure(exception)
        }
    }

    suspend fun extendPeminjaman(collectionLoanId: String, collectionId: String): Resource<StatusMessage>{
        return try{
            val result = api.extendPeminjaman(collectionLoanId, collectionId)
            Resource.Success(result)
        }catch (exception: Exception){
            Resource.Failure(exception)
        }
    }

    suspend fun finishPeminjaman(collectionLoanId: String, collectionId: String): Resource<StatusMessage>{
        return try {
            val result = api.finishPeminjaman(collectionLoanId, collectionId)
            Resource.Success(result)
        }catch (exception: Exception){
            Resource.Failure(exception)
        }
    }

    suspend fun getAllPelanggaran(): Resource<RPelanggaran>{
        return try{
            val result = api.getAllPelanggaran()
            Resource.Success(result)
        }catch (exception: Exception){
            Resource.Failure(exception)
        }
    }

    suspend fun getPelanggaranAnggota(memberNo: String): Resource<RPelanggaran>{
        return try{
            val result = api.getPelanggaranByMemberNo(memberNo)
            Resource.Success(result)
        }catch (exception: Exception){
            Resource.Failure(exception)
        }
    }

    suspend fun addPelanggaran(memberNo: String?, loanId: String?, collectionId: String?, pelanggaranState: PelanggaranState): Resource<StatusMessage>{
        return try{
            val result = api.addPelanggaran(memberNo, loanId, collectionId, pelanggaranState.jenisPelanggaran, pelanggaranState.jumlahDenda)
            Resource.Success(result)
        }catch (exception: Exception){
            Resource.Failure(exception)
        }
    }

}