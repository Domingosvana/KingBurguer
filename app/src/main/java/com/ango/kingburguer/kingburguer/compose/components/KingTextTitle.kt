package com.ango.kingburguer.kingburguer.compose.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun KingTextTitle(
    text:String
) {

    Text(
        text = text,
        fontSize = 34.sp,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Left,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 45.dp)

    )

}













@Preview(showBackground = true)
@Composable
private fun KingTextTitlePreview() {
    KingTextTitle(
        text = "Loging"
    )

}
