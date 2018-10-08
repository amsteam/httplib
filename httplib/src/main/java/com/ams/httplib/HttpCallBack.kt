package com.ams.httplib

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
abstract class HttpCallBack<Results> : ICallback {


    override fun onSucess(str: String) {
        try {
            //万能解析方案：
            val classInfo = analysisClassInfo(this)
            val resultConvert = HttpHelper.obtian().getResultConvert() ?: throw RuntimeException("没有给HttpHelper设置IResultConvert")
            val results = resultConvert.convert<Results>(classInfo, str)
            onSuccess(results)
        }catch (e : Exception){
            onFailures(e)
        }

    }

    override fun onFailure(ex: Exception) {
        onFailures(ex)
    }

    abstract fun onSuccess(results: Results)

    abstract fun onFailures(ex: Exception)



    private fun analysisClassInfo(obj: Any): Type {
        //Type是 Java 编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。
        val types = obj::class.supertypes
        //获取参数化类型的数组，泛型可能有多个
        val params = (types as ParameterizedType).actualTypeArguments
        return params[0]
    }

}