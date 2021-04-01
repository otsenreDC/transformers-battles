package io.banana.transformersbattle.data.remote.services

import io.banana.transformersbattle.data.remote.apis.SessionApi
import io.banana.transformersbattle.data.remote.apis.TransformersApi
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type


class Services {
    companion object {
        fun createClient(converterFactory: Converter.Factory): Retrofit = Retrofit.Builder()
            .baseUrl("https://transformers-api.firebaseapp.com")
            .client(httpClient())
            .addConverterFactory(converterFactory)
            .build()

        fun sessionApi(client: Retrofit): SessionApi = client.create(
            SessionApi::class.java
        )

        fun transformersApi(client: Retrofit): TransformersApi = client.create(
            TransformersApi::class.java
        )

        private fun httpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor()
                        .apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }).build()
        }
    }

}

class StringConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return if (ResponseBody::class.java == type) {
            Converter {
                it.string()
            }
        } else {
            null
        }
    }
}
