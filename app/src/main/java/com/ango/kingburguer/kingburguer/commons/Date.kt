package com.ango.kingburguer.kingburguer.commons

import java.text.DateFormat
import java.util.Date
import java.util.Locale


fun Date.formatted(): String{
    val df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
    val dfdate = df.format(this)
     return dfdate

}
