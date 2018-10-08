package com.ams.httplib

/**
 * 给请求参数签名
 */
interface IHttpParamSign {
    fun doSign(params: Map<String, String>): Map<String, String>
}