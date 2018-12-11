package com.andrstudy.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import kotlinx.android.synthetic.main.activity_server.*

class Server : AppCompatActivity() {

    internal var init_flag2 = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server)

        init_flag2 = intent.getIntExtra("flag2",0)

        if(init_flag2 ==1) {
            //서버에 연결해서 데이터 저장
            //진입시 버튼과 에딧택스트 가시
            var MAC_ADD = intent.getStringExtra("MAC_Adress")
            var USER_QR_CODE = intent.getStringExtra("QR_code")
            var USER_POHNE = intent.getStringExtra("Phone_num")

            button4.setOnClickListener {
                var USER_PASWORD: String = pass_word_text.text.toString()
                var USER_DEIVCE_ID = Settings.Secure.ANDROID_ID


                Log.i("MAC_ADD", MAC_ADD)
                Log.i("USER_QR_CODE", USER_QR_CODE)
                Log.i("USER_POHNE", USER_POHNE)
                Log.i("USER_PASWORD", USER_PASWORD)
                Log.i("USER_DEIVCE_ID", USER_DEIVCE_ID)


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
}
