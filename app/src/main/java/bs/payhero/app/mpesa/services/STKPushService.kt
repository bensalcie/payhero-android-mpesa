package bs.payhero.app.mpesa.services

import bs.payhero.app.mpesa.model.AccessToken
import bs.payhero.app.mpesa.model.STKPush
import bs.payhero.app.mpesa.model.STKResponse
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