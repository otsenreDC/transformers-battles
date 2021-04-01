package io.banana.transformersbattle.data.remote.apis

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface SessionApi {

    @GET("/allspark")
    suspend fun getToken(): ResponseBody

    @GET("/allspark")
    fun getTokenBlocking(): Call<ResponseBody>
}