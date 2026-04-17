# Lista de Mercado App

## Descripción corta

Organiza tus compras, crea listas y guarda tu historial sin internet.

## Descripción larga

¿Cansado de olvidar productos en el supermercado o perder tus listas en papel? Lista de Mercado es la aplicación que necesitas para organizar tus compras de forma rápida, sencilla y completamente offline. Solo tienes que registrarte una vez y podrás crear todas las listas que quieras, agregar productos con su categoría, cantidad y precio estimado, e ir marcando cada artículo a medida que lo vas agregando al carrito. Cuando terminas tus compras, la lista se guarda automáticamente en tu historial para que puedas consultarla cuando quieras. ¿Necesitas volver a comprar lo mismo? Puedes restaurar cualquier lista anterior y seguir usándola sin tener que volver a escribir todo. La aplicación también incluye un perfil de usuario, pantalla de créditos y un diseño limpio y moderno basado en Material 3. Todo funciona sin conexión a internet, porque tus listas se guardan directamente en tu teléfono. Ideal para el día a día, para entregas académicas o para cualquier persona que quiera tener el control de sus compras sin complicaciones.

## Tecnologías

- **Lenguaje:** Kotlin
- **UI:** Jetpack Compose, Material 3
- **Navegación:** Navigation Compose (flujo inicial: splash, registro, login y recuperación de contraseña)
- **Almacenamiento local:** `SharedPreferences` para sesión de usuario y persistencia de listas y productos (JSON serializado)
- **Datos de ejemplo:** usuarios de prueba en `assets` (`users.json`) para autenticación local
- **IDE:** Android Studio

## Estructura del proyecto

```
Entrega_3/
├── README.md
└── ShopList_Project/                 # Módulo Android (Gradle Kotlin DSL)
    ├── app/
    │   ├── build.gradle.kts
    │   └── src/main/
    │       ├── AndroidManifest.xml
    │       ├── assets/data/
    │       │   ├── users.json        # Credenciales de demostración
    │       │   └── diseños_app/      # Recursos gráficos del diseño
    │       ├── java/com/upb/shoplist/
    │       │   ├── *ActivityCompose.kt   # Pantallas principales (Compose)
    │       │   ├── AppNavigation.kt        # NavHost (splash, login, registro, recuperar)
    │       │   ├── SessionManager.kt       # Sesión (nombre, email, estado de login)
    │       │   ├── ShoppingListStorage.kt  # CRUD de listas en preferencias
    │       │   ├── ShoppingList.kt, Product.kt
    │       │   └── ui/theme/               # Tema, colores y tipografía
    │       └── res/                        # Drawables, strings, menús, temas
    ├── gradle/
    │   └── libs.versions.toml        # Versiones centralizadas
    ├── build.gradle.kts
    └── settings.gradle.kts
```

**Paquete de la app:** `com.upb.shoplist`  
**Requisitos orientativos:** `minSdk` 24, `targetSdk` / `compileSdk` 36 (según `app/build.gradle.kts`).

## Funcionalidades

- **Pantalla de bienvenida (splash)** y acceso a **inicio de sesión** o **registro**.
- **Login** validado contra el archivo local `users.json`; al iniciar sesión se guarda la sesión (nombre y correo).
- **Registro de usuario** y **recuperación de contraseña** (flujo en Compose con navegación).
- **Inicio (home):** visualización de listas guardadas, con carga desde almacenamiento local.
- **Crear listas** y **ver detalle** de cada lista.
- **Agregar y editar productos** con campos como nombre, categoría, cantidad, precio y estado comprado.
- **Historial** de listas y **detalle desde historial**.
- **Perfil** de usuario y **pantalla de créditos**.

Los datos de las listas se serializan a JSON y se guardan en preferencias; no se requiere backend para el uso básico de la app.

## Diseño

El diseño fue creado en Figma, asi como sus vistas con el prototipo y su posible navegacion de la aplicacion

### Figma

https://www.figma.com/design/sqAzNwD2AC3ipsWivzR166/Entrega-3?node-id=0-1&t=VVoUUNwS1eLin2mk-1

## Cómo ejecutar el proyecto

1. Abrir la carpeta `ShopList_Project` en Android Studio.
2. Sincronizar Gradle y ejecutar la app en un emulador o dispositivo con API 24 o superior.

## Autores

1. **Nicolas Mantilla Gelves**
2. **_(Nombre del autor 2)_**
3. **_(Nombre del autor 3)_**
