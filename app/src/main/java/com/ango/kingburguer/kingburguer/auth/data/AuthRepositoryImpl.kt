package com.ango.kingburguer.kingburguer.auth.data

import com.ango.kingburguer.kingburguer.core.api.KingBurguerService
import com.ango.kingburguer.kingburguer.auth.domain.AuthRepository
import com.ango.kingburguer.kingburguer.core.data.ApiResult
import com.ango.kingburguer.kingburguer.core.data.KingBurguerLocalStore
import com.ango.kingburguer.kingburguer.commons.apiCall
import com.ango.kingburguer.kingburguer.core.data.UserCredentisls
import com.ango.kingburguer.kingburguer.main.data.KingBurguerLocalDataSource

import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    val service: KingBurguerService,
    //val localStore: KingBurguerLocalStore
    val localDataSource: KingBurguerLocalDataSource

) : AuthRepository{

    override suspend fun login(loginRequest: LoginRequest, keepLogged: Boolean): ApiResult<LoginResponse>   {
        val result =  apiCall {
            service.login(loginRequest)
        } //depois }

        if(result is ApiResult.Success<LoginResponse>){
/*
                val userCredentisls = UserCredentisls(
                    result.data.accessToken,
                    result.data.refreshToken,
                    result.data.expiresSeconds.toLong(),
                    result.data.tokenType
                )
                localStore.updateUserCredential(userCredentisls)

 */


                 upadateUserCredential(result.data,keepLogged)


        }
        return result
    }


   override  suspend fun postUser(userRequest: UserRequest): ApiResult<UserCreateResponse> {
        val result = apiCall { service.postUser(userRequest) }
        return result

    }


   override suspend fun refreshToken(request: RefreshTokenRequest):ApiResult<LoginResponse>  {

        //vamos consultar os nossos dados do acesso
        val userCredentials = localDataSource.fetchInittialUserCredentials()
        val token = "${userCredentials.tokenType} ${userCredentials.accessToken}"
        val result = apiCall { service.refreshToken(request, token) }
        //  val result =apiCall {service.refreshToken(request, "${userCredentials.tokenType} ${userCredentials.refreshToken}")  }

        // success
        if (result is ApiResult.Success<LoginResponse>){

            /*
            val newUserCredentisls = UserCredentisls(
                result.data.accessToken,
                result.data.refreshToken,
                result.data.expiresSeconds.toLong(),
                result.data.tokenType
            )
            localStore.updateUserCredential(newUserCredentisls)

             */


              upadateUserCredential(result.data,true)

        }

        //salva os dados do usuario no dataStore
        //so sera salva se usuario quer salvar os dados


        //vamos atualizar os nossos refreshtoken


        return result
        //   return data ?: LoginResponse.Error("unexpected response success")

    }



   override suspend fun fetchInitialUserCredentials() = localDataSource.fetchInittialUserCredentials()


    private suspend fun upadateUserCredential(data: LoginResponse,keepLogged: Boolean){
        val newUserCredentisls = UserCredentisls(
            data.accessToken,
            data.refreshToken,
            data.expiresSeconds.toLong(),
            data.tokenType
        )
        localDataSource.updateUserCredential(newUserCredentisls,keepLogged)
    }


}