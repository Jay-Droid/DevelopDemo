package com.jay.developdemo.um

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jay.developdemo.R
import com.umeng.message.PushAgent

class UMActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_um)
        //该方法是【友盟+】Push后台进行日活统计及多维度推送的必调用方法，请务必调用！
        PushAgent.getInstance(this).onAppStart()
    }
}
