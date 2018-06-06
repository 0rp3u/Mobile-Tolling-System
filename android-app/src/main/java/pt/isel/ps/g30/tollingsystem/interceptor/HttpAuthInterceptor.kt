package pt.isel.ps.g30.tollingsystem.interceptor

import android.util.Base64
import java.io.IOException
import okhttp3.Interceptor
import okhttp3.Response
import android.R.id.edit



class HttpAuthInterceptor : Interceptor {

    private var authorizationValue = ""
    private var sessionCookie = ""


    fun setCredentials(httpUsername: String, httpPassword: String){
        val userAndPassword = "$httpUsername:$httpPassword"
        authorizationValue= "Basic ${Base64.encodeToString(userAndPassword.toByteArray(), Base64.NO_WRAP)}"
    }


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
                .addHeader("Cookie", sessionCookie)
                .apply {
                    if(authorizationValue.isNotEmpty()) addHeader("Authorization", authorizationValue)
                }
                .build()

        val originalResponse = chain.proceed(newRequest)

        if (originalResponse.isSuccessful && !originalResponse.headers("Set-Cookie").isEmpty()) {

            for (header in originalResponse.headers("Set-Cookie")) { //TODO make sure is only the session cookie
                sessionCookie = header
                //authorizationValue=""
            }

            //TODO save cookie persistently
        }

        return originalResponse
    }
}