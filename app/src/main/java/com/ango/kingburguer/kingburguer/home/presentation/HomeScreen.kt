package com.ango.kingburguer.kingburguer.home.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.ango.kingburguer.R
import com.ango.kingburguer.kingburguer.commons.currency
import com.ango.kingburguer.kingburguer.home.presentation.HomeViewModel
import com.ango.kingburguer.kingburguer.data.CategoryResponse
import com.ango.kingburguer.kingburguer.product.data.HighlightProductResponse
import com.ango.kingburguer.ui.theme.KingBurguerTheme
import com.ango.kingburguer.ui.theme.Orange600
import java.util.Date

@Composable
fun HomeScreen(
    modifier: Modifier,
    viewModel: HomeViewModel = viewModel(),
    onProductClicked: (Int) -> Unit
) {
    val state = viewModel.uiState.collectAsState().value
    HomeScreen(modifier, state, onProductClicked)
}

@Composable
fun HomeScreen(
    modifier: Modifier,
    state: HomeUiState,
    onProductClicked: (Int) -> Unit
) {
    // highlight View
    Column(
        modifier = modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        HighlightView(state.highlightUiState, onProductClicked)
        CategoriesView(state.categoryUiState, onProductClicked)
    }
}

@Composable
private fun HighlightView(state: HighlighUiState, onProductClicked: (Int) -> Unit) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
        when {
            state.isLoanding -> {
                CircularProgressIndicator()
            }

            state.error != null -> {
                androidx.compose.material.Text(
                    state.error,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            state.product != null -> {
                // o conteudo de destaque
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp),
                    placeholder = painterResource(R.drawable.logo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    model = state.product.pictureUrl,
                )

                OutlinedButton(
                    onClick =  {
                        onProductClicked(state.product.productId)
                    },
                    modifier = Modifier
                        .padding(bottom = 12.dp),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 6.dp
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = Orange600
                    )
                ) {
                    androidx.compose.material.Text(
                        color = Color.White,
                        text = stringResource(R.string.show_more)
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoriesView(
    state: CategoryUiState,
    onProductClicked: (Int) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            state.isLoanding -> {
                CircularProgressIndicator()
            }
            state.error != null -> {
                androidx.compose.material.Text(
                    state.error,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            else -> {
                HomeScreen(Modifier, state.categories, onProductClicked)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    categories: List<CategoryResponse>,
    onProductClicked: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            itemsIndexed(categories) { index, cat ->
                val topPadding = if (index == 0) 20.dp else 0.dp
                val bottomPadding = if (index == categories.size - 1) 20.dp else 0.dp
                androidx.compose.material.Text(
                    modifier = Modifier
                        .padding(start = 12.dp, bottom = 12.dp, top = topPadding),
                    text = cat.name,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.headlineMedium,
                )

                CompositionLocalProvider(
                    LocalOverscrollConfiguration provides null
                ) {
                    LazyRow(
                        modifier = Modifier
                            .padding(bottom = bottomPadding)
                    ) {
                        itemsIndexed(cat.products) { index, product ->
                            val startPadding = if (index == 0) 20.dp else 8.dp
                            val endPadding = if (index == categories.size - 1) 20.dp else 8.dp

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier
                                    .widthIn(max = 160.dp)
                                    .padding(start = startPadding, end = endPadding)
                            ) {
                                AsyncImage(
                                    model = product.pictureUrl,
                                    placeholder = painterResource(R.drawable.logo),
                                    modifier = Modifier
                                        .size(140.dp, 180.dp)
                                        .border(
                                            BorderStroke(0.3.dp, Color.Gray),
                                            RoundedCornerShape(8.dp)
                                        )
                                        .clickable {
                                            onProductClicked(product.id)
                                        },
                                    contentDescription = product.name
                                )
                                androidx.compose.material.Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    color = MaterialTheme.colorScheme.inverseSurface,
                                    text = product.name,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                androidx.compose.material.Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            MaterialTheme.colorScheme.primary,
                                            RoundedCornerShape(4.dp)
                                        ),
                                    color = MaterialTheme.colorScheme.surface,
                                    text = product.price.currency(),
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LightHomeLoadingScreenPreview() {
    KingBurguerTheme(dynamicColor = false, darkTheme = false) {
        val state = HomeUiState(
            categoryUiState = CategoryUiState(isLoanding  = true),
        )
        HomeScreen(Modifier, state) {}
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DarkHomeErrorScreenPreview() {
    KingBurguerTheme(dynamicColor = false, darkTheme = true) {
        val state = HomeUiState(
            categoryUiState = CategoryUiState(error = "Erro de teste"),
        )
        HomeScreen(Modifier, state) {}
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DarkHomeEmptyScreenPreview() {
    KingBurguerTheme(dynamicColor = false, darkTheme = true) {
        val state = HomeUiState(
            categoryUiState = CategoryUiState(
                categories = emptyList()
            ),
        )
        HomeScreen(Modifier, state) {}
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LightHighlightSuccessPreview() {
    KingBurguerTheme(dynamicColor = false, darkTheme = false) {
        val state = HomeUiState(
            highlightUiState = HighlighUiState(
                product = HighlightProductResponse(
                    0, 0, "https://placehold.co/600x400", Date(),
                )
            )
        )
        HomeScreen(Modifier, state) {}
    }
}


















/*
data class Product(
    val name: String = "",
    val id: Int,
    @DrawableRes val picture: Int = R.drawable.vitrine,
   val price: Double = 19.9
)
data class Category(
    val products: List<Product>,
    val name: String
)

 


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.factory),
    onProductClicked:(Int) -> Unit

) {
    val state = viewModel.uiState.collectAsState().value
    HomeScreen(
        modifier = modifier,
        state = state,
        onProductClicked = onProductClicked
    )


}




@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state:HomeUiState,
    onProductClicked:(Int) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        when{
            state.isLoanding -> {
                CircularProgressIndicator()
            }

            state.error != null -> {
                Text(text = state.error, color = Color.Red)

            }

            else -> {
                HomeScreen(
                    modifier = modifier,
                    onProductClicked = onProductClicked,
                    state.categories
                )
            }
        }
    }


}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
 fun HomeScreen(
    modifier: Modifier,
    onProductClicked:(Int) -> Unit,
    categories:List<CategoryResponse>
    ) {
/*
     val categories = listOf(
         Category(
             name = "Hamburguer",
             products = listOf(
                 Product("X-Salada",1),
                 Product("X-Bacon",2),
                 Product("X-Tudo",3)
             )
         ),

         Category(
             name = "Bebidas",
            products =   listOf(
                 Product("Coca-Cola com bom sabor unico para ser provado",4),
                 Product("Fanta",5),
                 Product("Guaraná",6),
                 Product("Pepsi",7),
                 Product("Sprite",8),
                 Product("Água",9),
                 Product("Cerveja",10),
             )
             ),

         Category(
             name = "Acompanhamentos",
             products = listOf(
                 Product("Batata Frita s1",11),
                 Product("Onion Rings s2",12),
                 Product("Cheddar s3",13)
             )


     )
     )

 */


    Column(modifier = modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {

        Box(contentAlignment = Alignment.BottomCenter){
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .background(Color.Blue),
                painter = painterResource(id = R.drawable.highlight),
                contentDescription = null,
                contentScale = ContentScale.Crop


            )
            OutlinedButton(
                onClick ={},
                modifier = Modifier
                    .padding(bottom = 12.dp),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 6.dp,
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = MaterialTheme.colorScheme.primary,
                )
            ) {
                Text(
                    color = Color.White,
                    text = stringResource(R.string.get_coupon)
                )
            }


        }




        LazyColumn(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ){
            itemsIndexed(categories){index, cat ->
                val topPadding =if(index == 0) 20.dp else 0.dp
                val bottomPadding = if(index == categories.size - 1) 20.dp else 0.dp


                Text(
                    modifier = Modifier.padding(start = 12.dp,bottom = 12.dp,top = topPadding),
                    text= cat.name,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.headlineMedium
                )

                CompositionLocalProvider(
                    LocalRippleConfiguration provides null
                ) {

                    LazyRow(
                        modifier = Modifier
                            .padding(bottom = bottomPadding),
                    ) {
                        val startPadding =if(index == 0) 20.dp else 8.dp
                        val endPadding = if(index == categories.size - 1) 20.dp else 8.dp


                        itemsIndexed(cat.products){index, product ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier
                                    .widthIn(max = 160.dp)
                                    .padding(start = startPadding, end = endPadding)

                            ){
                                AsyncImage(
                                    model = product.pictureUrl,
                                    placeholder = painterResource(id = R.drawable.logo),
                                    modifier = Modifier
                                        .size(140.dp, 180.dp)
                                        .border(
                                            BorderStroke(0.3.dp, Color.Gray),
                                            RoundedCornerShape(8.dp)
                                        )
                                        .clickable { onProductClicked(product.id) },
                                    //painter = painterResource(id = R.drawable.logo),
                                    contentDescription = product.name,

                                  //  error = painterResource(id = R.drawable.logo),


                                )



                                Text(
                                    modifier = Modifier.fillMaxSize(),
                                    color = MaterialTheme.colorScheme.inverseSurface,
                                    text = product.name,
                                    style = MaterialTheme.typography.headlineMedium
                                )

                                Text(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            MaterialTheme.colorScheme.primary,
                                            RoundedCornerShape(4.dp)
                                        ),
                                    color = MaterialTheme.colorScheme.surface,
                                    text = product.price.currency(),
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.headlineMedium
                                )


                            }

                        }
                    }



                }




            }



        }


    }
}













@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LightHomeScreenPreview() {
    KingBurguerTheme(dynamicColor = false, darkTheme = false) {
        val state = HomeUiState(isLoanding = true)
        HomeScreen(modifier = Modifier,state){}
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DarkHomeScreenPreview() {
    KingBurguerTheme(dynamicColor = false, darkTheme = true) {
        val state = HomeUiState(error = "erro ao carregar")
        HomeScreen(modifier = Modifier,state){}
    }
}

 */