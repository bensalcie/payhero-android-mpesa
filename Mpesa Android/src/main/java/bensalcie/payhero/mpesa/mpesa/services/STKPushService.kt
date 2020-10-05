package bensalcie.payhero.mpesa.mpesa.services

import bensalcie.payhero.mpesa.mpesa.model.AccessToken
import bensalcie.payhero.mpesa.mpesa.model.STKPush
import bensalcie.payhero.mpesa.mpesa.model.STKResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface STKPushService {
    @POST("mpesa/stkpush/v1/processrequest")
    fun sendPush(@Body stkPush: STKPush): Call<STKResponse>

    @GET("oauth/v1/generate?grant_type=client_credentials")
    fun getAccessToken(): Call<AccessToken>
}