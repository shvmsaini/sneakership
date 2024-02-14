package io.github.shvmsaini.sneakership.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.github.shvmsaini.sneakership.models.Cart

@Database(entities = [Cart::class], version = 1)
abstract class CartDatabase : RoomDatabase() {
    abstract fun CartDao(): CartDao

    companion object {
        private var INSTANCE: CartDatabase? = null
        fun getDatabase(context: Context): CartDatabase {
            if (INSTANCE == null) {
                synchronized(CartDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext, CartDatabase::class.java, "cartDB"
                        ).allowMainThreadQueries().build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}