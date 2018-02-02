package com.vodolazskiy.twitterclient.core

import android.util.Log
import com.vodolazskiy.twitterclient.core.di.DI

// TODO: 1/28/18 Use DI
object L {

    /**
     * @return true if this is debug build and logging enabled
     */
    private val isDebug: Boolean
        get() = DI.component.buildConfig.isDebug

    /**
     * T.
     *
     * @param throwable the throwable
     */
    fun t(throwable: Throwable?) {
        if (throwable != null && isDebug) {
            Log.e("PST", Log.getStackTraceString(throwable))
        }
    }

    /**
     * T.
     *
     * @param tag       the tag
     * @param throwable the throwable
     */
    fun t(tag: String, throwable: Throwable?) {
        if (throwable != null && isDebug) {
            Log.d(tag, if (throwable.message != null) throwable.message else throwable.toString())
        }
    }

    /**
     * E.
     *
     * @param exception the exception
     */
    fun e(exception: Throwable?) {
        if (exception != null && isDebug) {
            Log.e("?", Log.getStackTraceString(exception))
        }
    }

    /**
     * E.
     *
     * @param tag       the tag
     * @param exception the exception
     */
    fun e(tag: String, exception: String?) {
        if (exception != null && isDebug) {
            Log.e(tag, exception)
        }
    }

    /**
     * E.
     *
     * @param tag       the tag
     * @param throwable the throwable
     */
    fun e(tag: String, throwable: Throwable?) {
        if (throwable != null && isDebug) {
            Log.e(tag, Log.getStackTraceString(throwable))
        }
    }

    /**
     * E.
     *
     * @param tag       the tag
     * @param message   the message
     * @param throwable the throwable
     */
    fun e(tag: String, message: String, throwable: Throwable?) {
        if (throwable != null && isDebug) {
            Log.e(tag, message, throwable)
        }
    }

    /**
     * D.
     *
     * @param tag     the tag
     * @param message the message
     */
    fun d(tag: String, message: String?) {
        if (message != null && isDebug) {
            Log.d(tag, message)
        }
    }

    /**
     * D.
     *
     * @param tag       the tag
     * @param throwable the throwable
     */
    fun d(tag: String, throwable: Throwable?) {
        if (throwable != null && isDebug) {
            Log.d(tag, if (throwable.message != null) throwable.message else throwable.toString())
        }
    }

    /**
     * W.
     *
     * @param tag     the tag
     * @param message the message
     */
    fun w(tag: String, message: String?) {
        if (message != null && isDebug) {
            Log.w(tag, message)
        }
    }

    /**
     * W.
     *
     * @param tag       the tag
     * @param throwable the throwable
     */
    fun w(tag: String, throwable: Throwable?) {
        if (throwable != null && isDebug) {
            Log.w(tag, if (throwable.message != null) throwable.message else throwable.toString())
        }
    }

    /**
     * W.
     *
     * @param tag       the tag
     * @param message   the message
     * @param throwable the throwable
     */
    fun w(tag: String, message: String?, throwable: Throwable?) {
        if (throwable != null && message != null && isDebug) {
            Log.w(tag, message, throwable)
        }
    }

    /**
     * I.
     *
     * @param tag     the tag
     * @param message the message
     */
    fun i(tag: String, message: String?) {
        if (message != null && isDebug) {
            Log.i(tag, message)
        }
    }

    /**
     * I.
     *
     * @param tag      the tag
     * @param message1 the message 1
     * @param messages the messages
     */
    fun i(tag: String, message1: String?, vararg messages: String) {
        if (message1 != null && isDebug) {
            val stringBuilder = StringBuilder()
            stringBuilder.append(message1)
            for (message in messages) {
                stringBuilder.append(message)
            }

            L.i(tag, stringBuilder.toString())
        }
    }

    /**
     * I.
     *
     * @param tag       the tag
     * @param throwable the throwable
     */
    fun i(tag: String, throwable: Throwable?) {
        if (throwable != null && isDebug) {
            Log.i(tag, if (throwable.message != null) throwable.message else throwable.toString())
        }
    }

    /**
     * I.
     *
     * @param tag       the tag
     * @param message   the message
     * @param throwable the throwable
     */
    fun i(tag: String, message: String?, throwable: Throwable?) {
        if (throwable != null && message != null && isDebug) {
            Log.i(tag, message, throwable)
        }
    }

    /**
     * V.
     *
     * @param tag     the tag
     * @param message the message
     */
    fun v(tag: String, message: String?) {
        if (message != null && isDebug) {
            Log.v(tag, message)
        }
    }

    /**
     * V.
     *
     * @param tag       the tag
     * @param throwable the throwable
     */
    fun v(tag: String, throwable: Throwable?) {
        if (throwable != null && isDebug) {
            Log.v(tag, if (throwable.message != null) throwable.message else throwable.toString())
        }
    }

    /**
     * V.
     *
     * @param tag       the tag
     * @param message   the message
     * @param throwable the throwable
     */
    fun v(tag: String, message: String?, throwable: Throwable?) {
        if (throwable != null && message != null && isDebug) {
            Log.v(tag, message, throwable)
        }
    }

    /**
     * What a Terrible Failure: Report a condition that should never happen.
     * The error will always be logged at level ASSERT with the call stack.
     * Depending on system configuration, a report may be added to the
     * [android.os.DropBoxManager] and/or the process may be terminated
     * immediately with an error dialog.
     *
     * @param tag Used to identify the source of a log message.
     * @param msg The message you would like logged.
     */
    fun wtf(tag: String, msg: String) {
        if (isDebug) {
            Log.wtf(tag, msg)
        }
    }

    /**
     * PrintStackTrace exception
     *
     * @param e exception
     */
    fun exception(e: Exception?) {
        if (e != null && isDebug) {
            Log.e("?", Log.getStackTraceString(e))
        }
    }

    /**
     * PrintStackTraceException exception
     *
     * @param e exception
     */
    fun exception(e: Throwable?) {
        if (e != null && isDebug) {
            e.printStackTrace()
        }
    }
}
