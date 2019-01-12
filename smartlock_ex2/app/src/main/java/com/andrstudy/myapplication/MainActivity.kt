package com.andrstudy.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.io.IOException
import android.widget.Toast
import android.provider.Settings;
import android.util.Log
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    var android_ID  : String? = null
    var textView1 : TextView? = null
    var intent1 : Intent? = null
    var intent2 : Intent? = null // 값이 없다 == Mac_Address
    var user_Device_Id : String?=null
//    var user_qr_code : String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Thread.sleep(1500)


        textView1 = findViewById<TextView>(R.id.textView1)
        android_ID=findViewById<TextView>(R.id.android_ID).toString()

        android_ID = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID)

        var thread = NetworkThread()
        thread.start()

    }

    inner class NetworkThread : Thread(){
        override fun run() {
            var client = OkHttpClient()
            // 요청할 페이지 주소
            var builder = Request.Builder()

            var url = builder.url("http://203.244.145.214:8089/QrLockDoor/selectDB.jsp")

            var bodyBuilder = FormBody.Builder()
            bodyBuilder.add("user_Device_Id", android_ID)

            var body = bodyBuilder.build()
            var post = url.post(body)

            var request = post.build()

            client.newCall(request).enqueue(CallBack1())
        }
    }

    inner class CallBack1 : Callback{
        // 서버와의 통신이 실패되었을 때
        override fun onFailure(call: Call, e: IOException) {


        }
        // 서버와의 통신이 잘 마무리 되었을 때
        override fun onResponse(call: Call, response: Response) {
            var result = response?.body()?.string()

            Log.i("log", result)
            runOnUiThread{
                var obj = JSONObject(result)

                user_Device_Id = obj.getString("user_Device_Id")

                intent1 = Intent(this@MainActivity, Lock_Main::class.java)
                intent2 = Intent(this@MainActivity, Mac_address::class.java)
                if(!user_Device_Id.equals("?")){

                    intent1?.putExtra("USER_QR_CODE",obj.getString("user_QR_Code"))

                    startActivity(intent1)
                }else{
                    startActivity(intent2)
                }
            }
        }
    }
}
