package com.test.deliveryhero.helper;

import android.text.TextUtils;
import android.util.Log;

import com.test.deliveryhero.BuildConfig;

/**
 * Wrapp all the logs in Logger class
 * Kepp it centeralized here to disable the logs while publishing
 *
 * Log to any analytics platform, or write the logs to file.
 */
public class Logger {
    static private Logger _instance = null;

    private Logger() {
    }

    static public Logger instance() {
        if (_instance == null) _instance = new Logger();
        return _instance;
    }

    public void v(String tag, Object msg) {
        if (BuildConfig.DEBUG)
            Log.v(tag, msg + "");
    }

    public void e(String tag, Object msg) {
        Log.e(tag, msg + "");
    }

    public void w(String tag, Object msg) {
        Log.w(tag, msg + "");
    }

    public void logFullMessage(String tag, Object message) {
        if (!TextUtils.isEmpty(message.toString()) && message.toString().length() > 4000) {
            int chunkCount = message.toString().length() / 4000; // integer division
            for (int i = 0; i <= chunkCount; i++) {
                int max = 4000 * (i + 1);
                if (max >= message.toString().length()) {
                    v(tag, message.toString().substring(4000 * i));
                } else {
                    v(tag, message.toString().substring(4000 * i, max));
                }
            }
            return;
        }
        v(tag, message + "");
    }
}
