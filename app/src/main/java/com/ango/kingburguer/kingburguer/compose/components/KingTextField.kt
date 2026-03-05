package com.ango.kingburguer.kingburguer.compose.components


import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.ango.kingburguer.R
import com.ango.kingburguer.ui.theme.KingBurguerTheme
@Composable
fun KingTextField(
    value: String,
    @StringRes label: Int,
    @StringRes  placeholder: Int,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    obfuscate: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    error: String? = null
) {

    KingTextField(
        value = TextFieldValue(value, selection = TextRange(value.length)),
        label ,
        placeholder,
        keyboardType,
        imeAction,
        obfuscate,
        trailingIcon,
        modifier ,
        onValueChange = {
            onValueChange(it.text)
                        },
        error
    )

}






@Composable
fun KingTextField(
    value: TextFieldValue,
    @StringRes    label: Int,
    @StringRes  placeholder: Int,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    obfuscate: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    onValueChange: (TextFieldValue) -> Unit,
    error: String? = null
) {

    OutlinedTextField(
        modifier = modifier.fillMaxSize(),
        value = value,
        onValueChange = onValueChange,
        maxLines = 1,
        label = { Text(text = stringResource(id = label)) },
        placeholder = { Text(text = stringResource(id = placeholder)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        trailingIcon = trailingIcon,
        isError = error != null,
        supportingText = {
            error?.let { msg->
                Text(msg)
            }
        },


        //ofuscar o input
        visualTransformation = if (obfuscate) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        }
    )
}



@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    KingBurguerTheme {
        KingTextField(
            value =TextFieldValue(""),
            label = R.string.hint_email,
            placeholder = R.string.hint_email,
            keyboardType= KeyboardType.Password,
            imeAction =ImeAction.Done ,
            obfuscate = true,
            trailingIcon = {},
            modifier = Modifier,
            onValueChange = {}
        )
    }
}
