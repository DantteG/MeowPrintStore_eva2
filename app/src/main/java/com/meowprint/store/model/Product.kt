package com.meowprint.store.model.product

data class Product(
    val id: Int? = null,
    val name: String = "",
    val description: String = "",
    val price: Long = 0,
    val stock: Int = 0,
    val brand: String = "",
    val category: String = "",
    val images: List<ImageResource>? = null
)

