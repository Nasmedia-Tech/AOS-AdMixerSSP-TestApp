package com.nasmedia.admixerkotlin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nasmedia.admixerssp.ads.AdEvent
import com.nasmedia.admixerssp.ads.AdInfo
import com.nasmedia.admixerssp.ads.AdListener
import com.nasmedia.admixerssp.ads.InterstitialVideoAd

class InterstitialVideoActivity : AppCompatActivity() {
    private lateinit var btnInterstitialVideoShow: Button
    private lateinit var progressBar: ProgressBar
    private var interstitialVideoAdView: InterstitialVideoAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interstitialvideo)
        progressBar = findViewById(R.id.loading_bar)
        btnInterstitialVideoShow = findViewById(R.id.btn_interstitial_video_show)
        val adInfo =
            AdInfo.Builder(Application.ADUNIT_ID_INTERSTITIAL_VIDEO) // AdMixer 플랫폼에서 발급받은 전면 비디오 ADUNIT_ID
                .interstitialTimeout(0) // 초단위로 전면 광고 타임아웃 설정 (기본값 : 0, 0 이면 서버지정 시간으로 처리, 서버지정 시간 : 20s)
                .maxRetryCountInSlot(-1) // 리로드 시간 내에 반복 횟수(-1 : 무한, 0 : 반복 없음, n : n번 반복)
                .build()
        interstitialVideoAdView = InterstitialVideoAd(this)
        // 이 때 설정하신 Interstitial Video 의 부모 activity 는 원활한 광고 제공을 위해 hardwareAccelerated 가 true 설정되오니 참고 부탁드립니다.
        interstitialVideoAdView!!.setAdInfo(adInfo)
        interstitialVideoAdView!!.setListener(object : AdListener {
            override fun onReceivedAd(p0: String?, p1: Any?) {
                // 광고 수신 성공
                Toast.makeText(this@InterstitialVideoActivity, "onReceivedAd", Toast.LENGTH_SHORT)
                    .show()
                btnInterstitialVideoShow.setText("전면 비디오 광고보기")
                progressBar.setVisibility(View.GONE)
            }

            override fun onFailedToReceiveAd(p0: Any?, p1: String?, p2: Int, p3: String?) {
                // 광고 수신 실패
                Toast.makeText(this@InterstitialVideoActivity, p3, Toast.LENGTH_SHORT).show()
            }

            override fun onEventAd(p0: Any?, p1: AdEvent?) {
                // 기타 이벤트
                Toast.makeText(this@InterstitialVideoActivity, "onEventAd : " + p1.toString(), Toast.LENGTH_SHORT).show()

                when (p1) {

                    AdEvent.CLOSE, AdEvent.SKIPPED -> { // 광고 창이 닫혔을 때
                        interstitialVideoAdView?.closeInterstitialVideoAd()
                        btnInterstitialVideoShow.text = "전면 비디오 광고요청"
                    }
                    AdEvent.DISPLAYED -> {} // 광고 노출
                    else -> {}
                }
            }
        })

        // 광고 요청 후 광고를 받은 뒤, 원하는 시점에 노출
        btnInterstitialVideoShow.setOnClickListener(View.OnClickListener { v: View? ->
            if (interstitialVideoAdView!!.hasInterstitial) interstitialVideoAdView!!.showInterstitialVideoAd() // 광고를 노출한다.
            else {
                interstitialVideoAdView!!.loadInterstitialVideoAd() // 광고를 미리 로드한다.
                progressBar.setVisibility(View.VISIBLE)
            }
        })
    }

    // 생명주기에 따라 아래 설정이 반드시 필요합니다.
    override fun onDestroy() {
        if (interstitialVideoAdView != null) {
            interstitialVideoAdView!!.stopInterstitialVideoAd()
            interstitialVideoAdView = null
        }
        super.onDestroy()
    }
}