package com.ango.kingburguer.kingburguer.auth.presentation.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ango.kingburguer.kingburguer.auth.data.AuthRepositoryImpl
import com.ango.kingburguer.kingburguer.auth.presentation.signup.FieldState
import com.ango.kingburguer.kingburguer.core.validation.EmailValidator
import com.ango.kingburguer.kingburguer.core.validation.PasswordValidator
import com.ango.kingburguer.kingburguer.core.data.ApiResult
import com.ango.kingburguer.kingburguer.auth.data.LoginRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


//Anotar com hilt colocar o @inject que vai injetar o construtor e o logar  onde  injectar as dependecias na qual o  onde o construtor depende
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    var formState by mutableStateOf(FormState())

    private val emailValidator = EmailValidator()
    private val passwordValidator = PasswordValidator()

/*
    init {
        viewModelScope.launch {
            repository.userCredentialsFlow.collect {value ->
                Log.i("Teste", "userCredentialsFlow is $value")


            }
        }
    }

 */




    fun reset() {
        _uiState.update { LoginUiState() }
    }

    fun updateEmail(newEmail: String) {
        val textString = emailValidator.validate(newEmail)
        formState = formState.copy(
            email = FieldState(field = newEmail, error = textString, isValid = textString == null)
        )
        updateButton()
    }

    fun updatePassword(newPassword: String) {
        val textString = passwordValidator.validate(newPassword)
        formState = formState.copy(
            password = FieldState(
                field = newPassword,
                error = textString,
                isValid = textString == null
            )
        )

        updateButton()
    }

    fun updateRememberMe(rememberMe: Boolean) {
        formState = formState.copy(
            rememberMe = rememberMe
        )
    }

    private fun updateButton() {
        val formIsValid = with(formState) {
            email.isValid && password.isValid
        }

        formState = formState.copy(
            formIsValid = formIsValid
        )
    }

    fun send() {
        _uiState.update { it.copy(isLoading = true) }

        // simulação de latencia de rede / atraso de chamada
        viewModelScope.launch {
            with(formState) {
                val loginRequest = LoginRequest(
                    username = email.field,
                    password = password.field,
                )

                val result = repository.login(loginRequest, rememberMe)
                Log.i("Teste", "result is $result")
                // grande poder da classe selada!
                // tratar nossas classes com WHEN

                when (result) {
                    is ApiResult.Success -> {
                        _uiState.update { it.copy(isLoading = false, goToHome = true) }
                    }

                    is ApiResult.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }
/*
                    is ApiResult.Error -> {
                        _uiState.update { it.copy(isLoading = false, error = result.message) }
                    }

 */
                }

            }
        }
    }
/*
    companion object {
        val factory = viewModelFactory {
            initializer {
                val application  = this[APPLICATION_KEY]!!.applicationContext
                val localStore = KingBurguerLocalStore(application)
                val service = KingBurguerService.create()
                val repository = KingBurguerRepository(service, localStore )
                LoginViewModel(repository)
            }
        }
    }

 */

}



// 0. ocioso

// 1. carregando (progress)
// 2. sucesso (navegar para a tela principal)
// 3. falha (exibir alerta)

















