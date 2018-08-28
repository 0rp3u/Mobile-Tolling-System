package pt.isel.ps.g30.tollingsystem.data.api.interceptor

import android.content.SharedPreferences
import android.provider.Settings.Secure.putString
import android.util.Base64
import androidx.core.content.edit
import java.io.IOException
import okhttp3.Interceptor
import okhttp3.Response


class HttpAuthInterceptor(private val sharedPreferences: SharedPreferences) : Interceptor {

    private var authorizationValue = sharedPreferences.getString("authorizationValue", "")

    fun setCredentials(httpUsername: String, httpPassword: String){
        val userAndPassword = "$httpUsername:$httpPassword"
        authorizationValue= "Basic ${Base64.encodeToString(userAndPassword.toByteArray(), Base64.NO_WRAP)}"

        sharedPreferences.edit{
            putString("authorizationValue",authorizationValue)
        }
    }


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
                .apply {
                    if(authorizationValue.isNotEmpty()) addHeader("Authorization", authorizationValue)
                }
                .build()

        val originalResponse = chain.proceed(newRequest)

        if (originalResponse.code() == 401) {
            sharedPreferences.edit{
                remove("authorizationValue")
            }
        }

        return originalResponse
    }
}