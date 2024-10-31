package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model


import com.google.gson.annotations.SerializedName

data class PreferencesResponse(
    @SerializedName("data")
    val `data`: List<String?>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)