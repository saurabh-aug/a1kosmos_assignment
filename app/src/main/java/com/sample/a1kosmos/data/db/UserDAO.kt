package com.sample.a1kosmos.data.db

import androidx.room.*
import com.sample.a1kosmos.data.model.user.User

@Dao
interface UserDAO {


//    suspend fun insertUser(user: User): Long

    @Update
    suspend fun updateUser(user: User): Int

    @Delete
    suspend fun deleteUser(user: User): Int

    @Query("DELETE FROM user_data_table")
    suspend fun deleteAllUsers(): Int

    @Query("SELECT * FROM user_data_table") // ORDER BY id DESC"")
    fun getAllUsers(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllUsers(user: List<User>)
}