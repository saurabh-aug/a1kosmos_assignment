package com.sample.a1kosmos.data.model.user

data class UserData(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    var data: List<User>,
)









