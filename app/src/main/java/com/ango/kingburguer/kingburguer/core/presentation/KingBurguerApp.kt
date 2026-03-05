package com.ango.kingburguer.kingburguer.core.presentation



import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ango.kingburguer.kingburguer.auth.presentation.login.LoginScreen
import com.ango.kingburguer.kingburguer.auth.presentation.login.LoginViewModel
import com.ango.kingburguer.kingburguer.auth.presentation.signup.SignUpScreen
import com.ango.kingburguer.kingburguer.auth.presentation.signup.SignUpViewModel
import com.ango.kingburguer.kingburguer.main.presentation.MainScreen
import com.ango.kingburguer.kingburguer.main.presentation.MainViewModel
import com.ango.kingburguer.ui.theme.KingBurguerTheme


@Composable
fun  KingBurguerApp(
    startDestination: Screen
    //navgar com tela de splash
   // viewModel: SplashViewModel = viewModel(factory = SplashViewModel.factory)
) {

    val  navController = rememberNavController()
    KingBurguerNavHost(navController = navController, startDestination = startDestination)

/*

    val hasSessionState = viewModel.hasSessionState.collectAsState(initial = null)
    // null -> nao chamou = tela em branco
    // false -> deslogado = tela de login
    // true -> logado = tela principal

    hasSessionState.value?.let{logged ->
        val startDestination = if(logged) Screen.MAIN else Screen.LOGIN
        KingBurguerNavHost(
            navController =navController,
            startDestination = startDestination
        )




    }
 */


   // KingBurguerNavHost(navController = navController)
}








@Composable
fun KingBurguerNavHost(navController: NavHostController,startDestination: Screen) {
    NavHost(
        navController = navController
        ,startDestination =startDestination.route,//Screen.LOGIN.route
    ){
        composable(Screen.LOGIN.route){ navBackStackEntry ->
            val viewModel: LoginViewModel = hiltViewModel(navBackStackEntry)
            LoginScreen(
                viewModel = viewModel,

                onSignUpClick = { navController.navigate(Screen.SIGNUP.route) },
                onNavigateToHome = {
                    navController.navigate(Screen.MAIN.route){
                        popUpTo(Screen.LOGIN.route){inclusive  = true}
                    }
                },
              //  viewmodel =
            )
        }

        composable(Screen.SIGNUP.route){ navBackStackEntry ->
            val viewModel: SignUpViewModel = hiltViewModel(navBackStackEntry)
            SignUpScreen(
                viewmodel = viewModel,
                onNavigationClick = { navController.navigateUp() },
                onNavigateToLogin ={navController.navigateUp()//{
                 //   popUpTo(Screen.LOGIN.route){inclusive  = true}
               // }
                }
            )
        }

        composable(Screen.MAIN.route){ navBackStackEntry ->
            val viewModel: MainViewModel = hiltViewModel(navBackStackEntry)
            MainScreen(
                viewModel = viewModel,
                onNavigateToLogin = {
                    navController.navigate(Screen.LOGIN.route) {
                        popUpTo(Screen.MAIN.route) { inclusive = true }
                    }
                },
                OnNavigateToProfile= {
                    navController.navigate(Screen.PROFILE.route)
                },

            )
        }









    }


}


@Preview(showBackground = true)
@Composable
private fun KingBurguerAppPreview() {
    KingBurguerTheme{
        KingBurguerApp(
            startDestination = Screen.LOGIN
        )


    }


}



