package com.andrstudy.myapplication

import android.content.Intent
import android.os.AsyncTask
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.andrstudy.myapplication.R.id.android_ID

import org.json.JSONArray
import org.json.JSONObject

import java.io.BufferedReader
import java.io.InputStreamReader

import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.DefaultHttpClient

class MainActivity : AppCompatActivity() {
    internal var intent: Intent? = null
    internal var falseintent: Intent? = null
    internal var clickTime = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        android_ID.text = android_ID

        ConnectServer()

        val mac_intent = Intent(this@MainActivity, Mac_address::class.java)
        startActivity(mac_intent)
    }


    private fun ConnectServer() {

        val SIGNIN_URL = "http://203.244.145.218:8181/QrLockDoor/selectDB.jsp"
        val urlSuffix = "?User_Device_Id=$android_ID"

        class SignupUser : AsyncTask<String, Void, String>() {

            override fun onPreExecute() {
                super.onPreExecute()
            }

            override fun onPostExecute(s: String?) {
                super.onPostExecute(s)

                if (s != null) {
                    try {
                        val jArr = JSONArray(s)
                        var jsonObject = JSONObject()
                        jsonObject = jArr.getJSONObject(0)

                        intent!!.putExtra("User_Device_Id", jsonObject.getString(android_ID))

                        startActivity(intent)
                        finish()

                    } catch (e: Exception) {
                        falseintent!!.putExtra("idCheck", 1)

                        startActivity(falseintent)

                        finish()
                    }

                } else {
                    Toast.makeText(this@MainActivity, "서버와의 통신에 문제가 발생했습니다", Toast.LENGTH_SHORT).show()

                }

            }

            override fun doInBackground(vararg params: String): String? {
                val bufferedReader: BufferedReader? = null

                try {

                    val client = DefaultHttpClient()  // 보낼 객체 만들기
                    val post = HttpPost(SIGNIN_URL + urlSuffix)  // 주소 뒤에 데이터를 넣기

                    val response = client.execute(post) // 데이터 보내기

                    val bufreader = BufferedReader(
                            InputStreamReader(
                                    response.entity.content, "utf-8"))

                    var line: String? = null
                    var page = ""

                    while ((line = bufreader.readLine()) != null) {
                        page += line
                    }

                    clickTime++
                    return page
                } catch (e: Exception) {

                    return null
                }

            }
        }

        val su = SignupUser()
        su.execute(urlSuffix)
    }
}
