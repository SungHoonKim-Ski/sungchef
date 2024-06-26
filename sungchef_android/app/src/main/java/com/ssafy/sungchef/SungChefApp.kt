package com.ssafy.sungchef

import android.app.Application
import android.util.Log

import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.HiltAndroidApp

private const val TAG = "SungChefApp_성식당"
@HiltAndroidApp
class SungChefApp:Application() {
    override fun onCreate() {
        super.onCreate()
        var keyHash = Utility.getKeyHash(this)
        Log.d(TAG, "onCreate: $keyHash")
        // Kakao Sdk 초기화
        KakaoSdk.init(this, BuildConfig.NATIVE_APP_KEY)

        // Naver Sdk 초기화
        NaverIdLoginSDK.initialize(this, BuildConfig.NAVER_CLIENT_ID, BuildConfig.NAVER_CLIENT_SECRET, "sungchef")
    }
}