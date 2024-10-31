package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DetailTravelResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
) {
    data class Data(
        @SerializedName("activity")
        val activity: String?,
        @SerializedName("description")
        val description: String?,
        @SerializedName("duration")
        val duration: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("image")
        val image: String?,
        @SerializedName("location")
        val location: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("popularity")
        val popularity: String?,
        @SerializedName("type")
        val type: String?
    ) : Serializable
}