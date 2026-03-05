package com.ango.kingburguer.kingburguer.auth.presentation.signup

data class SignUpUiState(

    val isLoading: Boolean = false,
    val goToLogin: Boolean = false,
    val error: String? = null,

    )
