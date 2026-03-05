package com.ango.kingburguer.kingburguer.cupons.presentation

import com.ango.kingburguer.kingburguer.cupons.data.CoupnsFetchResponse

data class CouponUiState(
    val isLoading: Boolean = false,
    val coupon: CoupnsFetchResponse? = null,
    val error: String? = null,
   // val couponfilter:  CouponFilter? = null

)
