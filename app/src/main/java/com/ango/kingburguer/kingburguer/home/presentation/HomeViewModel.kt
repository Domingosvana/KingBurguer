package com.ango.kingburguer.kingburguer.home.presentation

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
class HomeViewModel @Inject constructor(
   // savedStateHandle: SavedStateHandle
    private val repository: KingBurguerRepository
): ViewModel(){

    private val _state = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _state.asStateFlow()



    init {
       fetchCategories()
        fetchHighlight()
    }

    fun fetchCategories() {
        _state.update { it.copy(categoryUiState = CategoryUiState(isLoanding = true) ) }
        viewModelScope.launch {
            val response = repository.fetshFeed()
            when(response){
                is ApiResult.Error -> {
                    val state = CategoryUiState(isLoanding = false, error = response.message)
                    _state.update{it.copy(categoryUiState = state)}
                }
                is ApiResult.Success -> {
                    val state = CategoryUiState(isLoanding = false, categories = response.data.categories)
                    _state.update{it.copy(categoryUiState = state)}
                }
            }

        }

    }



    fun fetchHighlight() {
        _state.update { it.copy(highlightUiState = HighlighUiState(isLoanding = true)) }
        viewModelScope.launch {
            val response = repository.fetchHighlight()
            when(response){
                is ApiResult.Error -> {
                    val state = HighlighUiState(isLoanding = false, error = response.message)
                    _state.update{it.copy(highlightUiState = state)}
                }
                is ApiResult.Success -> {
                    val state = HighlighUiState(isLoanding = false, product = response.data)
                    _state.update{it.copy(highlightUiState = state)}
                }
            }

        }

    }




/*
    companion object{
        val factory = viewModelFactory {
            initializer {
                val application  = this[APPLICATION_KEY]!!.applicationContext
                val localStore = KingBurguerLocalStore(application)
                val service = KingBurguerService.create()
                val repository = KingBurguerRepository(service, localStore )
                HomeViewModel(repository)
            }
        }
    }

 */

}