package io.banana.transformersbattle.data.remote.apis

import io.banana.transformersbattle.data.remote.dtos.TransformerDTO
import io.banana.transformersbattle.data.remote.responses.GetTransformersResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface TransformersApi {
    @GET("/transformers")
    @Headers(value = ["Content-type: application/json"])
    suspend fun getList(@Header("Authorization") token: String): GetTransformersResponse

    @POST("/transformers")
    @Headers(value = ["Content-type: application/json"])
    suspend fun create(
        @Header("Authorization") token: String,
        @Body transformerDTO: TransformerDTO
    ): TransformerDTO

    @PUT("/transformers")
    @Headers(value = ["Content-type: application/json"])
    suspend fun edit(
        @Header("Authorization") token: String,
        @Body transformerDTO: TransformerDTO
    ): TransformerDTO

    @DELETE("/transformers/{id}")
    @Headers(value = ["Content-type: application/json"])
    suspend fun delete(@Header("Authorization") token: String, @Path("id") id: String) : Response<Unit>
}