package com.codemate.blogreader.data.cache;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ironman on 04/08/16.
 */
public class SharedPrefCache {
    private static final String KEY_PERSIST_TIME = "persist_time";
    private static final String KEY_DATA = "data";

    private SharedPreferences prefs;
    private String cacheKeySuffix;

    public SharedPrefCache(Context context, String cacheName) {
        if (context == null) {
            return;
        }

        this.prefs = context.getSharedPreferences(cacheName, Context.MODE_PRIVATE);
    }

    protected void setCacheKeySuffix(String cacheKeySuffix) {
        this.cacheKeySuffix = cacheKeySuffix;
    }

    protected boolean isDataStillValid(long validityPeriod) {
        if (prefs == null) {
            return false;
        }

        long currentTime = System.currentTimeMillis();
        long persistTime = prefs.getLong(getProperKey(KEY_PERSIST_TIME), 0);

        return currentTime - persistTime <= validityPeriod;
    }

    protected void persistData(String data) {
        if (prefs == null) {
            return;
        }

        prefs.edit()
                .putLong(getProperKey(KEY_PERSIST_TIME), System.currentTimeMillis())
                .putString(getProperKey(KEY_DATA), data)
                .apply();
    }

    protected String getData() {
        if (prefs == null) {
            return null;
        }

        return prefs.getString(getProperKey(KEY_DATA), null);
    }

    protected void clearCache() {
        if (prefs == null) {
            return;
        }

        prefs.edit().clear().apply();
    }

    private String getProperKey(String input) {
        if (cacheKeySuffix != null) {
            return input + cacheKeySuffix;
        }

        return input;
    }
}
