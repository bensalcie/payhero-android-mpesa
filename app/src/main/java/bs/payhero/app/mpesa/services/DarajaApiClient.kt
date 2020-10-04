package bs.payhero.app.mpesa.services

import bs.payhero.app.mpesa.interceptor.AccessTokenInterceptor
import bs.payhero.app.mpesa.interceptor.AuthInterceptor
import bs.payhero.app.mpesa.services.Constants.CONNECT_TIMEOUT
import bs.payhero.app.mpesa.services.Constants.READ_TIMEOUT
import bs.payhero.app.mpesa.services.Constants.WRITE_TIMEOUT
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class DarajaApiClient(
    private val consumerKey: String,
    private val consumerSecret: String,
    private val environment: String
) {
    private var retrofit: Retrofit? = null
    private var isDebug = false
    private var isGetAccessToken = false
    private var mAuthToken: String? = null
    private val httpLoggingInterceptor = HttpLoggingInterceptor()

    fun setIsDebug(isDebug: Boolean): DarajaApiClient? {
        this.isDebug = isDebug
        return this
    }

    fun setAuthToken(authToken: String?): DarajaApiClient? {
        mAuthToken = authToken
        return this
    }

    fun setGetAccessToken(getAccessToken: Boolean): DarajaApiClient? {
        isGetAccessToken = getAccessToken
        return this
    }

    private fun okHttpClient(): OkHttpClient.Builder {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient
            .connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
        return okHttpClient
    }

    private fun getRestAdapter(): Retrofit? {
        val builder = Retrofit.Builder()
        builder.baseUrl(environment)
        builder.addConverterFactory(GsonConverterFactory.create())
        if (isDebug) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
        val okhttpBuilder = okHttpClient()
        if (isGetAccessToken) {
            okhttpBuilder.addInterceptor(AccessTokenInterceptor(consumerKey, consumerSecret))
        }
        if (mAuthToken != null && !mAuthToken!!.isEmpty()) {
            okhttpBuilder.addInterceptor(AuthInterceptor(mAuthToken!!))
        }
        builder.client(okhttpBuilder.build())
        retrofit = builder.build()
        return retrofit
    }

    fun mpesaService(): STKPushService? {
        return getRestAdapter()!!.create(STKPushService::class.java)
    }
}