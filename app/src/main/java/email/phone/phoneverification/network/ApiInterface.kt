package email.phone.phoneverification.network

import email.phone.phoneverification.data.OtpApiRequest
import email.phone.phoneverification.data.OtpApiResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

//
// Created by Dev on 25-09-2023.
//
interface ApiInterface {
    object RetrofitHelper {

        val apiService: ApiInterface
            get() = RetroInstance.getRetroInstance().create(ApiInterface::class.java)
    }

    @Headers("Content-Type: application/json")
    @POST("sendmail")
    fun sendOtp(@Body otpApiRequest: OtpApiRequest): Call<OtpApiResponse>


}