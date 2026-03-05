package com.ango.kingburguer.kingburguer.core.domain

import android.util.Log
import com.ango.kingburguer.kingburguer.auth.data.RefreshTokenRequest
import com.ango.kingburguer.kingburguer.auth.domain.AuthRepository
import com.ango.kingburguer.kingburguer.core.data.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckSessionUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    operator fun invoke(): Flow<Boolean> = flow {
        with(repository.fetchInitialUserCredentials()) {
           val res =  when {
                accessToken.isEmpty() -> false // nunca logou = false = ir para tela de login
                System.currentTimeMillis() < expiresTimestamp -> true // está ativo (nao expirou) = tela principal
                else -> {
                    Log.d("SplashViewModel", "Token expired. try refresh token.")
                    val response = repository.refreshToken(RefreshTokenRequest(refreshToken))
                    when (response) {
                        is ApiResult.Success -> true
                        else -> false
                    }
                }
            }
            emit(res)

        }
    }





}