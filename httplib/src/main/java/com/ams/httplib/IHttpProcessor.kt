package com.ams.httplib

/**
 * http处理器
 */
interface IHttpProcessor {
    fun post(url:String,params:Map<String,Any>?,callBack: ICallback)
    fun get(url:String, params: Map<String, Any>?, callBack: ICallback)
}