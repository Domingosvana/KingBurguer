package com.ango.kingburguer.kingburguer.core.validation

import com.ango.kingburguer.R


class DocumentValidator{
    private  val pattern = "###.###.###-##"   //Regex("[0-9]+")
    //val currentDocument = formState.document.field
    lateinit var result :String


    fun validate(currentDocument: String, document: String): TextString? {
        result = Mask(pattern, currentDocument, document)




        if (result.isBlank()) {
            return  ResourceString(R.string.error_document_blank)
        }


        if(result.length != pattern.length) {
            return ResourceString(R.string.error_document_invalid)


        }

        return null

        }




    }
