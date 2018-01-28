package com.vodolazskiy.twitterclient.core;

import android.support.annotation.NonNull;
import android.util.Log;

import com.vodolazskiy.twitterclient.core.di.DI;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

// TODO: 1/28/18 Use DI
public final class L {

    private L() {
        //no instance
    }

    /**
     * T.
     *
     * @param throwable the throwable
     */
    public static void t(Throwable throwable) {
        if (throwable != null && isDebug()) {
            L.e("PST", stacktraceToString(throwable));
        }
    }

    /**
     * T.
     *
     * @param tag       the tag
     * @param throwable the throwable
     */
    public static void t(String tag, Throwable throwable) {
        if (throwable != null && isDebug()) {
            Log.d(tag, throwable.getMessage() != null ? throwable.getMessage() : String.valueOf(throwable));
        }
    }

    /**
     * E.
     *
     * @param exception the exception
     */
    public static void e(Throwable exception) {
        if (exception != null && isDebug()) {
            Log.e("", stacktraceToString(exception));
        }
    }

    /**
     * E.
     *
     * @param tag       the tag
     * @param exception the exception
     */
    public static void e(String tag, String exception) {
        if (exception != null && isDebug()) {
            Log.e(tag, exception);
        }
    }

    /**
     * E.
     *
     * @param tag       the tag
     * @param throwable the throwable
     */
    public static void e(String tag, Throwable throwable) {
        if (throwable != null && isDebug()) {
            Log.e(tag, stacktraceToString(throwable));
        }
    }

    /**
     * E.
     *
     * @param tag       the tag
     * @param message   the message
     * @param throwable the throwable
     */
    public static void e(String tag, String message, Throwable throwable) {
        if (throwable != null && isDebug()) {
            Log.e(tag, String.valueOf(message), throwable);
        }
    }

    /**
     * D.
     *
     * @param tag     the tag
     * @param message the message
     */
    public static void d(String tag, String message) {
        if (message != null && isDebug()) {
            Log.d(tag, message);
        }
    }

    /**
     * D.
     *
     * @param tag       the tag
     * @param throwable the throwable
     */
    public static void d(String tag, Throwable throwable) {
        if (throwable != null && isDebug()) {
            Log.d(tag, throwable.getMessage() != null ? throwable.getMessage() : String.valueOf(throwable));
        }
    }

    /**
     * W.
     *
     * @param tag     the tag
     * @param message the message
     */
    public static void w(String tag, String message) {
        if (message != null && isDebug()) {
            Log.w(tag, message);
        }
    }

    /**
     * W.
     *
     * @param tag       the tag
     * @param throwable the throwable
     */
    public static void w(String tag, Throwable throwable) {
        if (throwable != null && isDebug()) {
            Log.w(tag, throwable.getMessage() != null ? throwable.getMessage() : String.valueOf(throwable));
        }
    }

    /**
     * W.
     *
     * @param tag       the tag
     * @param message   the message
     * @param throwable the throwable
     */
    public static void w(String tag, String message, Throwable throwable) {
        if (throwable != null && message != null && isDebug()) {
            Log.w(tag, message, throwable);
        }
    }

    /**
     * I.
     *
     * @param tag     the tag
     * @param message the message
     */
    public static void i(String tag, String message) {
        if (message != null && isDebug()) {
            Log.i(tag, message);
        }
    }

    /**
     * I.
     *
     * @param tag      the tag
     * @param message1 the message 1
     * @param messages the messages
     */
    public static void i(String tag, String message1, @NonNull String... messages) {
        if (message1 != null && isDebug()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(message1);
            for (String message : messages) {
                stringBuilder.append(String.valueOf(message));
            }

            L.i(tag, stringBuilder.toString());
        }
    }

    /**
     * I.
     *
     * @param tag       the tag
     * @param throwable the throwable
     */
    public static void i(String tag, Throwable throwable) {
        if (throwable != null && isDebug()) {
            Log.i(tag, throwable.getMessage() != null ? throwable.getMessage() : String.valueOf(throwable));
        }
    }

    /**
     * I.
     *
     * @param tag       the tag
     * @param message   the message
     * @param throwable the throwable
     */
    public static void i(String tag, String message, Throwable throwable) {
        if (throwable != null && message != null && isDebug()) {
            Log.i(tag, message, throwable);
        }
    }

    /**
     * V.
     *
     * @param tag     the tag
     * @param message the message
     */
    public static void v(String tag, String message) {
        if (message != null && isDebug()) {
            Log.v(tag, message);
        }
    }

    /**
     * V.
     *
     * @param tag       the tag
     * @param throwable the throwable
     */
    public static void v(String tag, Throwable throwable) {
        if (throwable != null && isDebug()) {
            Log.v(tag, throwable.getMessage() != null ? throwable.getMessage() : String.valueOf(throwable));
        }
    }

    /**
     * V.
     *
     * @param tag       the tag
     * @param message   the message
     * @param throwable the throwable
     */
    public static void v(String tag, String message, Throwable throwable) {
        if (throwable != null && message != null && isDebug()) {
            Log.v(tag, message, throwable);
        }
    }

    /**
     * What a Terrible Failure: Report a condition that should never happen.
     * The error will always be logged at level ASSERT with the call stack.
     * Depending on system configuration, a report may be added to the
     * {@link android.os.DropBoxManager} and/or the process may be terminated
     * immediately with an error dialog.
     *
     * @param tag Used to identify the source of a log message.
     * @param msg The message you would like logged.
     */
    public static void wtf(String tag, String msg) {
        if (isDebug()) {
            Log.wtf(tag, msg);
        }
    }

    /**
     * PrintStackTrace exception
     *
     * @param e exception
     */
    public static void exception(Exception e) {
        if (e != null && isDebug()) {
            L.e("?", L.stacktraceToString(e));
        }
    }

    /**
     * PrintStackTraceException exception
     *
     * @param e exception
     */
    public static void exception(Throwable e) {
        if (e != null && isDebug()) {
            e.printStackTrace();
        }
    }

    public static boolean shouldLogThisException(Throwable throwable) {
        if (isDebug()) return false;
        if (throwable instanceof SocketTimeoutException ||
                throwable instanceof UnknownHostException ||
                throwable instanceof ConnectException ||
                throwable instanceof SocketException ||
                throwable instanceof SSLHandshakeException) {
            return false;
        }
        return true;
    }

    /**
     * @return true if this is debug build and logging enabled
     */
    private static boolean isDebug() {
        return DI.INSTANCE.getComponent().getBuildConfig().isDebug();
    }

    public static String stacktraceToString(Throwable throwable) {
        if (throwable == null) {
            return "null";
        }

        String result = "?null?";

        StringWriter stringWriter = null;
        PrintWriter printWriter = null;
        try {
            stringWriter = new StringWriter();
            printWriter = new PrintWriter(stringWriter);
            throwable.printStackTrace(printWriter);
            result = stringWriter.toString(); // stack trace as a string
        } catch (Exception e) {
            exception(e);
        } finally {
            try {
                if (stringWriter != null) {
                    stringWriter.close();
                }
            } catch (Exception e) {
                L.e("stacktraceToString", e);
            }
            try {
                if (printWriter != null) {
                    printWriter.close();
                }
            } catch (Exception e) {
                L.e("stacktraceToString", e);
            }
        }

        return String.valueOf(throwable) + "\nStacktrace: " + result;
    }
}
