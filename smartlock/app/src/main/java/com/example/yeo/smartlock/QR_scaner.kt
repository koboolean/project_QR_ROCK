package com.example.yeo.smartlock

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class QR_scaner : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_scaner)
        //!! manifests에서 QR코드 리더 권한  !! 아직 부여안함!!
        //이전 Activity에서 MAC주소를 저장하고
        //QR코드 리더기가 QR코드 인식에 성공하면
        //QR코드 URL과 이전 Activity에서 받은 MAC주소를 Save로 전달하며 전환
    }
}
