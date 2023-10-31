package com.johnyen.taipeitravelspot.api.portal.response
import com.google.gson.annotations.SerializedName
import com.johnyen.taipeitravelspot.api.portal.response.model.Data


class TaipeiDataResponse {
    @SerializedName("total")
    var total: Int = 0
    @SerializedName("data")
    val data: List<Data>? = null
}