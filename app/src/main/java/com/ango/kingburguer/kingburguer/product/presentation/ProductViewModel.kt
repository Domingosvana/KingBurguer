package com.ango.kingburguer.kingburguer.product.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ango.kingburguer.kingburguer.product.presentation.ProductUiState
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
class ProductViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: KingBurguerRepository
): ViewModel(){
    val productId:Int  = savedStateHandle["productId"]?:0
   // val product by mutableStateOf(Product(id = productId))

    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState : StateFlow<ProductUiState> = _uiState.asStateFlow()

    init {
        start()
    }

    fun reset() {
        _uiState.value = ProductUiState()
    }



    fun start() {
        _uiState.value = ProductUiState(isLoading = true)
        viewModelScope.launch {
            val response = repository.fetchProductById(productId)
            when (response) {
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = response.message) }

                }

                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            productDetail = response.data,
                            error = null
                        )
                    }


                }
            }


        }

    }


    fun createCoupon() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val response = repository.createCoupon(productId)
            when(response) {
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = response.message) }
                }
                is ApiResult.Success -> {
                    _uiState.update { it.copy(isLoading = false, coupon = response.data) }
                }
            }
        }
    }




/*
    companion object{
        val factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val application = this[APPLICATION_KEY]!!.applicationContext
                val service = KingBurguerService.create()
                val localStorage = KingBurguerLocalStore(application)
                val repository = KingBurguerRepository(service, localStorage)
                ProductViewModel(savedStateHandle,repository)


            }
        }
    }


 */

}