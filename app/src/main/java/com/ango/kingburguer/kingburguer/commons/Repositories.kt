package com.ango.kingburguer.kingburguer.commons

import com.ango.kingburguer.kingburguer.core.data.ApiResult
import com.ango.kingburguer.kingburguer.core.data.Error
import com.ango.kingburguer.kingburguer.core.data.ErrorAuth
import com.ango.kingburguer.kingburguer.core.data.ErrorDetail
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import retrofit2.Response

//criar uma funcao para receber ou ser o tipo generico
// o retorno do response<loginresponse> e retrofit 2
suspend fun<T> apiCall(call: suspend () -> Response<T>): ApiResult<T> {
    try {
        val response = call()

        // o tipo de resposta pode vir retornar erro ou sucesso entao devemos deixar que ele seja tipo generico


        if (!response.isSuccessful) {
            val errorData = response.errorBody()?.string()?.let { json ->
                if (response.code() == 401) {
                    try{
                        val errorAuth = Gson().fromJson(json, ErrorAuth::class.java)
                        ApiResult.Error(errorAuth.detail.message)
                    }catch (e: Exception){
                        val error = Gson().fromJson(json, Error::class.java)
                        ApiResult.Error(error.detail)
                    }



                    //LEMBRA  QUANDO UM PARAMETRO VEM DENTRO DE UM ERRAY OU DICIONARIO OU LISTA DEVE CRIAR UMA VARIAVEL
                    //   val errorAuth = Gson().fromJson(json, ErrorAuth::class.java)
                    // ApiResult.Error(errorAuth.detail.message)


                } else {
                    try{
                        val err = Gson().fromJson(json, Error::class.java)
                        ApiResult.Error(err.detail)

                    }catch (e: JsonSyntaxException){
                        val err = Gson().fromJson(json, ErrorDetail::class.java)
                        ApiResult.Error(err.message)
                    }



                    Gson().fromJson(json, ApiResult.Error::class.java)

                }
            }
            return errorData ?: ApiResult.Error("internal server error") //depois

        }
        // success
        val data = response.body()
        //   Gson().fromJson(json, LoginResponse.Success::class.java)


        //salva os dados do usuario no dataStore
        //so sera salva se usuario quer salvar os dados
        if (data == null) {
            return ApiResult.Error("unexpected response success") //depois}
        }




        return ApiResult.Success(data)  //depois
        //   return data ?: LoginResponse.Error("unexpected response success")
    } catch (e: Exception) {
        return ApiResult.Error(e.message ?: "unexpected exception")
    }



}

