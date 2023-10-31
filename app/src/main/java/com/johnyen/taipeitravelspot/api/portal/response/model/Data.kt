package com.johnyen.taipeitravelspot.api.portal.response.model

import com.google.gson.annotations.SerializedName


class Data {
    @SerializedName("id")
    val id: Int = 0
    @SerializedName("name")
    val name: String = ""
    @SerializedName("introduction")
    val introduction: String = ""
    @SerializedName("address")
    val address: String = ""
    @SerializedName("url")
    val url: String = ""
    @SerializedName("images")
    val images: List<Images>? = null
}