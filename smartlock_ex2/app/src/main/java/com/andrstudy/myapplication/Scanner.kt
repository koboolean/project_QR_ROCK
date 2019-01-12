package com.andrstudy.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_server.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class Scanner : AppCompatActivity() {

    internal var init_flag = 0;

    var MAC_ADD : String?=null
    var QR_CODE : String?=null
    var Phone_num : String?=null
    var User_name : String?=null


    var aaa : String?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)
        init_flag = intent.getIntExtra("init_flag1",0)

        if(init_flag != 0)
            IntentIntegrator(this).initiateScan()



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                // 이부분에 결과값들어옴
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                if(init_flag == 1) {
                    User_name=intent.getStringExtra("User_name").toString()
                    Phone_num=intent.getStringExtra("Phone_num")
                    //등록과정 코드를 인식하면 Server에 flag2를 1로넘김
//                    var server_Intent = Intent(this@Scanner, Server::class.java)
                    //server_Intent.putExtra("MAC_Adress", intent.getStringExtra("MAC_Adress"))
                    /*server_Intent?.putExtra("Phone_num", intent.getStringExtra("Phone_num"))
                    Log.i("phone", intent.getStringExtra("Phone_num"))
                    server_Intent?.putExtra("User_name", intent.getStringExtra("User_name"))*/
                    //server_Intent?.putExtra("QR_code", result.contents)
                    QR_CODE=result.contents.toString()
                    init_flag=1
                    //server_Intent?.putExtra("flag2",1)
                    var thread1 = NetworkThread1()
                    thread1.start()
                    Log.i("여기오나요?",result.contents.toString())


                    //startActivity(server_Intent)
                }else if(init_flag == 2){
                    //문열기과정 코드를 인식하면 Server에 flag2를 2로넘김
                   // var server_Intent = Intent(this@Scanner, Server::class.java)

//                    server_Intent?.putExtra("QR_code", result.contents)
//                    server_Intent?.putExtra("flag2",2)

                    init_flag=2
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

        Log.i("qrqrqrqr",result.contents)
        QR_CODE = result.contents.toString()

    }

    inner class NetworkThread1 : Thread(){
        override fun run() {
            var client = OkHttpClient()
            // 요청할 페이지 주소
            var builder = Request.Builder()

            var url = builder.url("http://203.244.145.214:8089/QrLockDoor/maccheck.jsp")

            var bodyBuilder = FormBody.Builder()

            Log.i("threadqrqr",QR_CODE.toString())
            bodyBuilder.add("USER_QR_CODE", QR_CODE.toString())

            var body = bodyBuilder.build()
            var post = url.post(body)

            var request = post.build()

            client.newCall(request).enqueue(CallBack1())

          /*  Log.i("threadMac2222", MAC_ADD)*/


        }
    }

    inner class CallBack1 : Callback {
        // 서버와의 통신이 실패되었을 때
        override fun onFailure(call: Call, e: IOException) {
//
        }
        // 서버와의 통신이 잘 마무리 되었을 때
        override fun onResponse(call: Call, response: Response)  {
            var result = response?.body()?.string()
             var callmac : String?=null
            Log.i("log123456", result)

            runOnUiThread{
                var obj = JSONObject(result)

                MAC_ADD = obj.getString("MAC_ADD")
                Log.i("objMac", MAC_ADD)
                var server_Intent = Intent(this@Scanner, Server::class.java)
                server_Intent?.putExtra("QR_Code",QR_CODE)
                server_Intent?.putExtra("User_name",User_name)
                server_Intent?.putExtra("Phone_num",Phone_num)
                server_Intent?.putExtra("MAC_ADD", MAC_ADD)
                server_Intent?.putExtra("flag2",init_flag)
                startActivity(server_Intent)
            }

        }
    }
}

