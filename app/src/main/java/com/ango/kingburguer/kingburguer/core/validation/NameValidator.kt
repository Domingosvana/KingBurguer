package com.ango.kingburguer.kingburguer.core.validation



import com.ango.kingburguer.R

class NameValidator {

    fun validate(name:String):TextString?{
        if (name.isBlank()) {
            return  ResourceString(R.string.error_name_blank)
        }


        if(name.length < 3){
            return ResourceString(R.string.error_name_length)
        }

        return null

    }

}
