package com.ango.kingburguer.kingburguer.core.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ango.kingburguer.kingburguer.core.domain.CheckSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkSessionUseCase: CheckSessionUseCase,
    ) : ViewModel() {

    private val _hasSessionState = MutableStateFlow<Boolean?>(null)
    val hasSession: StateFlow<Boolean?> = _hasSessionState

    init {

            checkAuthentication()

    }



    private fun checkAuthentication(){
        checkSessionUseCase().onEach { authenticated ->
            _hasSessionState.value = authenticated
            Log.d("SplashViewModel", "checkAuthentication: $authenticated")
        }.launchIn(viewModelScope)
    }



    /*
    companion object {
        val factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY]!!.applicationContext
                val service = KingBurguerService.create()
                val localStorage = KingBurguerLocalStore(application)
                val repository = KingBurguerRepository(service, localStorage)
                SplashViewModel(repository)
            }
        }
    }

     */
}