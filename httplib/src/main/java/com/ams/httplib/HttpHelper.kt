package com.ams.httplib

class HttpHelper private constructor(): IHttpProcessor {

    private object HttpHelperHolder{
        val INSTANCE = HttpHelper()
    }

    companion object {

        //代理层要持有代理接口 引用
        private var mHttpProcessor: IHttpProcessor? = null
        private var mHttpParamSign: IHttpParamSign? = null
        private var mResultConvert: IResultConvert? = null

        fun init(httpProcessor: IHttpProcessor){
            init(httpProcessor,null,null)
        }
        fun init(httpProcessor: IHttpProcessor, httpParamSign: IHttpParamSign){
            init(httpProcessor,httpParamSign,null)
        }

        fun init(httpProcessor: IHttpProcessor, httpParamSign: IHttpParamSign?, resultConvert: IResultConvert?){
            mHttpProcessor = httpProcessor
            mHttpParamSign = httpParamSign
            mResultConvert = resultConvert
        }

        fun obtian(): HttpHelper {
            if (null == mHttpProcessor) {
                throw RuntimeException("请先初始化该类，调用HttpHelper.init(IHttpProcessor)方法")
            }
            return HttpHelperHolder.INSTANCE
        }

    }


    override fun post(url: String, params: Map<String, Any>?, callBack: ICallback) {
        mHttpProcessor!!.post(url,params,callBack)
    }

    override fun get(url: String, params: Map<String, Any>?, callBack: ICallback) {
        mHttpProcessor!!.get(url,params,callBack)
    }


    fun getResultConvert(): IResultConvert? {
        return mResultConvert
    }

    // 给请求参数签名
    private fun getSignParams(params: Map<String, String>): Map<String, String> {
        return if (null != mHttpParamSign) {
            mHttpParamSign!!.doSign(params)
        } else params
    }

}

