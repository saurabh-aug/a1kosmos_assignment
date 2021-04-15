package com.sample.a1kosmos.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sample.a1kosmos.data.model.form.UserForm
import com.sample.a1kosmos.data.model.user.User

@Dao
interface FormDAO {


    @Query("SELECT * FROM UserForm") // ORDER BY id DESC"")
    fun getAllUsers(): LiveData<UserForm>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUsers(user: UserForm)
}