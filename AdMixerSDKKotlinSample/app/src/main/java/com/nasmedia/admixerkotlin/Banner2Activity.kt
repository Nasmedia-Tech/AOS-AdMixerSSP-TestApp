package com.nasmedia.admixerkotlin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nasmedia.admixerssp.ads.AdEvent
import com.nasmedia.admixerssp.ads.AdInfo
import com.nasmedia.admixerssp.ads.AdListener
import com.nasmedia.admixerssp.ads.AdView

class Banner2Activity : AppCompatActivity() {

    private var banner: AdView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner2)

        val adInfo: AdInfo =
            AdInfo.Builder(Application.ADUNIT_ID_BANNER) // AdMixer 플랫폼에서 발급받은 배너 ADUNIT_ID;
                .setIsUseMediation(true) // 미디에이션 사용 여부 (true - 기본값, false - 미사용)
                .build()

        banner = findViewById(R.id.banner2)
        // 이 때 설정하신 Banner 의 부모 activity 는 원활한 광고 제공을 위해 hardwareAccelerated 가 true 설정되오니 참고 부탁드립니다.
        banner?.setAdInfo(adInfo)
        banner?.setAlwaysShowAdView(true) // 광고 로딩 전에도 영역을 차지할 것인지 설정(false – 기본값)
        banner?.setAdViewListener(object : AdListener {
                     override fun onReceivedAd(p0: String?, p1: Any?) {
                // 광고 수신 성공
                Toast.makeText(this@Banner2Activity, "onReceivedAd", Toast.LENGTH_SHORT).show()
            }

            override fun onFailedToReceiveAd(p0: Any?, p1: String?, p2: Int, p3: String?) {
                // 광고 수신 실패
                Toast.makeText(this@Banner2Activity, "onFailedToReceiveAd \n $p3", Toast.LENGTH_SHORT).show()
            }

            override fun onEventAd(p0: Any?, p1: AdEvent?) {
                // 기타 이벤트
                when (p1) {
                    AdEvent.CLICK -> {} // 클릭 시
                    AdEvent.DISPLAYED -> {} // 노출 시
                    else -> {}
                }
            }
        })

    }

    // 생명주기에 따라 아래 설정이 반드시 필요합니다.
    override fun onResume() {
        if (banner != null) {
            banner?.onResume()
            banner = null
        }
        super.onResume()
    }

    override fun onPause() {
        if (banner != null) {
            banner?.onPause()
            banner = null
        }
        super.onPause()
    }

    override fun onDestroy() {
        if (banner != null) {
            banner?.onDestroy()
            banner = null
        }
        super.onDestroy()
    }

}

