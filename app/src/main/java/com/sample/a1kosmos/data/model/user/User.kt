package com.sample.a1kosmos.data.model.user

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user_data_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val email: String,
    val first_name: String,
    val last_name: String,
    val avatar: String,
    )
