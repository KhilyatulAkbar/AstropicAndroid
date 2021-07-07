package com.nurmuhammadkhilyatulakbar.astropic

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class NetworkConfig {
    // set interceptor
    fun getInterceptor() : OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return  okHttpClient
    }
    fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://apodapi.herokuapp.com/")
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun getService() = getRetrofit().create(Apod::class.java)
}

interface Apod {
    @GET("api/")
    fun getApod(@Query("thumbs") thumbs:Boolean): Call<ApodModel>

    @GET("api/")
    fun getApodByDate(@Query("date") date:String, @Query("thumbs") thumbs:Boolean): Call<ApodModel>

    @GET("api/")
    fun getThisWeekApod(@Query("start_date") startDate:String, @Query("end_date") endDate:String, @Query("thumbs") thumbs:Boolean): Call<List<ApodModel>>

    @GET("search/")
    fun getByKeyword(@Query("search_query") searchQuery:String, @Query("number") number:Int, @Query("page") page:Int, @Query("thumbs") thumbs:Boolean): Call<List<ApodModel>>

}