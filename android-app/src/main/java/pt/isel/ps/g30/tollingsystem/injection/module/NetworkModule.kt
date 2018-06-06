package pt.isel.ps.g30.tollingsystem.injection.module

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import pt.isel.ps.g30.tollingsystem.TollingSystemApp
import pt.isel.ps.g30.tollingsystem.data.api.TollingService
import pt.isel.ps.g30.tollingsystem.interceptor.HttpAuthInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule(private val baseUrl: String) {

    @Provides
    @Singleton
    fun provideCache(app: TollingSystemApp): Cache {
        val cacheSize = 20 * 1024 * 1024 // 10 MiB
        return Cache(app.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor() = HttpAuthInterceptor()

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache, interceptor: HttpAuthInterceptor): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .cache(cache)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideTollingService(retrofit: Retrofit): TollingService = retrofit.create(TollingService::class.java)

}