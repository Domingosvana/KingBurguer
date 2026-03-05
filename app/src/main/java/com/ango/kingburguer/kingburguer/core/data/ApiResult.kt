package com.ango.kingburguer.kingburguer.core.data

//vamos criar um tipo que aceita um tipo generico

// devemos colocar o out porque o tipo T nao e subtito do tipo Nothing nao garante a
//substituicao do tipo N ou T
sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String) : ApiResult<Nothing>()

}


data class ErrorAuth(val detail: ErrorDetail)

data class Error(val detail: String)

data class ErrorDetail(val message: String)
