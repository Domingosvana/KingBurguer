package com.ango.kingburguer.kingburguer.core.validation

import android.util.Patterns
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.ango.kingburguer.R
import com.ango.kingburguer.kingburguer.auth.presentation.signup.FormState

var formState by mutableStateOf(FormState())


interface TextString{
    //val value: String?

    //@Composable
    ///fun asString():String
    @get:Composable
    val value: String
}

class ResourceString(@StringRes val input:Int):TextString{
    //@Composable
   // override fun asString(): String {
     //   return stringResource(input)
   // }
    override val value: String
        @Composable
        get() = stringResource(input)




}

class RawString(private val input:String):TextString{
//@Composable
  ///  override fun asString(): String {
     //   return input
    //}

    override val value: String
        @Composable
        get() = input


}






class EmailValidator {


    //@StringRes
    fun validate(email: String): TextString? {
        if (email.isBlank()) {
            return  ResourceString(R.string.error_email_blank)

        }

        if (!isEmailValid(email)) {
            return RawString("E-mail invalido ou nao encontrado") //R.string.error_email_invalid


        }
        return null

    }


    private fun isEmailValid(email:String):Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


}



































