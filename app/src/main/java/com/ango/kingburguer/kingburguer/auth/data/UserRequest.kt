package com.ango.kingburguer.kingburguer.auth.data

data class UserRequest(
    val name:String,
    val email:String,
    val password:String,
    val confirmPassword:String,
    val document:String,
    val birthday:String


)