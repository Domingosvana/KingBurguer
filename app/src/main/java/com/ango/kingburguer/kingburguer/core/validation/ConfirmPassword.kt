package com.ango.kingburguer.kingburguer.core.validation

import com.ango.kingburguer.R

class ConfirmPasswordValidator{

    fun validate(password:String,confirmPassword:String):TextString? {
        if (confirmPassword.isBlank()) {
            return ResourceString(R.string.error_comfirm_password_blank)


        }


        if(password.isNotBlank() && confirmPassword != password){
            return ResourceString(R.string.error_comfirm_password_should_same)

        }



        return null
    }


}
