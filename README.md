MeowPrint Store - Android project (skeleton)
Structure
app/src/main/java/com/meowprint/store/api → Retrofit, TokenManager, APIs

app/src/main/java/com/meowprint/store/model → Data classes (Login, Product)

app/src/main/java/com/meowprint/store/ui → Activities & Fragments

app/src/main/res/layout → XML layouts

How to use
Open Android Studio Narwhal 2025.1.1 Patch 1.

Import the project folder meowprint_store_project o crea un nuevo proyecto y reemplaza el módulo app.

Sync Gradle. Agrega el plugin kotlin-kapt si usas Glide:

gradle
plugins {
    id("kotlin-kapt")
}
Reemplaza placeholders (íconos, applicationId) según tu necesidad.

Ejecuta en dispositivo/emulador. Usa credenciales válidas de Xano para login.

Endpoints used
Auth base: https://x8ki-letl-twmt.n7.xano.io/api:eg9IqgHd/

Store base: https://x8ki-letl-twmt.n7.xano.io/api:Bcv1qHHX/

Ejemplos típicos de endpoints disponibles en Xano:

POST /auth/login → Login de cliente

GET /store/products → Listado de productos

POST /store/products → Crear producto (admin)

GET /store/users → Listado de usuarios (admin)

Notes
El código es un skeleton funcional adaptado a tus endpoints de Xano.

Si el campo de respuesta del login para el token difiere, actualiza la extracción del token en MainActivity.

El perfil de administrador usa credenciales fijas:

Contraseña: 1111
