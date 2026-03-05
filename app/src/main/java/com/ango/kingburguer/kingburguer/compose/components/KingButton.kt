package com.ango.kingburguer.kingburguer.compose.components


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun KingButton(
    modifier : Modifier = Modifier,
    text:String,
    enabled: Boolean = true,
    onClick:() -> Unit,
    loading: Boolean = false
) {

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        Button(
            onClick = onClick,
            //ele vai ficar ativado se enabled estiver realmente ativado
            //se realmente ele nao for carregando
            enabled = enabled && !loading,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
        ){
            // para nao mostrar o texto se estiver carregando
            if (!loading){
                Text(text.uppercase())
            }

        }
    }
if (loading){
    CircularProgressIndicator(
        modifier = Modifier.size(24.dp).scale(0.7f),
        color = Color.Gray

    )
}



}


@Preview(showBackground = true)
@Composable
private fun KingButtonPreview() {
    Column(){
        KingButton(text = "button", enabled = true, onClick = {}, loading  = false )
        KingButton(text = "button", enabled = false, onClick = {}, loading =true)
        KingButton(text = "button", enabled = true, onClick = {}, loading =true)
        KingButton(text = "button", enabled = true, onClick = {}, loading =true)
    }

    KingButton(text = "button", enabled = true, onClick = {})

}
