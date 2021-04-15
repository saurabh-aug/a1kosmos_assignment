package com.sample.a1kosmos.data.repository


import com.sample.a1kosmos.data.db.AppDatabase
import com.sample.a1kosmos.data.model.form.UserForm
import javax.inject.Inject


class FormRepository @Inject constructor(private val db: AppDatabase) {

    suspend fun saveUsers(user: UserForm) {
        db.userFormDAO().saveUsers(user)
    }

    val user = db.userFormDAO().getAllUsers()

}