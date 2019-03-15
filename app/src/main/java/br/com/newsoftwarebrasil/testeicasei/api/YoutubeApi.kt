package br.com.newsoftwarebrasil.testeicasei.api

import android.content.Context

import br.com.newsoftwarebrasil.testeicasei.api.response.YoutubeResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object YoutubeApi {
    val KEY = "AIzaSyBbIjopEsk7uz5_jHfJzRpjutG8XJS9bns"
    private var testeICaseiRequester: TesteICaseiRequester? = null

    private fun initApi(context: Context, parameter: String) {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/youtube/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        testeICaseiRequester = retrofit.create(TesteICaseiRequester::class.java)
    }


    fun getServiceClient(context: Context, parameter: String): TesteICaseiRequester? {
        if (testeICaseiRequester == null) {
            initApi(context, parameter)
        }

        return testeICaseiRequester
    }

    interface TesteICaseiRequester {//https://www.googleapis.com/youtube/v3/search?part=id,snippet&key=AIzaSyCN_jjPwqrj_oSGaHgWpmBT_cRBFzODoO4&q=Palmeiras
        @GET("search?part=id,snippet")
        fun listVideos(@Query("key") key: String, @Query("q") q: String): Call<YoutubeResponse>
    }
}