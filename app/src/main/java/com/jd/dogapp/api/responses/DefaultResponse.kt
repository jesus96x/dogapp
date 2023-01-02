package com.jd.dogapp.api.responses

import com.squareup.moshi.Json

class DefaultResponse (
    val msg: String,
    @field:Json(name = "is_success") val  isSuccess: Boolean,
)