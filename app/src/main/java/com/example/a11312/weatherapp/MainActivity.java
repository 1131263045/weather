package com.example.a11312.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String URL = "http://apis.juhe.cn/simpleWeather/query";
    private static final String KEY = "91503e66e173dcfd1c3fffa28734e727";

    private EditText edit_city;
    private Button btn_search;
    private TextView text_result;
    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        edit_city = findViewById(R.id.edit_city);
        btn_search = findViewById(R.id.btn_search);
        text_result = findViewById(R.id.text_result);

        btn_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_search) {
            city = edit_city.getText().toString().trim();
            // 开启线程来发起网络请求
            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        // Step1.创建一个OkHttpClient实例
                        OkHttpClient client = new OkHttpClient();
                        // Step2.创建一个Request对象
                        Request request = new Request.Builder().url(URL + "?city=" + city + "&key=" + KEY).build();
                        // Step3.调用OkHttpClient的newCall()方法来创建call对象，并调用它的execute()方法来发送请求并获取服务器返回的数据
                        Response response = client.newCall(request).execute();// Response对象=服务器返回的数据
                        String responseData = response.body().string();
                        showResponse(responseData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private void sendRequestWithOkHttp() {

    }

    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 在这里进行 UI 操作，将结果显示到界面上
                text_result.setText(response);
            }
        });
    }
}
