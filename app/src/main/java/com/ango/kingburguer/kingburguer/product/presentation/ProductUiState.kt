package com.ango.kingburguer.kingburguer.product.presentation

import com.ango.kingburguer.kingburguer.cupons.data.CouponResponse
import com.ango.kingburguer.kingburguer.product.data.ProductDetailResponse

data class ProductUiState(
    val isLoading:Boolean = false,
    val productDetail: ProductDetailResponse? = null,
    val error:String? = null,
    val coupon: CouponResponse? = null,
)