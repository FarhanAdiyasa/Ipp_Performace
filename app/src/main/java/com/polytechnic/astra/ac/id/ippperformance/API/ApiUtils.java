package com.polytechnic.astra.ac.id.ippperformance.API;

import android.util.Log;

import com.polytechnic.astra.ac.id.ippperformance.API.Service.MyService;
import com.polytechnic.astra.ac.id.ippperformance.RetryInterceptor;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ApiUtils {
    public static final String API_URL = "http://10.1.9.227:8080/";

    private static Retrofit retrofit = null;

    // Custom CookieJar implementation
    private static final CookieJar cookieJar = new CookieJar() {
        private final Map<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            cookieStore.put(url, cookies);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url);
            return cookies != null ? cookies : new ArrayList<>();
        }

        public void clearCookies() {
            cookieStore.clear();
        }
    };

    public static Retrofit getClient(String baseUrl) {
        HttpUrl httpUrl = HttpUrl.parse(baseUrl);
        if (httpUrl == null) {
            throw new IllegalArgumentException("Invalid base URL");
        }
        // Reinitialize Retrofit if the base URL changes or if it's null
        if (retrofit == null || !retrofit.baseUrl().equals(httpUrl)) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(new RetryInterceptor(5))
                    .addInterceptor(logging)
                    .cookieJar(cookieJar);

            OkHttpClient okHttpClient = httpClientBuilder.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static MyService getMyService() {
        return getClient(API_URL).create(MyService.class);
    }

    public static void clearCookies() {
        // Use the CookieJar instance directly
        if (cookieJar instanceof CustomCookieJar) {
            ((CustomCookieJar) cookieJar).clearCookies();
        }
    }

    public static List<Cookie> getCookiesForUrl(String url) {
        List<Cookie> cookies = new ArrayList<>();
        HttpUrl httpUrl = HttpUrl.parse(url);
        if (httpUrl != null) {
            List<Cookie> storedCookies = cookieJar.loadForRequest(httpUrl);
            cookies.addAll(storedCookies);
        }
        return cookies;
    }

    // CustomCookieJar is now merged into the main class
    private static class CustomCookieJar implements CookieJar {
        private final Map<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            cookieStore.put(url, cookies);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url);
            return cookies != null ? cookies : new ArrayList<>();
        }

        public void clearCookies() {
            cookieStore.clear();
        }
    }
}
