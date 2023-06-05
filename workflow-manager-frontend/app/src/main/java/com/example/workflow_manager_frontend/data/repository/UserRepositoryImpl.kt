package com.example.workflow_manager_frontend.data.repository

import android.util.Log
import com.example.workflow_manager_frontend.data.source.network.RetrofitInstance
import com.example.workflow_manager_frontend.domain.Customer
import java.io.IOException

class UserRepositoryImpl : UserRepository {
    private val tag ="UserRepository"

    override suspend fun getPrincipal(): Customer? {
        try{
            val response = RetrofitInstance.api.getPrincipal()
            if(! response.isSuccessful) {
                Log.e(tag, response.toString())
                return null
            }
            return response.body()
        }
        catch (e: IOException) {
            Log.e(tag, e.toString())
        }
        return null
    }
}