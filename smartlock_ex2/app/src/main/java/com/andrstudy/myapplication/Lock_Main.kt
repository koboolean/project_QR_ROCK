package com.andrstudy.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_lock__main.*
import okhttp3.*
import org.json.JSONArray
import java.io.IOException

class Lock_Main : AppCompatActivity() {
    var USER_QR_CODE : String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock__main)

        //재등록 버튼 flag1
        //문열기 버튼 flag2
        USER_QR_CODE=intent.getStringExtra("USER_QR_CODE")
        rein_btn.setOnClickListener {
            var re_register_Intent = Intent(this,Mac_address::class.java)
            startActivity(re_register_Intent)
        }
        open_btn.setOnClickListener {
            var open_Intent = Intent(this, Scanner::class.java)
            open_Intent.putExtra("init_flag1",2)
            startActivity(open_Intent)
        }
        manage_btn.setOnClickListener {

            var management_Intent = Intent(this@Lock_Main, Management::class.java)
            management_Intent.putExtra("USER_QR_CODE",USER_QR_CODE)
            startActivity(management_Intent)
            /*var thread = NetworkThread()
            thread.start()*/
        }



    }
    /*inner class NetworkThread : Thread(){
        override fun run() {
            var client = OkHttpClient()
            // 요청할 페이지 주소
            var builder = Request.Builder()

            var url = builder.url("http://203.244.145.214:8089/QrLockDoor/qrCheck.jsp")

            var bodyBuilder = FormBody.Builder()
            //bodyBuilder.add("USER_QR_CODE", USER_QR_CODE)
            bodyBuilder.add("USER_QR_CODE", USER_QR_CODE.toString())

            var body = bodyBuilder.build()
            var post = url.post(body)

            var request = post.build()

            client.newCall(request).enqueue(CallBack1())
        }
    }
    inner class CallBack1 : Callback {
        // 서버와의 통신이 실패되었을 때
        override fun onFailure(call: Call, e: IOException) {
//
        }
        // 서버와의 통신이 잘 마무리 되었을 때
        override fun onResponse(call: Call, response: Response) {
            var result = response?.body()?.string()

            Log.i("log", result)

            runOnUiThread{
                var obj = JSONArray(result)
                Log.i("sibal",obj.toString())

            }

        }
    }*/
}
