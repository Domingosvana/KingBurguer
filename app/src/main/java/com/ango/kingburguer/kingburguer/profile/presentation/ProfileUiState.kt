package com.ango.kingburguer.kingburguer.profile.presentation

import com.ango.kingburguer.kingburguer.profile.data.ProfileResponse

data class ProfileUiState(
    val urserProfile: ProfileResponse? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
