package com.ango.kingburguer.kingburguer.auth.presentation.signup

import com.ango.kingburguer.kingburguer.core.validation.TextString


data class FieldState(
    var field: String = "",
    //val error: String? = null,
    val error: TextString? = null,
    //val error: Int? = null,
    //val error: Int = 0,
    val isValid: Boolean = false
    )



data class FormState(
    val name: FieldState = FieldState(),
    val email: FieldState = FieldState(),
    val password: FieldState = FieldState(),
    val confirmPassword: FieldState = FieldState(),
    val document: FieldState = FieldState(),
    val birthday: FieldState = FieldState(),
    val formIsValid: Boolean = false
    )
