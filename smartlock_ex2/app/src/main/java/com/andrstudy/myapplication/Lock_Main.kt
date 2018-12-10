package com.andrstudy.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_lock_main.*

class Lock_Main : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock_main)
        //재등록 버튼 flag1
        //문열기 버튼 flag2
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
            var management_Intent = Intent(this, Management::class.java)
            startActivity(management_Intent)
        }

    }
}
