package com.jay.developdemo.main

import android.app.Application
import com.jay.developdemo.um.UMPushHelper
import android.widget.Toast
import android.R.id.custom
import com.umeng.message.entity.UMessage
import com.umeng.message.UmengNotificationClickHandler




/**
 * Author：Jay On 2019/6/1 20:41
 * Description:
 */
class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val umPushHelper = UMPushHelper()
        umPushHelper.init(this)

    }

}
