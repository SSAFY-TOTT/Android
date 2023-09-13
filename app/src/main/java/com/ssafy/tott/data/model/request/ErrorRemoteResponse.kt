package com.ssafy.tott.data.model.request

import com.google.gson.annotations.SerializedName
import com.ssafy.tott.domain.exception.NetworkException

data class ErrorRemoteResponse(
    @SerializedName("code") val errorCode: Int? = null,
    @SerializedName("message") val message: String,
) {
    fun toNetworkException() = NetworkException(errorCode, message)
}
