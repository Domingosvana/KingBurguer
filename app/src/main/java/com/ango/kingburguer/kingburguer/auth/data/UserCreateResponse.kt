package com.ango.kingburguer.kingburguer.auth.data

data class UserCreateResponse(
    val id: Int,
    val name: String,
    val email: String,
    val document: String,
    val birthday: String,
)
//   data class Error(val detail: String) : UserCreateResponse()

//    data class ErrorAuth(val detail: ErrorDetail) : UserCreateResponse()
