package com.ssafy.tott.data.mapper

import com.google.gson.Gson
import com.ssafy.tott.data.model.request.ErrorRemoteResponse

fun getErrorResponse(json: String): ErrorRemoteResponse {
    return Gson().fromJson(json, ErrorRemoteResponse::class.java)
}
