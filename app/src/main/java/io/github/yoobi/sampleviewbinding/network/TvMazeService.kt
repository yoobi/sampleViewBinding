package io.github.yoobi.sampleviewbinding.network

import io.github.yoobi.sampleviewbinding.data.ResultCall
import retrofit2.http.GET
import retrofit2.http.Query

interface TvMazeService {

    @GET("shows")
    suspend fun search(@Query("q") search: String?) : List<ResultCall>

}
