package com.ango.kingburguer.kingburguer.compose.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ango.kingburguer.kingburguer.auth.presentation.login.LoginUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModelq : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState : StateFlow<LoginUiState> = _uiState.asStateFlow()

    var email by mutableStateOf("")
        private  set
    var password by mutableStateOf("")
        private  set


    fun reset(){
        _uiState.update {LoginUiState()}


    }


    fun send(){
        _uiState.update { it.copy(isLoading = true) }
        //simulacao de latencia de rede / atraso de chama

        viewModelScope.launch(){
            delay(300L)
          //  _uiState.update { it.copy(isLoading = false, goToHome = false, error ="erro de conexao") }
            _uiState.update{it.copy(isLoading = false,goToHome = true)}
        }



    }
}