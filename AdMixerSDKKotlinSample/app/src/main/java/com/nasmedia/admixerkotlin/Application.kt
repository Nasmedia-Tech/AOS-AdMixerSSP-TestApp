package com.nasmedia.admixerkotlin

import android.app.Application
import com.nasmedia.admixerssp.common.AdMixer
import com.nasmedia.admixerssp.common.AdMixerLog

open class Application : Application() {
    companion object {
        const val MEDIA_KEY = "10065"
        const val ADUNIT_ID_BANNER = "100268"
        const val ADUNIT_ID_INTERSTITIAL_BANNER = "AdMixer 플랫폼에서 발급받은 전면 배너 ADUNIT_ID"
        const val ADUNIT_ID_NATIVE = "AdMixer 플랫폼에서 발급받은 네이티브 ADUNIT_ID"
        const val ADUNIT_ID_VIDEO = "AdMixer 플랫폼에서 발급받은 비디오 ADUNIT_ID"
        const val ADUNIT_ID_INTERSTITIAL_VIDEO = "AdMixer 플랫폼에서 발급받은 전면비디오 ADUNIT_ID"
        const val ADUNIT_ID_REWARD_INTERSTITIAL_VIDEO = "AdMixer 플랫폼에서 발급받은 리워드전면 ADUNIT_ID"

        val adUnits = arrayListOf(
            ADUNIT_ID_BANNER
            // , ADUNIT_ID_NATIVE, ...
        )
    }

    override fun onCreate() {
        super.onCreate()
        AdMixerLog.logLevel = AdMixerLog.LogLevel.VERBOSE
        // 특정 브라우저 지정
//        AdMixer.setBrowser(listOf("com.sec.android.app.sbrowser", "com.android.chrome") as ArrayList<String>?)

        // COPPA(아동보호법) 관련 항목 설정값 - 선택사항
        AdMixer.setTagForChildDirectedTreatment(AdMixer.AX_TAG_FOR_CHILD_DIRECTED_TREATMENT_FALSE)

        // AdMixer 초기화를 위해 반드시 광고 호출 전에 앱에서 1회 호출해주셔야 합니다.
        // adunits 파라미터는 앱 내에서 사용할 모든 adunit_id 를 배열 형태로 넘겨 주셔야 합니다.
        // XXXX_ADUNIT_ID 는 Admixer 사이트 미디어 > 미디어관리 > 미디어 등록에서 발급받은 Adunit ID 입니다.
        AdMixer.getInstance().initialize(this, MEDIA_KEY, adUnits )
        AdMixer.registerAdapter(AdMixer.ADAPTER_ADMANAGER)
        AdMixer.registerAdapter(AdMixer.ADAPTER_ADFIT)
        AdMixer.registerAdapter(AdMixer.ADAPTER_MOBWITH)
        AdMixer.registerAdapter(AdMixer.ADAPTER_PANGLE)
        AdMixer.registerAdapter(AdMixer.ADAPTER_APPLOVIN)
        AdMixer.registerAdapter(AdMixer.ADAPTER_UNITY)

    }
}