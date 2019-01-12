package com.andrstudy.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_mac_address.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class Mac_address : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mac_address)

        //editText에 있는 문자열에대한 무결성을 검사하고 **차후**
        //MAC주소양식과 일치한다면 저장하고 **차후**
        //전화번호 +인증? **미정**

        input_mac.setOnClickListener {
            var scanner_Intent = Intent(this@Mac_address, Scanner::class.java)
           // var mac_adress = mac_text.text.toString()
            var phone_num = phone_text.text.toString()
            var user_name = name_text.text.toString()

            //scanner_Intent.putExtra("MAC_Adress", mac_adress)
            scanner_Intent.putExtra("Phone_num", phone_num)
            scanner_Intent.putExtra("User_name", user_name)
            scanner_Intent.putExtra("init_flag1",1)
            startActivity(scanner_Intent)
        }
    }

}
