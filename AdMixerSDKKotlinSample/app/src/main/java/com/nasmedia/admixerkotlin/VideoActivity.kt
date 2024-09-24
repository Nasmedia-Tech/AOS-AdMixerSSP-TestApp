package com.nasmedia.admixerkotlin

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nasmedia.admixerssp.ads.AdEvent
import com.nasmedia.admixerssp.ads.AdInfo
import com.nasmedia.admixerssp.ads.AdListener
import com.nasmedia.admixerssp.ads.VideoAdView

class VideoActivity : AppCompatActivity() {
    private var videoAdView: VideoAdView? = null
    private lateinit var container: RelativeLayout
    private lateinit var tvComplete: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        container = findViewById(R.id.container_video)
        tvComplete = findViewById(R.id.tv_complete)
        val adInfo = AdInfo.Builder(Application.ADUNIT_ID_VIDEO) // AdMixer 플랫폼에서 발급받은 배너 ADUNIT_ID
            .build()
        videoAdView = VideoAdView(this)
        // 이 때 설정하신 video 의 부모 activity 는 원활한 광고 제공을 위해 hardwareAccelerated 가 true 설정되오니 참고 부탁드립니다.
        videoAdView!!.setAdInfo(adInfo)
        videoAdView!!.setAdViewListener(object : AdListener {
                    override fun onReceivedAd(p0: String?, p1: Any?) {
                // 광고 수신 성공
                Toast.makeText(this@VideoActivity, "onReceivedAd", Toast.LENGTH_SHORT).show()
                // 광고를 노출한다.
                val params = RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                params.addRule(RelativeLayout.CENTER_IN_PARENT)
                container.removeView(videoAdView)
                container.addView(videoAdView, params) // 레이아웃에 Video 광고 뷰를 추가 [ 광고 노출 ]
            }

            override fun onFailedToReceiveAd(p0: Any?, p1: String?, p2: Int, p3: String?) {
                // 광고 수신 실패
                Toast.makeText(this@VideoActivity, p3, Toast.LENGTH_SHORT).show()
            }

            override fun onEventAd(o: Any, adEvent: AdEvent) {
                // 기타 이벤트
                Toast.makeText(this@VideoActivity, adEvent.toString(), Toast.LENGTH_SHORT).show()
                when (adEvent) {
                    AdEvent.COMPLETION, AdEvent.SKIPPED -> {
                        val params = RelativeLayout.LayoutParams(
                            videoAdView!!.width, videoAdView!!.height
                        )
                        params.addRule(RelativeLayout.CENTER_IN_PARENT)
                        tvComplete.setLayoutParams(params)
                        tvComplete.setVisibility(View.VISIBLE)
                    }

                    AdEvent.CLICK -> {}
                    else -> {}
                }
            }
        })

        // 광고 요청 후 광고를 받은 뒤, 원하는 시점에 노출
        videoAdView!!.loadAd() // 광고를 미리 로드한다.
    }

    // 생명주기에 따라 아래 설정이 반드시 필요합니다.
    override fun onResume() {
        if (videoAdView != null) {
            videoAdView!!.onResume()
        }
        super.onResume()
    }

    override fun onPause() {
        if (videoAdView != null) {
            videoAdView!!.onPause()
        }
        super.onPause()
    }

    override fun onDestroy() {
        if (videoAdView != null) {
            videoAdView!!.onDestroy()
            videoAdView = null
        }
        super.onDestroy()
    }
}