package com.andrstudy.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_management.*
import okhttp3.*
import java.io.IOException
import android.provider.Settings
import android.content.Intent
import android.graphics.Bitmap
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList

class Management : AppCompatActivity() {

    var USER_QR_CODE : String?="1"
    var USER_NAME : String?="1"
    var USER_PHONE : String?="1"
    var USER_ADMIN : Int?= null
    var android_ID : String?=null
    var intent1 : Intent? = null

    var  listData = ArrayList<HashMap<String, Any>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_management)
        android_ID = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID)
        //해당 QR코드에 등록된 기기를 모두 서버에서 불러와 리스트로
        //admin이면 편집기능 활성화(삭제)
        //나머지는 조회만가능

        var adapter = ListAdapter()
        listView.adapter = adapter

        USER_QR_CODE=intent.getStringExtra("USER_QR_CODE")

    }

    override fun onResume() {
        super.onResume()
        var thread = NetworkThread()
        thread.start()
    }

    inner class NetworkThread : Thread(){
        override fun run() {
            var client = OkHttpClient()
            // 요청할 페이지 주소
            var builder = Request.Builder()

            var url = builder.url("http://203.244.145.214:8089/QrLockDoor/qrCheck.jsp")

            var bodyBuilder = FormBody.Builder()
            //bodyBuilder.add("USER_QR_CODE", USER_QR_CODE)
            bodyBuilder.add("USER_QR_CODE", USER_QR_CODE)

            var body = bodyBuilder.build()
            var post = url.post(body)

            var request = post.build()

            client.newCall(request).enqueue(CallBack1())
        }
    }

    inner class ListAdapter : BaseAdapter(){
        override fun getCount(): Int {
            return listData.size
        }

        override fun getItem(p0: Int): Any {
            return 0
        }

        override fun getItemId(p0: Int): Long {
            return 0
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var convertView = p1
            if(convertView == null){
                convertView = layoutInflater.inflate(R.layout.clientlist, null)
            }
            /*var img1 = convertView?.findViewById<ImageView>(R.id.imageView)*/
            var str1 = convertView?.findViewById<TextView>(R.id.textView)
            var str2 = convertView?.findViewById<TextView>(R.id.textView2)
            var str3 = convertView?.findViewById<TextView>(R.id.textView3)
            var map = listData.get(p0)

          /*  var mobile_img = map.get("mobile_img") as String*/
            var mobile_str1 = map.get("mobile_str1") as String
            var mobile_str2 = map.get("mobile_str2") as String
            var mobile_str3 = map.get("mobile_str3") as String
          /*  var bitmap: Bitmap? = imageMap.get(mobile_img)

            if(bitmap == null){

                var thread2 = ImageNetworkThread(mobile_img as String)
                thread2.start()
            }else{
                img1?.setImageBitmap(bitmap)
            }*/
            str1?.text = mobile_str1
            str2?.text = mobile_str2
            str3?.text = mobile_str3
            return convertView!!
        }
    }

    inner class CallBack1 : Callback {
        // 서버와의 통신이 실패되었을 때
        override fun onFailure(call: Call, e: IOException) {
//
        }
        // 서버와의 통신이 잘 마무리 되었을 때
        override fun onResponse(call: Call, response: Response) {
            var result = response?.body()?.string()

            var array = JSONArray(result)

            for (i in 0 until array.length()) {
                var obj = array.getJSONObject(i)

                var mobile_str1 = obj.getString("user_name")
                var mobile_str2 = obj.getString("user_phone")
                var mobile_str3 = obj.getString("user_admin")
                var map = HashMap<String, Any>()

                map.put("mobile_str1", mobile_str1)
                map.put("mobile_str2", mobile_str2)
                map.put("mobile_str3", mobile_str3)

                listData.add(map)
            }

            runOnUiThread {
                var adapter = listView.adapter as ListAdapter
                adapter.notifyDataSetChanged()
            }

        }
    }
}


