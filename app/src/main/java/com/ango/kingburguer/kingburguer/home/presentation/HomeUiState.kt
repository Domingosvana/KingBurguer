package com.ango.kingburguer.kingburguer.home.presentation

import com.ango.kingburguer.kingburguer.data.CategoryResponse
import com.ango.kingburguer.kingburguer.product.data.HighlightProductResponse


data class HomeUiState(
   val categoryUiState: CategoryUiState = CategoryUiState(),
   val highlightUiState: HighlighUiState = HighlighUiState()
)

data class CategoryUiState(
    val isLoanding:Boolean = false,
    val categories: List<CategoryResponse> = emptyList(),
    val error:String? = null
    )




data class HighlighUiState(
    val isLoanding:Boolean = false,
    val product: HighlightProductResponse? = null,
    val error:String? = null
)
