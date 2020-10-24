package com.example.githubuser.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceClient {

    companion object {
        private const val BASE_URL: String = "https://api.github.com/"
        @JvmStatic
        fun create(): Service {

            //This will show the full request and response in the logcat
            var interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            var okClient: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okClient)
                .build()
            return retrofit.create(Service::class.java)

        }
    }
}