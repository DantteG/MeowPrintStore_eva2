package com.meowprint.store.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.view.*
import android.webkit.MimeTypeMap
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.meowprint.store.api.RetrofitClient
import com.meowprint.store.databinding.FragmentAddProductBinding
import com.meowprint.store.model.product.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream

class AddProductFragment : Fragment() {
    private var _b: FragmentAddProductBinding? = null
    private val b get() = _b!!

    private val api by lazy { RetrofitClient.storeRetrofit(requireContext()).create(com.meowprint.store.api.StoreApi::class.java) }
    private var pickedUris: List<Uri> = emptyList()

    private val pickImages = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
        pickedUris = uris ?: emptyList()
        b.tvPicked.text = "${pickedUris.size} imágenes seleccionadas"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _b = FragmentAddProductBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        b.btnPick.setOnClickListener { pickImages.launch("image/*") }
        b.btnCreate.setOnClickListener { submit() }
    }

    private fun submit() {
        val name = b.etName.text.toString().trim()
        val brand = b.etBrand.text.toString().trim()
        val category = b.etCategory.text.toString().trim()
        val price = b.etPrice.text.toString().trim().toLongOrNull() ?: 0L
        val stock = b.etStock.text.toString().trim().toIntOrNull() ?: 0
        val desc = b.etDesc.text.toString().trim()

        if (name.isEmpty() || price <= 0 || stock < 0) {
            toast("Completa nombre, precio (>0) y stock (>=0)")
            return
        }

        setLoading(true)
        lifecycleScope.launch {
            try {
                val created = api.createProduct(
                    CreateProductRequest(name, desc, price, stock, brand, category)
                )

                val images = if (pickedUris.isNotEmpty()) {
                    val parts = withContext(Dispatchers.IO) { buildMultipartParts(pickedUris) }
                    api.uploadImages(parts)
                } else emptyList()

                val finalProduct = if (images.isNotEmpty()) {
                    api.patchProductImages(created.id!!, PatchImagesRequest(images))
                } else created

                toast("Producto creado: id=${finalProduct.id}, imágenes=${finalProduct.images?.size ?: 0}")
                clearForm()
            } catch (e: Exception) {
                toast("Error: ${e.message}")
            } finally { setLoading(false) }
        }
    }

    private fun buildMultipartParts(uris: List<Uri>): List<MultipartBody.Part> {
        val cr = requireContext().contentResolver
        val parts = mutableListOf<MultipartBody.Part>()
        for (uri in uris) {
            val fileName = guessFileName(uri)
            val mime = cr.getType(uri) ?: "image/jpeg"
            val input: InputStream = cr.openInputStream(uri) ?: continue
            val bytes = input.readBytes()
            val req = bytes.toRequestBody(mime.toMediaTypeOrNull())
            val part = MultipartBody.Part.createFormData("content[]", fileName, req)
            parts += part
        }
        return parts
    }

    private fun guessFileName(uri: Uri): String {
        val ext = MimeTypeMap.getSingleton().getExtensionFromMimeType(requireContext().contentResolver.getType(uri)) ?: "jpg"
        return "img_${System.currentTimeMillis()}.$ext"
    }

    private fun setLoading(v: Boolean) {
        b.progress.visibility = if (v) View.VISIBLE else View.GONE
        b.btnCreate.isEnabled = !v
        b.btnPick.isEnabled = !v
    }

    private fun clearForm() {
        b.etName.text?.clear()
        b.etBrand.text?.clear()
        b.etCategory.text?.clear()
        b.etPrice.text?.clear()
        b.etStock.text?.clear()
        b.etDesc.text?.clear()
        pickedUris = emptyList()
        b.tvPicked.text = "0 imágenes seleccionadas"
    }

    private fun toast(msg: String) = android.widget.Toast.makeText(requireContext(), msg, android.widget.Toast.LENGTH_LONG).show()

    override fun onDestroyView() { _b = null; super.onDestroyView() }
}
