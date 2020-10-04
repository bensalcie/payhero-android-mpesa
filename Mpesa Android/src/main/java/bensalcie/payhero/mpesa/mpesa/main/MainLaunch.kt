package bensalcie.payhero.mpesa.mpesa.main

import android.util.Log
import bensalcie.payhero.mpesa.mpesa.model.AccessToken
import bensalcie.payhero.mpesa.mpesa.model.MpesaParams
import bensalcie.payhero.mpesa.mpesa.model.STKPush
import bensalcie.payhero.mpesa.mpesa.services.DarajaApiClient
import bensalcie.payhero.mpesa.mpesa.services.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MainLaunch {
    lateinit var mApiClient:DarajaApiClient

    fun MpesaInit(consumerKey:String, consumerSecret:String){
          mApiClient= DarajaApiClient(consumerKey,consumerSecret)

        mApiClient.setGetAccessToken(true)
        mApiClient.mpesaService()!!.getAccessToken().enqueue(object : Callback<AccessToken?> {
            override fun onResponse(call: Call<AccessToken?>, response: Response<AccessToken?>) {
                if (response.isSuccessful) {
                    mApiClient.setAuthToken(response.body()?.accessToken)
                } else {
                    Timber.d(MainLaunch::class.java.simpleName, "TOKEN RESPONSE:${response.message()} \n ${response.body()} ")
                }
            }

            override fun onFailure(call: Call<AccessToken?>, t: Throwable) {}
        })
    }

    fun requestSTKPush(mpesaParams: MpesaParams): Response<STKPush>? {
        var  resresponse:Response<STKPush>?=null
        val timestamp = Utils.getTimestamp()
        mApiClient.setGetAccessToken(false)
        val stkPush = STKPush(mpesaParams.accountReference,mpesaParams.amount,mpesaParams.businessShortCode,mpesaParams.callBackURL,
            Utils.sanitizePhoneNumber(mpesaParams.phoneNumber)!!,mpesaParams.partyB,Utils.getPassword(mpesaParams.businessShortCode, mpesaParams.password, timestamp!!)!!
            , Utils.sanitizePhoneNumber(mpesaParams.phoneNumber)!!,timestamp,mpesaParams.transactionDesc,mpesaParams.transactionType)
        //Sending the data to the Mpesa API, remember to remove the logging when in production.
        mApiClient.mpesaService()!!.sendPush(stkPush).enqueue(object : Callback<STKPush> {
            override fun onResponse(call: Call<STKPush?>, response: Response<STKPush>) {
                try {
                    if (response.isSuccessful) {
                        resresponse= response
                        Timber.d("post submitted to API. %s", response.body())
                    } else {
                        resresponse=response
//                        resultstkPush= STKPush("","","","","","","","","","","")
                        Timber.e("Response %s", response.errorBody()!!.string())
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<STKPush>, t: Throwable) {
                Timber.e(t)
            }
        })
        return  resresponse
    }
}