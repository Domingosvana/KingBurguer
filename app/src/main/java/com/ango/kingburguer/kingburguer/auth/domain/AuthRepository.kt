package com.ango.kingburguer.kingburguer.auth.domain

import com.ango.kingburguer.kingburguer.auth.data.LoginRequest
import com.ango.kingburguer.kingburguer.auth.data.LoginResponse
import com.ango.kingburguer.kingburguer.core.data.ApiResult
import com.ango.kingburguer.kingburguer.auth.data.UserCreateResponse
import com.ango.kingburguer.kingburguer.auth.data.UserRequest
import com.ango.kingburguer.kingburguer.auth.data.RefreshTokenRequest
import com.ango.kingburguer.kingburguer.core.data.UserCredentisls

interface AuthRepository {

    suspend fun login(loginRequest: LoginRequest, keepLogged: Boolean): ApiResult<LoginResponse>

    suspend fun postUser(userRequest: UserRequest): ApiResult<UserCreateResponse>

    suspend fun refreshToken(request: RefreshTokenRequest):ApiResult<LoginResponse>


    suspend fun fetchInitialUserCredentials(): UserCredentisls

}