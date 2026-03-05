package dev.tiagoaguiar.kingburguer.main.domain

import com.ango.kingburguer.kingburguer.core.data.ApiResult
import com.ango.kingburguer.kingburguer.cupons.data.CoupnsFetchResponse
import com.ango.kingburguer.kingburguer.cupons.data.CouponResponse
import com.ango.kingburguer.kingburguer.data.FeedResponse
import com.ango.kingburguer.kingburguer.product.data.HighlightProductResponse
import com.ango.kingburguer.kingburguer.product.data.ProductDetailResponse
import com.ango.kingburguer.kingburguer.profile.data.ProfileResponse

interface KingBurguerRepository {

    suspend fun fetshFeed(): ApiResult<FeedResponse>

    suspend fun fetchUser(): ApiResult<ProfileResponse>

    suspend fun createCoupon(productId: Int): ApiResult<CouponResponse>

    suspend fun fetchHighlight(): ApiResult<HighlightProductResponse>

    suspend fun fetchProductById(productId: Int): ApiResult<ProductDetailResponse>


    suspend fun fetchCoupons(page: Int? = null, expired: Int? = null): ApiResult<CoupnsFetchResponse>


}