package com.ams.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ams.httplib.HttpCallBack
import com.ams.httplib.HttpHelper
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        HttpHelper.obtian().get("", null, object : HttpCallBack<Any>() {
            override fun onSuccess(results: Any) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onFailures(ex: Exception) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}
