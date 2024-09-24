package com.nasmedia.admixerkotlin

import android.os.Bundle
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.nasmedia.admixerssp.ads.AdEvent
import com.nasmedia.admixerssp.ads.AdInfo
import com.nasmedia.admixerssp.ads.AdListener
import com.nasmedia.admixerssp.ads.AdView


class BannerActivity : AppCompatActivity() {

    private lateinit var container: RelativeLayout
    private var banner: AdView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner)
        container = findViewById(R.id.container_banner)

        val adInfo: AdInfo = AdInfo.Builder(Application.ADUNIT_ID_BANNER) // AdMixer 플랫폼에서 발급받은 배너 ADUNIT_ID;
            .setIsUseMediation(true) // 미디에이션 사용 여부 (true - 기본값, false - 미사용)
            .build()

        val params: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        banner = AdView(this@BannerActivity)
        banner?.layoutParams = params
        // 이 때 설정하신 Banner 의 부모 activity 는 원활한 광고 제공을 위해 hardwareAccelerated 가 true 설정되오니 참고 부탁드립니다.
        banner?.setAdInfo(adInfo)
        banner?.setAlwaysShowAdView(true) // 광고 로딩 전에도 영역을 차지할 것인지 설정(false – 기본값)

        banner?.setAdViewListener(object : AdListener {
            override fun onReceivedAd(p0: String?, p1: Any?) {
                // 광고 수신 성공
                Toast.makeText(this@BannerActivity, "onReceivedAd", Toast.LENGTH_SHORT).show()
                // [2번 방법] 광고를 받은 뒤, 원하는 시점에 노출
//                 if (banner?.hasAd == true) {  // 광고를 성공적으로 받았는지 판단
//                    container.removeView(banner);
//                    container.addView(banner); // 레이아웃에 배너 광고 뷰를 추가
//                    b
            }

            override fun onFailedToReceiveAd(p0: Any?, p1: String?, p2: Int, p3: String?) {
                // 광고 수신 실패
                Toast.makeText(this@BannerActivity, "onFailedToReceiveAd \n $p2", Toast.LENGTH_SHORT).show()
            }

            override fun onEventAd(p0: Any?, p1: AdEvent?) {
                // 기타 이벤트
                when (p1) {
                    AdEvent.CLICK -> {}
                    AdEvent.DISPLAYED -> {}
                    else -> {}
                }
            }
        })

        // [1번 방법] 광고 요청 후 받은 즉시 노출
        container.addView(banner)

        // [2번 방법] 광고 요청 후 광고를 받은 뒤, 원하는 시점에 노출
//         banner?.loadAd(); // 광고를 미리 로드한다
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

