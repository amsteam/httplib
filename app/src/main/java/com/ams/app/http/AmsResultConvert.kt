package com.ams.app.http

import com.ams.httplib.IResultConvert
import com.google.gson.Gson
import org.json.JSONObject
import java.lang.reflect.Type



class AmsResultConvert : IResultConvert {
    override fun <T> convert(type: Type, result: String): T {
        val obj = JSONObject(result)
        val code = obj.optInt("code")
        if (code == 200) {
            val gson = Gson()
            return gson.fromJson(obj.optString("data"), type)
        } else {
            throw Exception(obj.optString("msg"))
        }
    }
}