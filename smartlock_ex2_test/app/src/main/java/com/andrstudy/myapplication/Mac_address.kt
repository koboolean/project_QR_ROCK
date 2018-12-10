package com.andrstudy.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.andrstudy.myapplication.R.id.button
import com.andrstudy.myapplication.R.layout.activity_qr__scanner
import kotlinx.android.synthetic.main.activity_mac_address.*

class Mac_address : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mac_address)

        //editText에 있는 문자열에대한 무결성을 검사하고 **차후**
        //MAC주소양식과 일치한다면 저장하고 **차후**
        //전화번호 +인증? **미정**

        button.setOnClickListener {
            val mac_adress: String = mac_text.text.toString()
            val phone_num: String = phone_text.text.toString()

            var QRscanner_Intent = Intent(this, activity_qr__scanner::class.java)
            QRscanner_Intent.putExtra("MAC_Adress", mac_adress)
            QRscanner_Intent.putExtra("Phone_num", phone_num)
            startActivity(QRscanner_Intent)
        }
    }
}
