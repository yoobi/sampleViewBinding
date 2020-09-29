package io.github.yoobi.sampleviewbinding.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResultCall(val score: Float, val show: Series)

@JsonClass(generateAdapter = true)
data class Series(@Json(name = "name") val title: String,
                  val status: String,
                  val image: ImageList?,
                  val id: String)

@JsonClass(generateAdapter = true)
data class ImageList(val medium: String, val original: String)