package com.ams.httplib

import java.lang.reflect.Type

/**
 * 对返回结果进行变换返回
 *
 * gson to bean
 */
interface IResultConvert {

    fun <T> convert(type: Type, result: String): T

}