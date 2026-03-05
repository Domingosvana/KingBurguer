package dev.tiagoaguiar.kingburguer.compose.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.ango.kingburguer.R
import com.ango.kingburguer.kingburguer.commons.currency
import com.ango.kingburguer.kingburguer.compose.components.KingAlert
import com.ango.kingburguer.kingburguer.compose.components.KingButton
import com.ango.kingburguer.kingburguer.product.presentation.ProductUiState
import com.ango.kingburguer.kingburguer.product.presentation.ProductViewModel
import com.ango.kingburguer.kingburguer.product.data.CategoryDetailResponse
import com.ango.kingburguer.kingburguer.product.data.ProductDetailResponse
import com.ango.kingburguer.ui.theme.KingBurguerTheme


import java.util.Date

@Composable
fun ProductScreen(
    modifier: Modifier,
    viewModel: ProductViewModel = viewModel(),
    onCouponGenerated: () -> Unit,
) {
    val state = viewModel.uiState.collectAsState().value
    ProductScreen(modifier, state, couponClicked = {
        viewModel.createCoupon()
    }, onCouponGenerated = {
        viewModel.reset()
        onCouponGenerated()
    })
}

@Composable
fun ProductScreen(
    modifier: Modifier,
    state: ProductUiState,
    couponClicked: () -> Unit,
    onCouponGenerated: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            state.isLoading -> { // LOADING
                CircularProgressIndicator()
            }

            state.error != null -> { // ERROR
                androidx.compose.material3.Text(
                    state.error,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            else -> { // SUCESSO
                state.productDetail?.let {
                    ProductScreen(modifier, state.productDetail, couponClicked)
                }
                state.coupon?.let {
                    KingAlert(
                        onDismissRequest = {},
                        onConfirmation = onCouponGenerated,
                        stringResource(R.string.app_name),
                        stringResource(R.string.coupon_generated, state.coupon.coupon),
                        Icons.Filled.Info
                    )
                }
            }
        }
    }
}

@Composable
fun ProductScreen(
    modifier: Modifier = Modifier,
    product: ProductDetailResponse,
    couponClicked: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(scrollState)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp),
                    model = product.pictureUrl,
                    placeholder = painterResource(R.drawable.logo),
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = product.name,
                        modifier = Modifier.weight(1f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        modifier = Modifier
                            .wrapContentWidth()
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 12.dp),

                        text = product.price.currency(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.surface,
                        style = MaterialTheme.typography.titleMedium
                    )

                }

                Text(
                    modifier = Modifier
                        .padding(start = 24.dp, end = 24.dp, bottom = 56.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    text = product.description
                )
            }

            KingButton(
                modifier = Modifier
                    .padding(horizontal = 24.dp),
                text = stringResource(R.string.get_coupon),
                onClick = couponClicked)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LightProductScreenPreview() {
    KingBurguerTheme(dynamicColor = false, darkTheme = false) {
        ProductScreen(
            product = ProductDetailResponse(
                id = 1,
                name = "Product A",
                description = "Teste de titulo de produto que seja realmente muito grande euq eu não cabe na tela quando for enorme este texto que estou escrevendo",
                pictureUrl = "",
                price = 21.99,
                createdDate = Date(),
                categoryResponse = CategoryDetailResponse(
                    id = 1,
                    name = "cat test"
                )
            )
        ) { }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DarkProductScreenPreview() {
    KingBurguerTheme(dynamicColor = false, darkTheme = true) {
        ProductScreen(
            product = ProductDetailResponse(
                id = 1,
                name = "Product A",
                description = "Teste de titulo de produto que seja realmente muito grande euq eu não cabe na tela quando for enorme este texto que estou escrevendo",
                pictureUrl = "",
                price = 21.99,
                createdDate = Date(),
                categoryResponse = CategoryDetailResponse(
                    id = 1,
                    name = "cat test"
                )
            )
        ) { }
    }
}