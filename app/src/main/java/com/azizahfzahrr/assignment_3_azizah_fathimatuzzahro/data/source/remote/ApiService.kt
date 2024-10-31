package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.remote

import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.DetailTravelResponse
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.LoginRequest
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.LoginResponse
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.PreferencesResponse
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.TravelResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("travel")
    suspend fun getTravelData(
        @Query("page") page: Int,
    ): Response<TravelResponse>

    @GET("user/profile")
    suspend fun getUserProfile(): Response<LoginResponse>

    @GET("travel/{id}")
    suspend fun getDestinationDetail(@Path("id") id: Int): Response<DetailTravelResponse>

    @GET("travel/type")
    suspend fun getTravelTypes(): Response<PreferencesResponse>
}