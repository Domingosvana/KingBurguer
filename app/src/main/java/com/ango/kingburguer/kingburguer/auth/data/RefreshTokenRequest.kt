package com.ango.kingburguer.kingburguer.auth.data

import com.google.gson.annotations.SerializedName

// estes dados vem da api
data class RefreshTokenRequest(
    @SerializedName("refresh_token")
    val refreshToken:String,
)