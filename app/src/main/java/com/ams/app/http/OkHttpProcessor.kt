package com.ams.app.http

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import com.ams.httplib.ICallback
import com.ams.httplib.IHttpProcessor
import okhttp3.*
import java.io.File
import java.io.IOException




class OkHttpProcessor : IHttpProcessor {

    private val mHandler = Handler(Looper.getMainLooper())
    private var mOkHttpClient: OkHttpClient? = null

    constructor(context:Context){
        mOkHttpClient = OkHttpClient().newBuilder()
                .cache(getCache(context))
                .build()
    }


    override fun post(url: String, params: Map<String, Any>?, callBack: ICallback) {
        val builder = FormBody.Builder()
        params!!.forEach { entry ->  val value = if (TextUtils.isEmpty(entry.value as CharSequence?)) "" else entry.value
            builder.add(entry.key, value as String)}
        val request = Request.Builder()
                .url(url)
                .post(builder.build())
                .build()
        performRequest(request,  callBack)
    }

    override fun get(url: String, params: Map<String, Any>?, callBack: ICallback) {
        val request = Request.Builder()
                .url(appendParam(url, params!!))
                .build()
        performRequest(request, callBack)
    }


    private fun performRequest(request: Request, callback: ICallback): Call {
        val call = mOkHttpClient!!.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                if (call.isCanceled) return
                mHandler.post { callback.onFailure(e) }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                if (call.isCanceled) return
                val result = response.body()!!.string()
                mHandler.post { callback.onSucess(result)}
            }
        })

        return call
    }

    private fun appendParam(url: String, params: Map<String, Any>?): String {
        if (null == params) return url
        val sb = StringBuffer(url)
        sb.append("?")
        val entrySet = params.entries
        for ((key, value) in entrySet) {
            sb.append(key).append("=").append(value).append("&")
        }
        if (sb.length > 0) sb.delete(sb.length - 1, sb.length)
        return sb.toString()
    }

    private fun getCache(context: Context): Cache {
        val cacheFile = getCacheFile(context)
        val cacheSize = 10 * 1024 * 1024
        return Cache(cacheFile, cacheSize.toLong())
    }

    private fun getCacheFile(context: Context): File {
        var cacheFile = context.externalCacheDir
        if (!cacheFile!!.exists()) {
            cacheFile = context.cacheDir
        }
        return cacheFile
    }
}