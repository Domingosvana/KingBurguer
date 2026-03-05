package com.ango.kingburguer.kingburguer.core.api

import com.ango.kingburguer.BuildConfig
import com.ango.kingburguer.kingburguer.cupons.data.CoupnsFetchResponse
import com.ango.kingburguer.kingburguer.data.FeedResponse
import com.ango.kingburguer.kingburguer.product.data.HighlightProductResponse
import com.ango.kingburguer.kingburguer.auth.data.LoginRequest
import com.ango.kingburguer.kingburguer.auth.data.LoginResponse
import com.ango.kingburguer.kingburguer.product.data.ProductDetailResponse
import com.ango.kingburguer.kingburguer.auth.data.RefreshTokenRequest
import com.ango.kingburguer.kingburguer.auth.data.UserCreateResponse
import com.ango.kingburguer.kingburguer.auth.data.UserRequest
import com.ango.kingburguer.kingburguer.profile.data.ProfileResponse
import com.ango.kingburguer.kingburguer.cupons.data.CouponResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


interface KingBurguerService {

   // @GET("kingburguer")
//    suspend fun getTest(): Response<String>
  //  suspend fun getTest(): String

    @POST("users")
    suspend fun postUser(
        @Body userRequest: UserRequest,
        @Header("x-secret-key") secretkey: String = BuildConfig.X_SECRET_KEY //"2d2df78e-7c0b-4bf2-b1c9-ff0a1963c281"
    ): Response<UserCreateResponse>//String


    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest,
        @Header("x-secret-key") secretkey: String = BuildConfig.X_SECRET_KEY //"2d2df78e-7c0b-4bf2-b1c9-ff0a1963c281"
    ): Response<LoginResponse>//String // trocar o tipo response para criar um tipo generico


    //No nosso autoizacao longa nao precisamos de secret-key porque vamos usar uma requizicao longa
    //VAMOS FAZER ATUALIZACAO DE DADOS TAMBEM
    @PUT("auth/refresh-token")
    suspend fun refreshToken(
        @Body refreshTokenRequest: RefreshTokenRequest,
        @Header("Authorization") token: String,
        //@Header("x-secret-key") secretkey: String = BuildConfig.X_SECRET_KEY //")
    ): Response<LoginResponse>//String


    @GET("feed")
    suspend fun fetshFeed(
        @Header("Authorization") token: String,
        ): Response<FeedResponse>//String


//buscar o detalhe do produto
    @GET("products/{id}")
    suspend fun fetchProductById(
        @Header("Authorization") token: String,
        @Path("id") productId: Int
    ): Response<ProductDetailResponse>//()



    //buscar os dados do usuario
    @GET("users/me")
    suspend fun fetchUser(
        @Header("Authorization") token: String,
    ): Response<ProfileResponse>



    @POST("products/{id}/coupon")
    suspend fun createCoupon(
        @Header("Authorization") token: String,
        @Path("id") productId: Int,
    ): Response<CouponResponse>


    @GET("highlight")
    suspend fun fetchHighlight(
        @Header("Authorization") token: String,
    ): Response<HighlightProductResponse>


    @GET("coupons")
    suspend fun  fetchCoupons(
        @Header("Authorization") token: String,
        @Query("product_id") productId: Int? = null,
        @Query("page") page: Int? = null,
        @Query("expired") expired: Int? = null   // 1 para expirados, 0 para ativos

    ): Response<CoupnsFetchResponse>




/*
    companion object {

          val BASE_URL = "https://hades.tiagoaguiar.co/kingburguer/"

        fun create(): KingBurguerService{
            val  logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY

            }

            val clientOK = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
                //para data
            val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(clientOK)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(KingBurguerService::class.java)



        }





    }

 */


}