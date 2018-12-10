package com.andrstudy.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator

class Scanner : AppCompatActivity() {
    internal var init_flag = 0;

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
                    //등록과정 코드를 인식하면 Server에 flag2를 1로넘김
                    var server_Intent = Intent(this@Scanner, Server::class.java)
                    server_Intent.putExtra("MAC_Adress", intent.getStringExtra("MAC_Adress"))
                    server_Intent.putExtra("Phone_num", intent.getStringExtra("Phone_num"))
                    server_Intent.putExtra("QR_code", result.contents)
                    server_Intent.putExtra("flag2",1)
                    startActivity(server_Intent)
                }else if(init_flag == 2){
                   //문열기과정 코드를 인식하면 Server에 flag2를 2로넘김
                    var server_Intent = Intent(this@Scanner, Server::class.java)
                    server_Intent.putExtra("QR_code", result.contents)
                    server_Intent.putExtra("flag2",2)
                    startActivity(server_Intent)
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
