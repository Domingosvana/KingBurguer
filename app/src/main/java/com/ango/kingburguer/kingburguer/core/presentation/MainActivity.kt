package com.ango.kingburguer.kingburguer.core.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ango.kingburguer.kingburguer.core.presentation.Screen
import com.ango.kingburguer.ui.theme.KingBurguerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: SplashViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                Log.d("Splash", "onCreate keep on: ${viewModel.hasSession.value}")
                viewModel.hasSession.value == null
            }
        }
        setContent {
            KingBurguerTheme(dynamicColor = false) {

                Box(modifier = Modifier.Companion.safeDrawingPadding()) {

                    val startState = viewModel.hasSession.collectAsState()

                    startState.value?.let { logged ->
                        val startDestination = if (logged) Screen.MAIN else Screen.LOGIN
                        Log.d("Splash", "onCreate: $startDestination")

                        KingBurguerApp(startDestination = startDestination)


                    }

                }
            }





        }
    }
}