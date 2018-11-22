package com.example.yeo.smartlock

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.yeo.smartlock.R.layout.activity_lock_main
import com.example.yeo.smartlock.R.layout.activity_mac_ad

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var pref = this.getSharedPreferences("isFirst",Activity.MODE_PRIVATE)
        var first = pref.getBoolean("isFirst",true)

        if(first){
            var mac_add_Intent = Intent(this, activity_mac_ad::class.java)
            startActivity(mac_add_Intent)

            pref.edit().putBoolean("isFirst",false).apply();
        }

        else {
            var lockmain_Intent = Intent(this, activity_lock_main::class.java)
            startActivity(lockmain_Intent)
        }
    }
}
