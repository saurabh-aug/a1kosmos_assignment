package com.sample.a1kosmos.data.repository


import com.sample.a1kosmos.data.db.AppDatabase
import com.sample.a1kosmos.data.model.user.UserData
import com.sample.a1kosmos.data.network.ApiServiceImp
import com.sample.a1kosmos.data.preferances.PreferenceProvider
import javax.inject.Inject


class UserRepository @Inject constructor(
    private val db: AppDatabase,
    private val api: ApiServiceImp,
    private val pref: PreferenceProvider
) {


    suspend fun getUserDataServer(page: Int, per_page: Int): UserData {
        lateinit var users: UserData
        val userData = db.userDAO().getAllUsers()
        lateinit var saveUser: UserData
        try {
            saveUser = pref.getSavePage()!!
        } catch (e: Exception) {
            e.toString()
        }
        if (userData.isNullOrEmpty() || saveUser.page < page) {
            users = api.getUserData(page, per_page)
            db.userDAO().saveAllUsers(users.data)
            pref.savePage(users)
            println(pref.getSavePage())
            return users
        } else {
            users = UserData(page,saveUser.per_page,saveUser.total,saveUser.total_pages,userData)
            return users
        }
    }

}