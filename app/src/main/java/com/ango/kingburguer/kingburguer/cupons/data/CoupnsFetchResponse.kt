package com.ango.kingburguer.kingburguer.cupons.data

import com.google.gson.annotations.SerializedName
import java.util.Date


/*
" total": 5,
    "limit": 20,
    "data": [
        {
            "id": 653,
            "product_id": 4,
            "coupon": "ILNHWY",
            "expiration_at": "2026-03-13T12:59:12",
            "created_at": "2026-02-26T12:59:11"
        },


    ]
}
 */

data class CoupnsFetchResponse(
    val total: Int?= null,
    val limit: Int? = null,
    val data: List<CouponFilter>? = null,

)

data class CouponFilter(
    val id: Int? = null,
    @SerializedName("product_id")
    val productId: Int? = null,
    val coupon: String?= null,
    @SerializedName("expiration_at")
    val expirationAt: String? = null,
    @SerializedName("created_at")
    val createdAt: Date ,
)
