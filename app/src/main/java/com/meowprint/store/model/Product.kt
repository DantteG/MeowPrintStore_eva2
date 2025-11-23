import com.google.gson.annotations.SerializedName
import com.meowprint.store.model.product.ImageResource

data class Product(
    val id: Int? = null,
    val name: String = "",
    val description: String = "",
    val price: Long = 0,
    val stock: Int = 0,
    val brand: String = "",
    val category: String = "",
    @SerializedName("images")
    val images: List<ImageResource>? = null
)

data class CartItem(
    val id: Int,
    val name: String,
    val price: Double,
    val quantity: Int = 1
)
