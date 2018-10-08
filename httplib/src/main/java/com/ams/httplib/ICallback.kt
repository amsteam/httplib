package com.ams.httplib

interface ICallback {
    fun onSucess(str:String)
    fun onFailure(ex:Exception)
}