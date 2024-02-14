package io.github.shvmsaini.sneakership.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.github.shvmsaini.sneakership.models.Cart

@Dao
interface CartDao {
    @Query("SELECT * FROM Cart")
    fun getAll(): List<Cart>

    @Insert
    fun insert(item: Cart)

    @Query("DELETE FROM Cart WHERE id=:id")
    fun delete(id: String)
}