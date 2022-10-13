package com.wantobeme.lira.network

class ApiHelper{

    companion object{
        val apiService = RetroAPI.getInstance()
    }

    suspend fun getKatalog() = apiService.getKatalog()
}