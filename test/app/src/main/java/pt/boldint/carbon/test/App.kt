package pt.boldint.carbon.test

import android.app.Application
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

open class App : Application() {

    companion object{
        lateinit var instance: App
            private set
    }

    lateinit var service :Service


    override fun onCreate() {
        super.onCreate()
        instance = this

        service = provideTollingService(provideRetrofit(provideOkHttpClient(provideCache())))

    }




    fun provideCache(): Cache {
        val cacheSize = 20 * 1024 * 1024 // 10 MiB
        return Cache(this.cacheDir, cacheSize.toLong())
    }



    fun provideOkHttpClient(cache: Cache): OkHttpClient = OkHttpClient.Builder()
            .cache(cache)
            .build()


    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl("https://dev.a-to-be.com/")
            .client(okHttpClient)
            .build()

    fun provideTollingService(retrofit: Retrofit): Service = retrofit.create(Service::class.java)


}