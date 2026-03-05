package com.ango.kingburguer.kingburguer.main.data

import com.ango.kingburguer.kingburguer.auth.data.LoginRequest
import com.ango.kingburguer.kingburguer.auth.data.LoginResponse
import com.ango.kingburguer.kingburguer.commons.apiCall
import com.ango.kingburguer.kingburguer.core.api.KingBurguerService
import com.ango.kingburguer.kingburguer.core.data.ApiResult
import com.ango.kingburguer.kingburguer.core.data.KingBurguerLocalStore
import com.ango.kingburguer.kingburguer.core.data.UserCredentisls
import com.ango.kingburguer.kingburguer.cupons.data.CoupnsFetchResponse
import com.ango.kingburguer.kingburguer.cupons.data.CouponResponse
import com.ango.kingburguer.kingburguer.data.FeedResponse
import com.ango.kingburguer.kingburguer.product.data.HighlightProductResponse
import com.ango.kingburguer.kingburguer.product.data.ProductDetailResponse
import com.ango.kingburguer.kingburguer.profile.data.ProfileResponse
import dev.tiagoaguiar.kingburguer.main.domain.KingBurguerRepository
import javax.inject.Inject

class KingBurguerRepositoryImpl @Inject constructor(
    val service: KingBurguerService,
    //val localStore: KingBurguerLocalStore
    private val localDataSource: KingBurguerLocalDataSource
): KingBurguerRepository {

        //mostrar os dados da leitura do dataStore
       // val userCredentialsFlow = localStore.userCredentialsFlow
        //buscar as credencials do usuario do nosso datastore que e como noso banco de dados


/*

    suspend fun postUser(userRequest: UserRequest): ApiResult<UserCreateResponse> {
        val result = apiCall { service.postUser(userRequest) }
        return result

    }

 */



    override  suspend fun fetshFeed(): ApiResult<FeedResponse> {
        //vamos consultar os nossos dados do acesso
        // o fetshFedd depende do chave de acesso
        val userCredentials = localDataSource.fetchInittialUserCredentials()
        val token = "${userCredentials.tokenType} ${userCredentials.accessToken}"

        return/* result =*/ apiCall { service.fetshFeed(token) }
        //return result

    }



    override  suspend fun fetchProductById(productId: Int): ApiResult<ProductDetailResponse> {
        //vamos consultar os nossos dados do acesso
        val userCredentials = localDataSource.fetchInittialUserCredentials()
        val token = "${userCredentials.tokenType} ${userCredentials.accessToken}"
        return apiCall { service.fetchProductById(token, productId) }

    }


    override suspend fun fetchHighlight(): ApiResult<HighlightProductResponse> {
        val userCredentials = localDataSource.fetchInittialUserCredentials()
        val token = "${userCredentials.tokenType} ${userCredentials.accessToken}"
        return apiCall { service.fetchHighlight(token) }
    }


    override suspend fun fetchCoupons( page: Int? , expired: Int?): ApiResult<CoupnsFetchResponse> {
        val userCredentials = localDataSource.fetchInittialUserCredentials()
        val token = "${userCredentials.tokenType} ${userCredentials.accessToken}"
        return apiCall { service.fetchCoupons(token, page = page, expired = expired) }

    }



    //os dados do usuario
    override suspend fun fetchUser(): ApiResult<ProfileResponse> {
        val userCredentials = localDataSource.fetchInittialUserCredentials()
        val token = "${userCredentials.tokenType} ${userCredentials.accessToken}"
        return apiCall { service.fetchUser(token) }
    }



   override suspend fun createCoupon(productId: Int): ApiResult<CouponResponse> {
        val userCredentials = localDataSource.fetchInittialUserCredentials()
        val token = "${userCredentials.tokenType} ${userCredentials.accessToken}"
        return apiCall { service.createCoupon(token, productId) }
    }




/*
    suspend fun login(loginRequest: LoginRequest, keepLogged: Boolean): ApiResult<LoginResponse> {
        val result = apiCall {
            service.login(loginRequest)
        } //depois }

        if(result is ApiResult.Success<LoginResponse>){
            if (keepLogged) {
                val userCredentisls = UserCredentisls(
                    result.data.accessToken,
                    result.data.refreshToken,
                    result.data.expiresSeconds.toLong(),
                    result.data.tokenType
                )
                localDataSource.updateUserCredential(userCredentisls,)


               // upadateUserCredential(result.data)



            }
        }
        return result



    }

 */






























}