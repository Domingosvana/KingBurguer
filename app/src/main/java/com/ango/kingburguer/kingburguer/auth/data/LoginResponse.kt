package com.ango.kingburguer.kingburguer.auth.data

import com.google.gson.annotations.SerializedName


//devemos criar um tipo que aceita um tipo generico
//para success e para error



data class LoginResponse (
    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("refresh_token")
    val refreshToken: String,

    @SerializedName("token_type")
    val tokenType: String,

    @SerializedName("expires_seconds")
    val expiresSeconds: Double,
)

