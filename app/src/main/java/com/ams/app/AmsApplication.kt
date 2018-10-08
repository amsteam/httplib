package com.ams.app

import android.app.Application
import com.ams.app.http.AmsHttpParamSign
import com.ams.app.http.AmsResultConvert
import com.ams.app.http.OkHttpProcessor
import com.ams.httplib.HttpHelper

class AmsApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val okHttpProcessor = OkHttpProcessor(this)

        val paramSign = AmsHttpParamSign("privateKey")
        val resultConvert = AmsResultConvert()


        /**
         *  可根据不同的网络框架进行替换， 只需要改动一行代码即可实现
         *    okHttpProcessor 替换 XUtilhttpProcessor 即可
         *
         */

        HttpHelper.init(okHttpProcessor,paramSign,resultConvert)
    }
}