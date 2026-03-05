package com.ango.kingburguer.kingburguer.auth.presentation.login

data class LoginUiState(
    val isLoading: Boolean = false,
    val goToHome: Boolean = false,
    val error: String? = null,

)
