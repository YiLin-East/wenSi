package com.fc.wensi;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OKHttp {
    private static OKHttp instance = null;
    public static OKHttp getInstance() {
        if (instance == null){
            instance = new OKHttp();
        }
        return instance;
    }

    Request request;
    OkHttpClient okHttpClient;

    /**
     * get异步请求
     * @param url
     * @param listener
     */
    public void getEnqueue(String url , IGetDataListener listener){
         request = new Request.Builder()
                .url(url)
                .build();
         okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                listener.onSuccess(response.body().string());
            }
        });
    }
}
