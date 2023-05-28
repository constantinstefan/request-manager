package com.example.workflow_manager_frontend.data.repository

import com.example.workflow_manager_frontend.domain.Jwt
import com.example.workflow_manager_frontend.domain.request.AuthenticationRequest

interface JwtRepository {

    suspend fun getJwt() : Jwt?

    suspend fun getJwtFromRemote(authenticationRequest: AuthenticationRequest) : Jwt?

    suspend fun insertJwt(jwt: Jwt)

    suspend fun deleteJwt()
}