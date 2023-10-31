package com.cbes.ezreturn.utils

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import org.json.JSONArray
import org.json.JSONObject

fun createEmptyItemView(
    context: Context
): View {
    return LinearLayout(context)
        .apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
        }
}

fun JSONObject.toMap(): Map<String, *> = keys().asSequence().associateWith {
    when (val value = this[it]) {
        is JSONArray -> {
            val map = (0 until value.length()).associate { Pair(it.toString(), value[it]) }
            JSONObject(map).toMap().values.toList()
        }
        is JSONObject -> value.toMap()
        JSONObject.NULL -> null
        else -> value
    }
}

