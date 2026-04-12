package com.example.finalprojectkotlin.dataClass

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Home : Screen("home")
    object Cart : Screen("cart")
    object FavoritesScreen : Screen("fav")
    object CategoriesScreen : Screen("category")
    object CategoryProducts : Screen("categoryProducts")
    object ProfileScreen : Screen("profile")


    object ProductDetails : Screen("product_details") {
        fun passId(id: Int): String {
            return "product_details/$id"
        }
    }

}