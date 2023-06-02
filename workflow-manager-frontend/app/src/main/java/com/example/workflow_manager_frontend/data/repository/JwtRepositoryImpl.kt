package com.example.workflow_manager_frontend.data.repository

import android.util.Log
import com.example.workflow_manager_frontend.data.source.db.JwtDao
import com.example.workflow_manager_frontend.data.source.network.RetrofitInstance
import com.example.workflow_manager_frontend.domain.Jwt
import com.example.workflow_manager_frontend.domain.request.AuthenticationRequest
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JwtRepositoryImpl @Inject constructor(
    private val jwtDao: JwtDao
) : JwtRepository

{
    private val tag = "JwtRepository"
    override suspend fun getJwt(): Jwt? {
        return jwtDao.getToken()
    }

    override suspend fun insertJwt(jwt: Jwt) {
        jwt.id= 1
        return jwtDao.saveToken(jwt)
    }

    override suspend fun deleteJwt() {
        return jwtDao.deleteToken()
    }

    override suspend fun getJwtFromRemote(authenticationRequest: AuthenticationRequest): Jwt? {
        try {
            val response = RetrofitInstance.api.login(authenticationRequest)
            if (!response.isSuccessful) {
                Log.e(tag, response.toString())
                return null;
            }

            return response.body()
        } catch(e: IOException) {
            Log.e(tag, e.toString())
        }
        return null
    }
}