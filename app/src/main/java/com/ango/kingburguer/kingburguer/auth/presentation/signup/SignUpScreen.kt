package com.ango.kingburguer.kingburguer.auth.presentation.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ango.kingburguer.R
import com.ango.kingburguer.kingburguer.compose.components.KingAlert
import com.ango.kingburguer.kingburguer.compose.components.KingButton
import com.ango.kingburguer.kingburguer.compose.components.KingTextField
import com.ango.kingburguer.kingburguer.compose.components.KingTextTitle
import com.ango.kingburguer.ui.theme.KingBurguerTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    viewmodel:SignUpViewModel = viewModel(),
    onNavigationClick: () ->Unit,
    onNavigateToLogin: () -> Unit,
) {

    Surface(modifier = Modifier.fillMaxSize()){
        Scaffold(
            topBar = {
                TopAppBar(
                  title = {Text(stringResource(id = R.string.login))},
                    navigationIcon = {
                        IconButton(onClick = onNavigationClick) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = stringResource(id = R.string.back)
                            )
                        }
                    },
                   colors = TopAppBarDefaults.topAppBarColors(
                       containerColor = MaterialTheme.colorScheme.primary,
                       titleContentColor = MaterialTheme.colorScheme.onPrimary,
                       navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                   )
                )
            }
        ) { contentPadding ->
            SignUpContentScreen(
                //viewModel = viewmodel,
                modifier = Modifier.padding(top = contentPadding.calculateTopPadding()),
                viewmodel = viewmodel,
                onNavigateToLogin = onNavigateToLogin


            )
        }

    }

}

@Composable
private fun SignUpContentScreen(
    modifier: Modifier,
    viewmodel:SignUpViewModel,
    onNavigateToLogin:() -> Unit,
   // error: String? = null
) {
    Surface(modifier = Modifier.fillMaxSize()) {

        val scrollState = rememberScrollState()
        var passwordHidden by remember { mutableStateOf(true) }

        val state by viewmodel.uiState.collectAsState()
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)

                .verticalScroll(scrollState)
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp, Alignment.Top),
                // verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
               // modifier = Modifier
                 //   .padding(horizontal = 20.dp)

                  //  .verticalScroll(scrollState)
            ) {

                 /*   LaunchedEffect(key1 = state.goToLogin) {
                        if(state.goToLogin){
                            onNavigateToLogin()
                            viewmodel.reset()
                        }
                    }

                  */


                if(state.goToLogin){

                    KingAlert(
                        onDismissRequest ={},
                        onConfirmation = {onNavigateToLogin()
                            viewmodel.reset()},
                        dialogTitle = stringResource(id = R.string.app_name),
                        dialogText = stringResource(id = R.string.user_created),
                        icon = Icons.Filled.Info
                    )


                }




                //   if(state.goToHome){
                      // onNavigateToHome()
                //     KingTextTitle("Navegar para HOME")
                 //  }

                //     else if(state.value.error != null){

                state.error?.let {
                    KingAlert(
                        onDismissRequest ={viewmodel.reset()},
                        onConfirmation = {viewmodel.reset()},
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
                    text = stringResource(id = R.string.signup)
                )

                KingTextField(
                    value = viewmodel.formState.email.field,
                    onValueChange = {viewmodel.updateEmail(it)},
                    label =  R.string.email,
                    placeholder = R.string.hint_email,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                    modifier = Modifier,//.padding(horizontal = 20.dp)
                    error = viewmodel.formState.email.error?.value //asString()

                )


                KingTextField(
                    value = viewmodel.formState.name.field,
                    onValueChange = {viewmodel.updateName(it)},
                    label = R.string.Nome,
                    placeholder = R.string.hint_name,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    modifier = Modifier,//.padding(horizontal = 20.dp)
                    error = viewmodel.formState.name.error?.value


                )
                            KingTextField(
                    value = viewmodel.formState.password.field,
                    onValueChange = {viewmodel.updatePassword(it)},
                    label = R.string.password,
                    placeholder =  R.string.hint_password,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next,
                    obfuscate = passwordHidden,
                    error = viewmodel.formState.password.error?.value,//asString(),
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





                KingTextField(
                    value = viewmodel.formState.confirmPassword.field,
                    onValueChange = {viewmodel.updateComfirmPassword(it)},
                    label = R.string.alert_confirm,
                    placeholder = R.string.hint_comfirm_password,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next,
                    obfuscate = passwordHidden,
                    error = viewmodel.formState.confirmPassword.error?.value,//asString(),
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




                KingTextField(
                    value = TextFieldValue(
                        viewmodel.formState.document.field,
                        selection = TextRange(viewmodel.formState.document.field.length)
                    ) ,
                    onValueChange = {textFieldValue ->
                        viewmodel.UpdateDocument(textFieldValue.text)


                                    },
                    label =R.string.document,
                    placeholder =  R.string.hint_comfirm_CPF,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                    modifier = Modifier,//.padding(horizontal = 20.dp)
                    error = viewmodel.formState.document.error?.value,//asString()
                )


                KingTextField(
                    value = TextFieldValue(
                        viewmodel.formState.birthday.field,
                        selection = TextRange(viewmodel.formState.birthday.field.length)
                    ),
                    onValueChange = {textFieldValue ->
                        viewmodel.updateBirthday(textFieldValue.text)
                    },
                    label =R.string.birthday,
                    placeholder = R.string.hint_birthday,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                    modifier = Modifier,//.padding(horizontal = 20.dp)
                    error = viewmodel.formState.birthday.error?.value//asString()
                )





































                //REMEMBER ME
/*
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Checkbox(
                        checked = true,
                        onCheckedChange = {}
                    )

                    Text(text = "remember-me")
                }

 */

                // ENVIAR

                KingButton(
                    enabled = viewmodel.formState.formIsValid ,
                    onClick = {viewmodel.send()},
                    text = stringResource(id = R.string.signup),
                    loading = state.isLoading
                )

                //CADASTRAR SE NAO TEM CONTA
/*
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = "have account?")
                    TextButton(onClick = onSignUpClick) {
                        Text(text = "sign-up")
                    }


                }

 */
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
private fun SignUpContentScreenPreview() {
    KingBurguerTheme(dynamicColor = false,darkTheme = false) {
        SignUpScreen(
            viewmodel = viewModel(),
            onNavigationClick = {},
            onNavigateToLogin ={}
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SignUpContentPreviewLDark() {
    KingBurguerTheme(dynamicColor = false,darkTheme = true) {
        SignUpScreen(
            viewmodel = viewModel(),
            onNavigationClick = {},
            onNavigateToLogin ={}
        )
    }
}



















