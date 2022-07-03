package com.facd.matchessimulatorapp.domain


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Place(
    @SerializedName("imagem")
    val image: String,
    @SerializedName("nome")
    val name: String
): Parcelable