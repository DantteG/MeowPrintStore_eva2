package com.meowprint.store.model.product

data class ImageResource(
    val access: String? = null,
    val url: String? = null,
    val path: String,
    val name: String? = null,
    val type: String? = null,
    val size: Long? = null,
    val mime: String? = null,
    val meta: Meta? = null
)
