package io.github.shvmsaini.sneakership.repository

import android.content.Context
import com.google.gson.Gson
import io.github.shvmsaini.sneakership.database.CartDatabase
import io.github.shvmsaini.sneakership.models.Cart
import io.github.shvmsaini.sneakership.models.Sneaker
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream

class SneakersRepository(private val context: Context) {
    fun getSneakers(): ArrayList<Sneaker> {
        val jsonArray = JSONArray(loadJSONFromAsset())
        val list = ArrayList<Sneaker>()
        val listOfColor = listOf("Blue", "Black", "Red")
        val listOfName = listOf("Nike Air", "Puma Max", "Adidas Rock")
        val listOfImage = listOf("https://i.imgur.com/w2ePptI.png", "https://i.imgur.com/4SVUQOU.png", "https://i.imgur.com/qQ4tyME.png")
        val listOfSize = listOf(7, 8, 9)
        repeat(100) {
            val jsonObject = jsonArray.getJSONObject(0)
            val product = Gson().fromJson(jsonObject.toString(), Sneaker::class.java)
            product.id = it.toString()
            product.retailPrice = (it * 100) + 99
            product.size = listOfSize[it % 3]
            product.name = listOfName[it % 3]
            product.colorway = listOfColor[it % 3]
            product.media?.imageUrl = listOfImage[it % 3]
            product.media?.smallImageUrl = listOfImage[it % 3]
            product.media?.thumbUrl = listOfImage[it % 3]
            list.add(product)
        }
        return list
    }

    fun removeFromCart(item: Cart) {
        val cartDao = CartDatabase.getDatabase(context).CartDao()
        cartDao.delete(item.id!!)
    }

    fun addToCart(item: Cart) {
        val cartDao = CartDatabase.getDatabase(context).CartDao()
        cartDao.insert(item)
    }

    fun getCart(): ArrayList<Cart> {
        val cartDao = CartDatabase.getDatabase(context).CartDao()
        return cartDao.getAll() as ArrayList<Cart>
    }

    private fun loadJSONFromAsset(): String? {
        val json: String? = try {
            val inputStream: InputStream = context.assets.open("sneakers.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }
}