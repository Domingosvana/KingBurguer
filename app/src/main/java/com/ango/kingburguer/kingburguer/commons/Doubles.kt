package com.ango.kingburguer.kingburguer.commons

import java.text.NumberFormat
import java.util.Locale

fun Double.currency():String{
    val formatter = NumberFormat.getCurrencyInstance(Locale("pt","AO"))
    return formatter.format(this)
}