package com.facd.matchessimulatorapp.domain


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Team(
    @SerializedName("forca")
    val stars: Int,
    @SerializedName("imagem")
    val image: String,
    @SerializedName("nome")
    val name: String,
    var score: Int?
): Parcelable