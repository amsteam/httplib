package com.ams.app.http

import android.text.TextUtils
import com.ams.httplib.IHttpParamSign
import java.util.*




class AmsHttpParamSign : IHttpParamSign {

    private var mPrivateKey:String

    constructor(privateKey: String){
        mPrivateKey = privateKey
    }

    override fun doSign(params: Map<String, String>): Map<String, String> {
        val signParams = TreeMap<String, String>()
        val sb = StringBuilder()

        params.forEach{ entry -> signParams[entry.key] = entry.value }

        signParams.forEach { entry ->
            val value = if (TextUtils.isEmpty(entry.value)) "" else entry.value
            signParams[entry.key] = value
            sb.append(entry.key).append("=").append(entry.value).append("&")
        }

        signParams.put("sign", RSAUtil.sign(mPrivateKey,sb.toString())!!)

        return signParams

    }
}