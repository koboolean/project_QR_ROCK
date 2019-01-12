package com.andrstudy.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_server.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class Server : AppCompatActivity() {
    var MAC_ADD : String?="2"
    var USER_QR_CODE : String?="2"
    var USER_PHONE  : String?="2"
    var USER_PASWORD: String?="2"
    var USER_DEIVCE_ID  : String?="2"
    var USER_NAME : String?="2"
    var USER_ADMIN : Int?=2
    //var textview : TextView? = null//mac_text_view
    var show_edit: String?=null
    var editText : String? = null//mac_text

    internal var init_flag2 = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server)

        init_flag2 = intent.getIntExtra("flag2",0)



        if(init_flag2 ==1) {
            //서버에 연결해서 데이터 저장 //진입시 버튼과 에딧택스트 가시
            /*server_Intent?.putExtra("QR_Code",QR_CODE)
            server_Intent?.putExtra("User_name",User_name)
            server_Intent?.putExtra("Phone_num",Phone_num)
            server_Intent?.putExtra("MAC_ADD", MAC_ADD)
            server_Intent?.putExtra("flag2",init_flag)*/
            USER_QR_CODE = intent.getStringExtra("QR_Code")
            Log.i("FIRSTUSER_QR_CODE", USER_QR_CODE.toString())
            //show_edit = findViewById<TextView>(R.id.mac_text_view).toString()

            //editText=findViewById<TextView>(R.id.mac_text).toString()

            MAC_ADD = intent.getStringExtra("MAC_ADD")

            Log.i("MAC_ADD ::::", intent.getStringExtra("MAC_ADD"))

            if (MAC_ADD.equals("") ||MAC_ADD==null||MAC_ADD.equals("NULL")||MAC_ADD.equals("null")) {
                //관리자인경우
                mac_text_view.visibility = View.GONE

                MAC_ADD = mac_text.text.toString()

            }
            else
            {
                //회원인 경우 mac_add가 존재
                mac_text.visibility= View.GONE
                mac_text_view.setText("mac : $MAC_ADD")
            }


            button4.setOnClickListener {
                // MAC_ADD = intent.getStringExtra("MAC_Adress")

                USER_PHONE = intent.getStringExtra("Phone_num")
                USER_NAME = intent.getStringExtra("User_name")

                USER_PASWORD = pass_word_text.text.toString()
                USER_DEIVCE_ID = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID)


                MAC_ADD = mac_text.text.toString()

                Log.i("USER_QR_CODE", USER_QR_CODE.toString())
                Log.i("USER_PHONE", USER_PHONE.toString())
                Log.i("USER_PASWORD", USER_PASWORD.toString())
                Log.i("USER_DEIVCE_ID", USER_DEIVCE_ID.toString())

                var management_intent = Intent(this@Server, Management::class.java)
                management_intent.putExtra("USER_QR_CODE", USER_QR_CODE)
                startActivity(management_intent)

                var thread = NetworkThread()
                thread.start()


                var lockMain_Intent = Intent (this,Lock_Main::class.java)
                startActivity(lockMain_Intent)
            }

        }
        else if(init_flag2 == 2){
            //서버에 연결
            //받아온 QR코드에 넘김
            var USER_QR_CODE = intent.getStringExtra("QR_code")

            button4.setOnClickListener(){
                var lockMain_Intent = Intent (this,Lock_Main::class.java)
                startActivity(lockMain_Intent)
            }
        }
    }

    /*override fun onStart() {
        super.onStart()
        setContentView(R.layout.activity_server)
    }*/

    inner class NetworkThread : Thread(){
        override fun run() {
            var client = OkHttpClient()
            // 요청할 페이지 주소
            var builder = Request.Builder()

            var url = builder.url("http://203.244.145.214:8089/QrLockDoor/insertDB.jsp")

            var bodyBuilder = FormBody.Builder()
            bodyBuilder.add("MAC_ADD", MAC_ADD)
            bodyBuilder.add("USER_PHONE", USER_PHONE)
            bodyBuilder.add("USER_QR_CODE", USER_QR_CODE)
            bodyBuilder.add("USER_NAME", USER_NAME)
            bodyBuilder.add("USER_PASWORD", USER_PASWORD)
            bodyBuilder.add("USER_DEIVCE_ID",USER_DEIVCE_ID)


            var body = bodyBuilder.build()
            var post = url.post(body)

            var request = post.build()

            client.newCall(request).enqueue(CallBack1())
        }
    }
    inner class CallBack1 : Callback{
        // 서버와의 통신이 실패되었을 때
        override fun onFailure(call: Call, e: IOException) {
            /*textView1?.text = "현재 통신이 원활하지 않습니다."*/
            /*Toast.makeText(getApplicationContext(),"현재 통신이 원활하지 않습니다.",Toast.LENGTH_LONG).show();*/
        }
        // 서버와의 통신이 잘 마무리 되었을 때
        override fun onResponse(call: Call, response: Response) {
            var result = response?.body()?.string()
            /*textView1?.text = "현재 통신이 원활하다."*/
            /*Toast.makeText(getApplicationContext(),"현재 통신이 원활합니다 영희야^^.",Toast.LENGTH_LONG).show();*/
            Log.i("log", result)
            runOnUiThread{
                var obj = JSONObject(result)

            }
        }
    }


}
