package com.cbes.ezreturn.utils

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics

object Logger {
    private val crashlytics: FirebaseCrashlytics get() = FirebaseCrashlytics.getInstance()

    private val callerClassName: String
        get() {
            val caller = callerStack ?: return ""
            val className = caller.className
            return className.substring(className.lastIndexOf('.') + 1)
        }

    private val callerStack: StackTraceElement?
        get() {
            val stElements = Thread.currentThread().stackTrace
            for (i in 1 until stElements.size) {
                val ste = stElements[i]
                if (ste.className != Logger::class.java.name && ste.className.indexOf("java.lang.Thread") != 0) {
                    return ste
                }
            }
            return null
        }

    fun logD(msg: String, e: Throwable? = null) {
        Log.d(callerClassName, msg, e)
        crashlytics.log(msg)
        if (e != null) {
            crashlytics.recordException(e)
        }
    }

    fun logE(msg: String, e: Throwable? = null) {
        Log.e(callerClassName, msg, e)
        crashlytics.log(msg)
        if (e != null) {
            crashlytics.recordException(e)
        }
    }
}