package com.meowprint.store.model

data class ImageResource(
    val access: String? = null,
    val path: String,
    val name: String? = null,
    val type: String? = null,
    val size: Long? = null,
    val mime: String? = null,
    val meta: Meta? = null,
    val url: String? = null
)

data class Meta(val width: Int? = null, val height: Int? = null)

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

data class CreateProductRequest(
    val name: String,
    val description: String,
    val price: Long,
    val stock: Int,
    val brand: String,
    val category: String
)

data class PatchImagesRequest(
    val images: List<ImageResource>
)
