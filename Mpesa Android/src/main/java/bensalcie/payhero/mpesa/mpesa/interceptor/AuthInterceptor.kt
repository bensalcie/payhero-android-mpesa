package bensalcie.payhero.mpesa.mpesa.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class AuthInterceptor(private val mAuthToken: String) :Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        val request: Request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $mAuthToken")
            .build()
        return chain.proceed(request)
    }
}