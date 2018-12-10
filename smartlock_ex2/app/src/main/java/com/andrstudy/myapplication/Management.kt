package com.andrstudy.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class Management : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_management)
        //해당 QR코드에 등록된 기기를 모두 서버에서 불러와 리스트로
        //admin이면 편집기능 활성화(삭제)
        //나머지는 조회만가능
    }
}
