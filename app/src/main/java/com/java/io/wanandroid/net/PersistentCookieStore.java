package com.java.io.wanandroid.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.java.io.wanandroid.base.App;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

public class PersistentCookieStore {

    private static final String TAG = "PersistentCookieStore";
    private static final String COOKIE_PREFS = "Cookies_Prefs";
    private final Map<String, ConcurrentHashMap<String, Cookie>> mCookies;
    private final SharedPreferences mCookiePrefs;

    public PersistentCookieStore() {
        mCookiePrefs = App.getAppContext().getSharedPreferences(COOKIE_PREFS, Context.MODE_PRIVATE);
        mCookies = new HashMap<>();
        Map<String, ?> prefsMap = mCookiePrefs.getAll();
        prefsMap.forEach((key, value) -> {

        });
    }

    public void add(HttpUrl url, Cookie cookie) {
        String token = getCookieToken(cookie);

        /* 是否要持久化cookie, 是否要重置cookie */
        if (!cookie.persistent()) {
            if (!mCookies.containsKey(url.host())) {
                mCookies.put(url.host(), new ConcurrentHashMap<>());
            }
            mCookies.get(url.host()).put(token, cookie);
        } else {
            if (mCookies.containsKey(url.host())) {
                mCookies.remove(url.host()).remove(token);
            }
        }

        SharedPreferences.Editor edit = mCookiePrefs.edit();
        edit.putString(url.host(), TextUtils.join(",", mCookies.get(url.host()).keySet()));
        edit.putString(token, encodeCookie(new OkHttpCookie(cookie)));

    }

    private String getCookieToken(Cookie cookie) {
        return cookie.name() + "@" + cookie.domain();
    }

    private String encodeCookie(OkHttpCookie cookies) {
        if (null == cookies) {
            return null;
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(cookies);
        } catch (IOException e) {
            Log.e(TAG, "encodeCookie: IOException", e);
            return null;
        }
        return byteArrayToHexString(bos.toByteArray());
    }

    private Cookie decodeCookie(String original) {
        byte[] bytes = hexStringToByteArray(original);
        ByteArrayInputStream bis = null;
        Cookie cookie = null;
        try {
            bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            cookie = ((OkHttpCookie) ois.readObject()).getCookie();
        } catch (IOException e) {
            Log.e(TAG, "decodeCookie: IOException", e);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "decodeCookie: ClassNotFoundException", e);
        } finally {
            if (null != bis) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return cookie;
    }

    private String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte element : bytes) {
            int v = element & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.US);
    }

    private byte[] hexStringToByteArray(String hexString) {
        int length = hexString.length();
        byte[] data = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            data[i / 2] = (byte) (Character.digit(hexString.charAt(i), 16) << 4 + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }
}
