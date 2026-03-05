package com.ango.kingburguer.kingburguer.core.data

//Estes dados sao recebidos da API http 200k e salvos no dataStore
// Enviamos um put com gmail e o retorno e deste dados 200k
data class UserCredentisls(
    val accessToken:String="",
    val refreshToken:String="",
    val expiresTimestamp:Long = 0,
    val tokenType:String="",
)