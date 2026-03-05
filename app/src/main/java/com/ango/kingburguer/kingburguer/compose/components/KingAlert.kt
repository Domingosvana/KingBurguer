package com.ango.kingburguer.kingburguer.compose.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
//import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.ango.kingburguer.R

@Composable
fun KingAlert(
    onDismissRequest: () -> Unit,
    onConfirmation:() -> Unit,
    dialogTitle:String,
    dialogText:String,
    icon: ImageVector,
) {


    AlertDialog(
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = stringResource(id = R.string.alert_icon)

            )
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {Text(text = dialogText)},



        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onConfirmation) {
                Text(text = stringResource(id = R.string.alert_confirm))

            }
        }




    )

}



















@Preview(showBackground = true)
@Composable
private fun KingAlertPreview() {
    KingAlert(
        onDismissRequest = {},
        onConfirmation = {},
        dialogTitle = "title",
        dialogText = "text",
        icon  = ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground),
    )
}