package com.wantobeme.lira.repository

import com.wantobeme.lira.model.Presensi
import com.wantobeme.lira.network.ApiServices
import com.wantobeme.lira.views.utils.Resource
import javax.inject.Inject

class PresensiRepository @Inject constructor(
    private val api: ApiServices
) {

    suspend fun getAllDaftarPresensi(): Resource<List<Presensi>>{
        return try {
            val result = api.getAllDaftarPresensi().data
            Resource.Success(result = result)
        }catch (exception: Exception){
            Resource.Failure(exception = exception)
        }
    }

    suspend fun getPresensiByNomorAnggota(memberNo: String): Resource<List<Presensi>>{
        return try {
            val result = api.getPresensiByNomorAnggota(memberNo).data
            Resource.Success(result = result)
        }catch (exception: Exception){
            Resource.Failure(exception = exception)
        }
    }

}