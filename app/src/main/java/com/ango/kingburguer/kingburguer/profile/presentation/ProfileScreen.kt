package com.ango.kingburguer.kingburguer.profile.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ango.kingburguer.R
import com.ango.kingburguer.kingburguer.commons.formatted


import com.ango.kingburguer.kingburguer.core.validation.Mask
import com.ango.kingburguer.kingburguer.profile.data.ProfileResponse
import com.ango.kingburguer.ui.theme.KingBurguerTheme



@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel()
) {


    val state = viewModel.uiState.collectAsState().value
    ProfileScreen(
        modifier = modifier,
        state = state
    )




}


@Composable
fun ProfileScreen(
    modifier: Modifier,
    state: ProfileUiState
) {

    Box(modifier = Modifier
        .fillMaxSize(), contentAlignment = Alignment.Center ){
        when{
            state.isLoading ->{
                CircularProgressIndicator()

        }
            state.errorMessage != null ->{
                Text(
                    text = state.errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }

            state.urserProfile != null ->{
                ProfileScreen(
                    modifier = modifier,
                    profile = state.urserProfile


                )
            }
        }

    }

}




@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    profile: ProfileResponse

) {

    Surface(modifier = modifier.fillMaxSize()){

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(start = 20.dp, end = 20.dp, top = 16.dp),
        ) {
            ProfileProperty(R.string.prop_id,profile.id)
            ProfileProperty(R.string.prop_name,profile.name)
            ProfileProperty(R.string.prop_email,profile.email)
            ProfileProperty(R.string.prop_document, Mask("###.###.###-##","",profile.document))
            ProfileProperty(R.string.prop_birthday,profile.birthday.formatted())
        }


    }



}


@Composable
fun    ProfileProperty(@StringRes key:Int, value: Any) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(text = stringResource(id = key),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(text = value.toString(),
            color = MaterialTheme.colorScheme.inverseSurface
        )

    }
    Divider(modifier = Modifier.padding(vertical = 14.dp),thickness = 0.8.dp)


}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LightProfileScreenPreview() {
    KingBurguerTheme(dynamicColor = false, darkTheme = false) {
        ProfileScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DarkProfileScreenPreview() {
    KingBurguerTheme(dynamicColor = false, darkTheme = true) {
        ProfileScreen()
    }
}


