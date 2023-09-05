package com.example.requestapi.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.requestapi.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    suspend fun getUserByUsernameAndPassword(username: String, password: String): User?

    @Insert
    suspend fun insertUser(user: User)
}