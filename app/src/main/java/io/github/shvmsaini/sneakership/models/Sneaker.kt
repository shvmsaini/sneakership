package io.github.shvmsaini.sneakership.models

import java.io.Serializable

data class Sneaker(
    var id: String?,
    var brand: String?,
    var colorway: String?,
    val gender: String?,
    val media: Media?,
    var size: Int?,
    val releaseDate: String?,
    var retailPrice: Int?,
    val styleId: String?,
    var name: String?,
    val title: String?,
    val year: Int?,
    var addedToCart: Boolean = false
) : Serializable

