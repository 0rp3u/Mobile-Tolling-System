package pt.isel.ps.g30.tollingsystem.interceptor

import android.util.Base64

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HttpAuthInterceptor : Interceptor {

    private lateinit var httpUsername: String
    private lateinit var httpPassword: String


    fun setCredentials(httpUsername: String, httpPassword: String){
        this.httpPassword = httpPassword
        this.httpUsername = httpUsername
    }

    private val authorizationValue: String
        get() {
            val userAndPassword = "httpUsername:$httpPassword"
            return "Basic ${Base64.encodeToString(userAndPassword.toByteArray(), Base64.NO_WRAP)}"
        }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", authorizationValue)
                .build()

        return chain.proceed(newRequest)
    }
}