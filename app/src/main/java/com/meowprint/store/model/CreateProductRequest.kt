package com.meowprint.store.model.product

data class CreateProductRequest(
    val name: String,
    val description: String,
    val price: Long,
    val stock: Int,
    val brand: String,
    val category: String
)
