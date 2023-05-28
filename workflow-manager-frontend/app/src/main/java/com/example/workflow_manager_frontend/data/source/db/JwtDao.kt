package com.example.workflow_manager_frontend.data.source.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.workflow_manager_frontend.domain.Jwt

@Dao
interface JwtDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveToken(token: Jwt)

    @Query("SELECT * FROM jwt_token WHERE id = 1")
    fun getToken(): Jwt?

    @Query("DELETE FROM jwt_token")
    fun deleteToken()
}