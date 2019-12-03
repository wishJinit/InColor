package com.yujin.inphoto.util

import android.util.Log

class DLog {
    companion object{
        val TAG = "YUJIN_JIN"

        fun e (msg : String){
            if (BaseApplication.DEBUG) Log.e(TAG, buildLogMsg(msg))
        }

        fun w (msg : String){
            if (BaseApplication.DEBUG) Log.w(TAG, buildLogMsg(msg))
        }

        fun i (msg : String){
            if (BaseApplication.DEBUG) Log.i(TAG, buildLogMsg(msg))
        }

        fun d (msg : String){
            if (BaseApplication.DEBUG) Log.d(TAG, buildLogMsg(msg))
        }

        fun v (msg : String){
            if (BaseApplication.DEBUG) Log.v(TAG, buildLogMsg(msg))
        }

        fun buildLogMsg(message: String): String {
            val ste = Thread.currentThread().stackTrace[4]
            val sb = StringBuilder()

            sb.append("[")
            sb.append(ste.fileName!!.replace(".java", ""))
            sb.append("::")
            sb.append(ste.methodName)
            sb.append("]")
            sb.append(message)

            return sb.toString()
        }
    }

}