package com.andrstudy.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    String android_ID;
    Intent intent, falseintent;
    int clickTime = 0;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //android_ID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        tv=(TextView)findViewById(R.id.android_ID);
        tv.setText(android_ID);

        ConnectServer();

        Intent mac_intent = new Intent(MainActivity.this,Mac_address.class);
        startActivity(mac_intent);
    }



    private void ConnectServer() {

        final String SIGNIN_URL = "http://203.244.145.218:8181/QrLockDoor/selectDB.jsp";
        final String urlSuffix = "?User_Device_Id=" + android_ID;

        class SignupUser extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if (s != null) {
                    try {
                        JSONArray jArr = new JSONArray(s);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject=jArr.getJSONObject(0);

                        intent.putExtra("User_Device_Id", jsonObject.getString(android_ID));

                        startActivity(intent);
                        finish();

                    } catch (Exception e) {
                        falseintent.putExtra("idCheck", 1);

                        startActivity(falseintent);

                        finish();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "서버와의 통신에 문제가 발생했습니다", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            protected String doInBackground(String... params) {
                BufferedReader bufferedReader = null;

                try {

                    HttpClient client = new DefaultHttpClient();  // 보낼 객체 만들기
                    HttpPost post = new HttpPost(SIGNIN_URL + urlSuffix);  // 주소 뒤에 데이터를 넣기

                    HttpResponse response = client.execute(post); // 데이터 보내기

                    BufferedReader bufreader = new BufferedReader(
                            new InputStreamReader(
                                    response.getEntity().getContent(), "utf-8"));

                    String line = null;
                    String page = "";

                    while ((line = bufreader.readLine()) != null) {
                        page += line;
                    }

                    clickTime ++;
                    return page;
                } catch (Exception e) {

                    return null;
                }
            }
        }

        SignupUser su = new SignupUser();
        su.execute(urlSuffix);
    }
}
