package com.upb.shoplist

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

object ShoppingListStorage {

    private const val PREFS_NAME = "shoplist_prefs"
    private const val KEY_LISTS = "saved_lists"

    fun getLists(context: Context): List<ShoppingList> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_LISTS, "[]") ?: "[]"
        val array = JSONArray(json)
        val lists = mutableListOf<ShoppingList>()

        for (i in 0 until array.length()) {
            val item = array.getJSONObject(i)
            val productsArray = item.optJSONArray("products") ?: JSONArray()
            val products = mutableListOf<Product>()

            for (j in 0 until productsArray.length()) {
                val productJson = productsArray.getJSONObject(j)
                products.add(
                    Product(
                        id = productJson.optString("id"),
                        name = productJson.optString("name"),
                        category = productJson.optString("category"),
                        quantity = productJson.optInt("quantity", 0),
                        price = productJson.optDouble("price", 0.0),
                        isPurchased = productJson.optBoolean("isPurchased", false)
                    )
                )
            }

            lists.add(
                ShoppingList(
                    id = item.optString("id"),
                    name = item.optString("name"),
                    products = products,
                    createdAt = item.optLong("createdAt", System.currentTimeMillis())
                )
            )
        }

        return lists.sortedByDescending { it.createdAt }
    }

    fun saveOrUpdateList(context: Context, list: ShoppingList) {
        val currentLists = getLists(context).toMutableList()
        val index = currentLists.indexOfFirst { it.id == list.id }

        if (index >= 0) {
            currentLists[index] = list
        } else {
            currentLists.add(0, list)
        }

        val array = JSONArray()
        currentLists.forEach { shoppingList ->
            val productsArray = JSONArray()
            shoppingList.products.forEach { product ->
                productsArray.put(
                    JSONObject().apply {
                        put("id", product.id)
                        put("name", product.name)
                        put("category", product.category)
                        put("quantity", product.quantity)
                        put("price", product.price)
                        put("isPurchased", product.isPurchased)
                    }
                )
            }

            array.put(
                JSONObject().apply {
                    put("id", shoppingList.id)
                    put("name", shoppingList.name)
                    put("products", productsArray)
                    put("createdAt", shoppingList.createdAt)
                }
            )
        }

        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_LISTS, array.toString()).apply()
    }
}