package com.sample.a1kosmos.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sample.a1kosmos.data.model.form.UserForm
import com.sample.a1kosmos.data.model.user.User

@Database(entities = [User::class, UserForm::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDAO(): UserDAO
    abstract fun userFormDAO(): FormDAO

}

