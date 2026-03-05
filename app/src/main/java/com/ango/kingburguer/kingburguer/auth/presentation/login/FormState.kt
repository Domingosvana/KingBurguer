package com.ango.kingburguer.kingburguer.auth.presentation.login

import com.ango.kingburguer.kingburguer.auth.presentation.signup.FieldState

data class FormState(
    val email: FieldState = FieldState(),
    val password: FieldState = FieldState(),
    val rememberMe: Boolean = false,
    val formIsValid: Boolean = false
)