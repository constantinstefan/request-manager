package com.example.workflow_manager_frontend.data.source.network

import com.example.workflow_manager_frontend.data.repository.JwtRepository

object JwtRepositoryInstance {
    var instance : JwtRepository? = null


    fun setJwtRepository(jwtRepository: JwtRepository) {
        instance = jwtRepository
    }
}