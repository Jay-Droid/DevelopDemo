package com.jay.developdemo.um

import android.content.Context
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.jay.developdemo.main.DemoApplication
import com.umeng.commonsdk.UMConfigure
import com.umeng.message.IUmengRegisterCallback
import com.umeng.message.PushAgent
import com.umeng.message.UmengNotificationClickHandler
import com.umeng.message.entity.UMessage
import com.umeng.message.UTrack
import android.os.Looper.getMainLooper
import com.umeng.message.UmengMessageHandler
import android.R
import android.app.Notification
import android.widget.RemoteViews


/**
 * Author：Jay On 2019/6/1 22:13
 * Description:
 */
class UMPushHelper {

    companion object {
        val TAG: String = UMPushHelper::class.java.simpleName
    }

    fun init(demoApplication: DemoApplication) {
        Log.d(TAG, "初始化友盟推送")

        /**
         * 初始化UMConfigure
         */
        // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
        // 参数一：当前上下文context；
        // 参数二：应用申请的Appkey（需替换）；
        // 参数三：渠道名称；
        // 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
        // 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息（需替换）
        UMConfigure.init(
            demoApplication,
            "5cf271ab4ca35744a6001389",//替换为Appkey,服务后台位置：应用管理 -> 应用信息 -> Appkey
            "Umeng",
            UMConfigure.DEVICE_TYPE_PHONE,
            "8a73f4d44a0c7d789ce7951dd6270d84"//替换为秘钥信息,服务后台位置：应用管理 -> 应用信息 -> Umeng Message Secret
        )

        /**
         * 注册设备获取deviceToken
         */
        //Push注册在UMConfigure.init方法之后注册，注册成功后可获取deviceToken，进行消息下发
        //获取消息推送代理示例
        val mPushAgent = PushAgent.getInstance(demoApplication)
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(object : IUmengRegisterCallback {
            override fun onSuccess(deviceToken: String) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                //deviceToken是【友盟+】消息推送生成的用于标识设备的id，长度为44位，不能定制和修改。
                //同一台设备上不同应用对应的deviceToken不一样。获取deviceToken的值后，可进行消息推送测试！
                Log.d(TAG, "友盟推送注册成功：deviceToken：-------->  $deviceToken")
                //deviceToken：-------->  Au4O8wyyalug55ry6OhYJ2TiMe_pXQGrI__QcdzUACiw
            }

            override fun onFailure(s: String, s1: String) {
                Log.e(TAG, "友盟推送注册失败：-------->  s:$s,s1:$s1")
            }
        })

        /**
         * 自定义通知栏样式
         */
        val messageHandler1 = object : UmengMessageHandler() {
            override fun getNotification(context: Context?, msg: UMessage): Notification {

                //默认为0，若填写的builder_id并不存在，也使用默认。
                return super.getNotification(context, msg)
            }
        }
        mPushAgent.messageHandler = messageHandler1

        /**
         * 自定义通知栏打开动作
         */
        val notificationClickHandler = object : UmengNotificationClickHandler() {
            override fun handleMessage(context: Context?, msg: UMessage?) {
                Toast.makeText(context, msg?.custom, Toast.LENGTH_LONG).show()
                Log.d(TAG, "自定义通知栏打开动作：-------->  custom:${msg?.toString()}")
            }
        }
        mPushAgent.notificationClickHandler = notificationClickHandler

        /**
         * 自定义消息（消息透传）
         */
        val messageHandler = object : UmengMessageHandler() {
            override fun dealWithCustomMessage(context: Context?, msg: UMessage?) {
                Handler(getMainLooper()).post(Runnable {
                    // 对于自定义消息，PushSDK默认只统计送达。若开发者需要统计点击和忽略，则需手动调用统计方法。
                    val isClickOrDismissed = true
                    if (isClickOrDismissed) {
                        //自定义消息的点击统计
                        UTrack.getInstance(demoApplication).trackMsgClick(msg)
                    } else {
                        //自定义消息的忽略统计
                        UTrack.getInstance(demoApplication).trackMsgDismissed(msg!!)
                    }
                    Log.d(TAG, "自定义消息（消息透传）：-------->  custom:${msg?.custom}")
                })
            }
        }
        mPushAgent.messageHandler = messageHandler
    }

}
