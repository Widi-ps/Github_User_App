package com.example.githubuserapp3.data.local

import androidx.room.*
import com.example.githubuserapp3.model.User

@Dao
interface UserDao {@Query("SELECT * FROM user ORDER BY username ASC")
suspend fun getFavoriteListUser(): List<User>

    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun getFavoriteDetailUser(username: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteUser(user: User)

    @Delete
    suspend fun deleteFavoriteUser(user: User)
}