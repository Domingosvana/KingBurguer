package com.ango.kingburguer.kingburguer.main.presentation


import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface



import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ango.kingburguer.R
import com.ango.kingburguer.kingburguer.cupons.presentation.CouponScreen
import com.ango.kingburguer.kingburguer.home.presentation.HomeScreen

import com.ango.kingburguer.kingburguer.profile.presentation.ProfileScreen
import com.ango.kingburguer.kingburguer.cupons.presentation.CouponViewModel
import com.ango.kingburguer.kingburguer.home.presentation.HomeViewModel
import com.ango.kingburguer.kingburguer.main.presentation.MainViewModel
import com.ango.kingburguer.kingburguer.core.presentation.Screen
import com.ango.kingburguer.kingburguer.product.presentation.ProductViewModel
import com.ango.kingburguer.kingburguer.profile.presentation.ProfileViewModel
import com.ango.kingburguer.ui.theme.KingBurguerTheme
import dev.tiagoaguiar.kingburguer.compose.product.ProductScreen




@Composable
fun MainScreen(
    onNavigateToLogin: () -> Unit,
    OnNavigateToProfile: () -> Unit,
    viewModel: MainViewModel = viewModel()
) {
    val shouldQuit = viewModel.uiStorage.collectAsState().value
    if (shouldQuit) {
        viewModel.reset()
        onNavigateToLogin()
    }
    MainScreen(
        onLogoutClick = {
            viewModel.logout()
        },
        onNavigateToProfile = { OnNavigateToProfile() },

    )
}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onLogoutClick: () -> Unit,onNavigateToProfile: () -> Unit ) {
    val navController = rememberNavController()

    //para visualizar o titulo da tela ou da navegacao
    var titleTopBarId by remember { mutableIntStateOf (R.string.menu_home) }

    //este euma pilha de excutacao backstack
    val navBackStackEntry by navController.currentBackStackEntryAsState() //.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    LaunchedEffect(key1 = currentRoute){
        if(currentRoute == Screen.HOME.route){
            titleTopBarId = R.string.menu_home

        }
    }


    Surface(modifier = Modifier.fillMaxSize()) {



        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier.height(56.dp),
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,

                            title = { Text(text = stringResource(id = titleTopBarId))
                    },
                            navigationIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = stringResource(id = R.string.app_name),
                            tint = Color.Unspecified

                        )
                    },

                    actions = {
                        IconButton(onClick ={ navController.navigate(Screen.PROFILE.route)} ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        IconButton(onClick = onLogoutClick) {
                            Icon(
                                imageVector = Icons.Default.PowerSettingsNew,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }


                    },



                )



            },


            bottomBar = {
                MainBottomNavigation(
                    navController,

                ){titleId ->
                    titleTopBarId = titleId
                }
            },
        ) { contentPadding ->
            MainContentScreen(
                navController = navController,
                contentPadding = contentPadding
            )
        }
    }
}

data class NavigationItem(
    @StringRes val title: Int,
    val icon: ImageVector,
    val router: Screen
)

@Composable
fun MainBottomNavigation(navController: NavHostController,onNavigationChanged:(Int) -> Unit ) {
    val navigationItems = listOf(
        NavigationItem(
            title = R.string.menu_home,
            icon = Icons.Default.Home,
            router = Screen.HOME
        ),
        NavigationItem(
            title = R.string.menu_coupon,
            icon = Icons.Default.ShoppingCart,
            router = Screen.COUPON
        ),
        NavigationItem(
            title = R.string.menu_profile,
            icon = Icons.Default.Person,
            router = Screen.PROFILE
        ),
    )

    NavigationBar(
    modifier = Modifier.height(56.dp),
        containerColor = MaterialTheme.colorScheme.background
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState() //.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route


        navigationItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.router.route,
                onClick = {
                    if(currentRoute != item.router.route){
                        navController.navigate(item.router.route){
                            popUpTo(navController.graph.findStartDestination().id){
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        onNavigationChanged(item.title)


                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = stringResource(item.title)
                    )
                },
                label = {
                    Text(stringResource(item.title))
                },
               // unselectedContentColor = MaterialTheme.colorScheme.outline,
               // selectedContentColor = MaterialTheme.colorScheme.primary
            )
        }
    }
}


@Composable
fun MainContentScreen(navController: NavHostController,
                      contentPadding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Screen.HOME.route,
    ){
        composable(Screen.HOME.route){ navBackStackEntry ->
            val viewModel: HomeViewModel = hiltViewModel(navBackStackEntry)

            HomeScreen(
                viewModel = viewModel,
                modifier = Modifier.padding(
                  top =contentPadding.calculateTopPadding(),
                    bottom = contentPadding.calculateBottomPadding()
                )

            ){productId ->
                navController.navigate("${Screen.PRODUCT.route}/$productId")
            }
        }


        composable(Screen.COUPON.route){ navBackStackEntry ->
            val viewModel: CouponViewModel = hiltViewModel(navBackStackEntry)
            CouponScreen(
                viewModel = viewModel,
                modifier = Modifier.padding(
                    top =contentPadding.calculateTopPadding(),
                    bottom = contentPadding.calculateBottomPadding()
                )

            )
        }




        composable(Screen.PROFILE.route){ navBackStackEntry ->
            val viewModel: ProfileViewModel = hiltViewModel(navBackStackEntry)

            ProfileScreen(
                viewModel = viewModel,
                modifier = Modifier.padding(
                    top =contentPadding.calculateTopPadding(),
                    bottom = contentPadding.calculateBottomPadding()
                ),


            )
        }



        composable(

            route = "${Screen.PRODUCT.route}/{productId}",
            arguments = listOf(
                navArgument("productId"){type = NavType.IntType}
            )
        ){navBackStackEntry ->
            val viewModel: ProductViewModel = hiltViewModel(navBackStackEntry)




            ProductScreen(
                viewModel = viewModel,
                modifier = Modifier.padding(
                    top =contentPadding.calculateTopPadding(),
                    bottom = contentPadding.calculateBottomPadding()
                ),
                onCouponGenerated = {
                    navController.popBackStack()
                }

            )
        }











    }

}









/*
@Composable
private fun ProfileScreen(modifier: Modifier) {
    Surface(modifier = modifier.fillMaxSize()) {
        Text("PROFILE SCREEN",
            style = MaterialTheme.typography.headlineLarge)
    }
}

 */






@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LightMainScreenPreview() {
    KingBurguerTheme(dynamicColor = false, darkTheme = false) {
        MainScreen(
            onLogoutClick = {},
            onNavigateToProfile = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DarkMainScreenPreview() {
    KingBurguerTheme(dynamicColor = false, darkTheme = true) {
        MainScreen(
            onLogoutClick = {},
            onNavigateToProfile = {}
        )
    }
}

//Chave da API: 2d2df78e-7c0b-4bf2-b1c9-ff0a1963c281
/*
"id": 287,
    "name": "vana",
    "email": "zkodia@gmail.com",
    "document": "9498199312",
    "birthday": "2010-08-24"
 */