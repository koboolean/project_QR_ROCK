package com.example.yeo.smartlock

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MAC_ad : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mac_ad)
        //(최초실행시,QR코드 재등록시)
        //버튼을 클릭할시
        //editText에 있는 문자열에대한 무결성을 검사하고
        //MAC주소양식과 일치한다면 저장하고
        //next activity인 QR_scaner로 MAC주소를 전달한다.
    }
}