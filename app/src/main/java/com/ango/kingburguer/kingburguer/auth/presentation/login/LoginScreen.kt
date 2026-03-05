package com.ango.kingburguer.kingburguer.auth.presentation.login




import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState

import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState

import androidx.lifecycle.viewmodel.compose.viewModel
import com.ango.kingburguer.R
import com.ango.kingburguer.kingburguer.compose.components.KingAlert
import com.ango.kingburguer.kingburguer.compose.components.KingButton
import com.ango.kingburguer.kingburguer.compose.components.KingTextField
import com.ango.kingburguer.kingburguer.compose.components.KingTextTitle

import com.ango.kingburguer.ui.theme.KingBurguerTheme


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onSignUpClick: () -> Unit,
    onNavigateToHome : () -> Unit,
    //viewModel: LoginViewModel = viewModel(factory = LoginViewModel.factory)
) {
    Surface(modifier = Modifier.fillMaxSize()) {

        val scrollState = rememberScrollState()
        var passwordHidden by remember { mutableStateOf(true) }

    //
        val uiState by viewModel.uiState.collectAsState()
Column() {

        Column(
            verticalArrangement = Arrangement.spacedBy(14.dp, Alignment.Top),
           // verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 20.dp)

                .verticalScroll(scrollState)
        ) {

            LaunchedEffect(key1 = uiState.goToHome) {
                if(uiState.goToHome){
                    onNavigateToHome()
                    viewModel.reset()
                }
            }


          //   if(state.value.goToHome){
            //     KingTextTitle("Navegar para HOME")
          //   }

        //     else if(state.value.error != null){

            uiState.error?.let {
                KingAlert(
                    onDismissRequest = { /*TODO*/ },
                    onConfirmation = {
                        viewModel.reset()
                    },
                    dialogTitle = stringResource(id = R.string.app_name),
                    dialogText = it,
                    icon = Icons.Filled.Info
                )
            }





        //         KingTextTitle("Erro de requisicao")
          //   }



         //   else if (state.value.isLoading){
           //     KingTextTitle("Carregando")
           // }

            //    KingTextTitle("Logado para proxima tela")



            //LOGIN FICA NO TOP
            KingTextTitle(
                text = "login"
            )

            KingTextField(
                value = viewModel.formState.email.field,
                onValueChange = { viewModel.updateEmail(it) },
                label =R.string.email,
                placeholder =R.string.hint_email,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                error = viewModel.formState.email.error?.value,
                modifier = Modifier//.padding(horizontal = 20.dp)
            )

            KingTextField(
                value =viewModel.formState.password.field ,
                onValueChange = {viewModel.updatePassword(it)},
                label = R.string.password,
                placeholder =R.string.hint_password ,//"Digite sua senha",
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                error = viewModel.formState.password.error?.value,
                obfuscate = passwordHidden,
                modifier = Modifier,//.padding(horizontal = 20.dp),
                trailingIcon = {
                    IconButton(onClick = { passwordHidden = !passwordHidden }) {
                        val image = if (passwordHidden) {
                            Icons.Filled.VisibilityOff
                        } else {
                            Icons.Filled.Visibility
                        }
                        val description = if (passwordHidden) {
                            stringResource(id = R.string.show_password)
                        } else {
                            stringResource(id = R.string.hide_password)
                        }

                        Icon(imageVector = image, contentDescription = description)
                    }
                }
            )

            //REMEMBER ME

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Checkbox(
                    checked = viewModel.formState.rememberMe,
                    onCheckedChange = {viewModel.updateRememberMe(it)}
                )

                Text(stringResource(id = R.string.remember_me))
            }

            // ENVIAR

            KingButton(
                enabled = viewModel.formState.formIsValid,
                onClick = {viewModel.send()},
                text =stringResource(id = R.string.send) ,
                loading = uiState.isLoading
            )

            //CADASTRAR SE NAO TEM CONTA

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(stringResource(id = R.string.have_account))
                TextButton(onClick = onSignUpClick) {
                    Text(stringResource(id = R.string.sign_up))
                }
            }
        }

            //IMAGEM

            Image(
                modifier = Modifier.fillMaxSize(),
                alignment = Alignment.BottomCenter,
                painter = painterResource(id = R.drawable.cover3),contentDescription = "cover"
            )



        }


    }
}


@Preview(showBackground = true,showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    KingBurguerTheme(dynamicColor = false,darkTheme = false) {
        LoginScreen(
            onSignUpClick = {},
            viewModel = viewModel(),
            onNavigateToHome = {}
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginScreenPreviewLDark() {
    KingBurguerTheme(dynamicColor = false,darkTheme = true) {
        LoginScreen(
            onSignUpClick = {},
            viewModel = viewModel(),
            onNavigateToHome ={}
        )
    }
}
