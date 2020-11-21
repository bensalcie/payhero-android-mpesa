# Payhero Android MPESA Library [![](https://jitpack.io/v/bensalcie/payhero-android-mpesa.svg)](https://jitpack.io/#bensalcie/payhero-android-mpesa)
  Android MPESA library to request STK Push using MPESA Daraja API.
# Screenshots
  <p float="center">
  <img src="https://github.com/bensalcie/payhero-android-mpesa/blob/main/screen.jpg" width="200" /> 
  <img src="https://github.com/bensalcie/payhero-android-mpesa/blob/main/screentwo.jpg" width="200" />
  </p>
(Please note that this library is still under development,Feel free to contact me personally if 
you meet any challenge,Thanks)

## How to use the library

## Step 1. Add the JitPack repository to your build file
  Add it in your root build.gradle at the end of repositories:
  ```kotlin
    allprojects {
      repositories {
        ...
        maven { url 'https://jitpack.io' }
      }
    }
   ```
##  Step 2. Add the dependency
```kotlin
    dependencies {
            implementation 'com.squareup.retrofit2:retrofit:2.5.0'
            implementation 'com.github.bensalcie:payhero-android-mpesa:0.1.6'
    }
  ```
 ## Step 3. Add this in onCreate() method.
 ```kotlin
      private var mApiClient: DarajaApiClient? = null //Intitialization before on create

          mApiClient = DarajaApiClient(
            "xxxconsumerkeyxxxxx",
            "xxxxxconsumersecretxxxx",
            Environment.SANDBOX
        )
        //use Environment.PRODUCTION when on production
      //get consumerkey and secret from https://developer.safaricom.co.ke/user/me/apps
          mApiClient!!.setIsDebug(true) //Set True to enable logging, false to disable.
          getAccessToken()//make request availabe and ready for processing.
 ```
## Step 4.Define access token method.
```kotlin
    //Access token Method being called.
     private fun getAccessToken() {
     mApiClient!!.setGetAccessToken(true)
     mApiClient!!.mpesaService()!!.getAccessToken().enqueue(object : Callback<AccessToken> {
     override fun onResponse(call: Call<AccessToken?>, response: Response<AccessToken>) {
     if (response.isSuccessful) {
          mApiClient!!.setAuthToken(response.body()?.accessToken)
        }
          }
          override fun onFailure(call: Call<AccessToken?>, t: Throwable) {}
      })
        }
  ```
##  Step 5. Initiate STK Push
```kotlin
   btnDeposit.setOnClickListener {
              val amount = etAmount.text.toString()
              val phone =etPhone.text.toString()
                  if (amount.isNotEmpty() && phone.isNotEmpty()) {
                          btnDeposit.text = getString(R.string.processing)
                      performSTKPush(phone, amount)
                  } else {
                      etPhone.error = getString(R.string.errorm)
                      etAmount.error = getString(R.string.errorm)
                  }
              }
    private fun performSTKPush(amount: String, phone_number: String) {
          //Handle progresss here
    //credentials here are test credentials
          val timestamp = Utils.getTimestamp()
          val stkPush = STKPush("acc ref",amount,"business number","callback url",
              Utils.sanitizePhoneNumber(phone_number)!!,"business number",Utils.getPassword("business number", 
        "passkey", timestamp!!)!!
              , Utils.sanitizePhoneNumber(phone_number)!!,timestamp,"Trans. desc",Environment.TransactionType.CustomerPayBillOnline)
          mApiClient!!.setGetAccessToken(false)
           mApiClient!!.mpesaService()!!.sendPush(stkPush).enqueue(object : Callback<STKResponse> {
            override fun onResponse(call: Call<STKResponse>, response: Response<STKResponse>) {
                mProgressDialog!!.dismiss()
                try {
                    if (response.isSuccessful) {
                      //handle response here
                      //response contains CheckoutRequestID,CustomerMessage,MerchantRequestID,ResponseCode,ResponseDescription
                    } else {
                        //Timber.e("Response %s", response.errorBody()!!.string())
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<STKResponse>, t: Throwable) {
                //mProgressDialog!!.dismiss()
                //Timber.e(t)
            }
        })
      }
      
   ```
<a href="https://www.buymeacoffee.com/bensalcie" target="_blank"><img src="https://cdn.buymeacoffee.com/buttons/v2/default-blue.png" alt="Buy Me A Coffee" style="height: 60px !important;width: 217px !important;" ></a>


[See Real Demo Here](https://play.google.com/store/apps/details?id=bs.dicemoney.app)






