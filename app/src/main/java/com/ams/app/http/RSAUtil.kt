package com.ams.app.http

import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Base64
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.Signature
import java.security.spec.PKCS8EncodedKeySpec

object RSAUtil{
    private val KEY_ALGORITHM = "RSA"

    @RequiresApi(Build.VERSION_CODES.FROYO)
    fun sign(privateKey: String, str: String): String? {
        try {
            val sign = Signature.getInstance("SHA1withRSA")
            sign.initSign(restorePrivateKey(Base64.decode(privateKey, Base64.DEFAULT)))
            val data = str.toByteArray(Charsets.UTF_8)
            sign.update(data)
            val signature = sign.sign()
            return String(Base64.encode(signature, Base64.DEFAULT))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 还原私钥，PKCS8EncodedKeySpec 用于构建私钥的规范
     *
     * @param keyBytes
     * @return
     */
    fun restorePrivateKey(keyBytes: ByteArray): PrivateKey? {
        val pkcs8EncodedKeySpec = PKCS8EncodedKeySpec(keyBytes)
        try {
            val factory = KeyFactory.getInstance(KEY_ALGORITHM)
            return factory.generatePrivate(pkcs8EncodedKeySpec)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}