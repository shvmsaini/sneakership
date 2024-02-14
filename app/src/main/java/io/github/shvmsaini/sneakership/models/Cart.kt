package io.github.shvmsaini.sneakership.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cart")
class Cart(
    @PrimaryKey(autoGenerate = true)
    var dbId: Int = 0,
    var id: String?,
    var name: String?,
    var title: String?,
    var retailPrice: Int?,
    var size: Int?,
    var color: String?,
    var image: String?
)
