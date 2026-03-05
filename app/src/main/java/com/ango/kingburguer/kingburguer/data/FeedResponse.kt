package com.ango.kingburguer.kingburguer.data

import com.google.gson.annotations.SerializedName
import java.util.Date

/*
"categories": [
        {
            "id": 1,
            "name": "Vegetariano",
            "products": [
                {
                    "id": 4,
                    "name": "Combo KB Kiss",
                    "description": "0% carne. Pão com gergelim, maionese, alface, tomate, cebola, ketchup, picles, cheddar fatiado e hambúrguer à base de proteína vegetal.\n\nImagem meramente ilustrativa.",
                    "picture_url": "https://cdn.tiagoaguiar.co/images/king-burguer/combo-kiss.png",
                    "price": 31.9,
                    "created_date": "2023-02-16"
                }
            ]
        },

 */

data class FeedResponse(
    val categories: List<CategoryResponse>

)


data class CategoryResponse(
    val id: Int,
    val name:String,
    val products: List<ProductResponse>
)

data class ProductResponse(
    val id: Int,
    val name: String,
    val description: String,
    @SerializedName("picture_url")
    val pictureUrl: String,
    val price: Double,
    @SerializedName("created_date")
    val createdDate: Date,

)
