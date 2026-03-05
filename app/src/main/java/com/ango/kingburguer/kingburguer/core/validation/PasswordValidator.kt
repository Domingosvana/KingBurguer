package com.ango.kingburguer.kingburguer.core.validation

import com.ango.kingburguer.R





class PasswordValidator {
    fun validate( password: String):TextString?{
        if(password.isBlank()){
            return ResourceString(R.string.error_password_blank)


        }


        else if(password.length < 8){
            return  ResourceString(R.string.error_password_length)


        }





        return null
    }




    fun validate(confirmPassword: String, password: String): TextString? {
        if (password.isBlank()) {
            return ResourceString(R.string.error_password_blank)
        }

        if (password.length < 8) {
            return ResourceString(R.string.error_comfirm_password_blank)
        }

        if (confirmPassword.isNotBlank() && confirmPassword != password) {
            return ResourceString(R.string.error_confirm_password_invalid)
        }

        return null
    }










}




