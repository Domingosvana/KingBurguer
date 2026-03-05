package com.ango.kingburguer.kingburguer.cupons.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ango.kingburguer.kingburguer.core.data.ApiResult

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tiagoaguiar.kingburguer.main.domain.KingBurguerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CouponViewModel @Inject constructor(
   // savedStateHandle: SavedStateHandle,
    private val repository: KingBurguerRepository
): ViewModel(){
    //val productId:Int  = savedStateHandle["productId"]?:0
   // val product by mutableStateOf(Product(id = productId))

    private val _uiState = MutableStateFlow(CouponUiState())
    val uiState : StateFlow<CouponUiState> = _uiState.asStateFlow()

    init {
        start()
    }

   // fun reset() {
     //   _uiState.value = ProductUiState()
   // }


/*
    fun start() {
        _uiState.value = CouponUiState(isLoading = true)
        viewModelScope.launch {
            val response = repository.fetchCoupons()
            when (response) {
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = response.message) }

                }

                is ApiResult.Success -> {_uiState.update { it.copy(isLoading = false, coupon = response.data, error = null) }


                }


            }


        }

    }

 */



    fun start(expired: Boolean? = null) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val expiredParam = when (expired) {
                true -> 1
                false -> 0
                null -> null
            }
            val response = repository.fetchCoupons(page = 0, expired = expiredParam)
            when (response) {
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = response.message) }
                }
                is ApiResult.Success -> {
                    _uiState.update { it.copy(isLoading = false, coupon = response.data) }
                }
            }
        }
    }









    //Log("CouponViewModel", "start: $response")




/*
    companion object{
        val factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val application = this[APPLICATION_KEY]!!.applicationContext
                val service = KingBurguerService.create()
                val localStorage = KingBurguerLocalStore(application)
                val repository = KingBurguerRepository(service, localStorage)
                CouponViewModel(repository)


            }
        }
    }

 */

}