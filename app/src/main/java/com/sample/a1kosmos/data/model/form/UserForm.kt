package com.sample.a1kosmos.data.model.form

import androidx.room.ColumnInfo
import androidx.room.ColumnInfo.*
import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_USER_ID = 0

@Entity()
data class UserForm(
    val id: Int = 0,
    var first_name: String? = null,
    var last_name: String? = null,
    @ColumnInfo(typeAffinity = BLOB) var avatar: ByteArray? = null,
    var uri: String? = null,

    ) {
    @PrimaryKey(autoGenerate = false)
    var uid: Int = CURRENT_USER_ID
}
