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
import com.nasmedia.admixerssp.ads.RewardInterstitialVideoAd

class RewardInterstitialVideoActivity : AppCompatActivity() {
    private lateinit var btnRewardVideoShow: Button
    private lateinit var progressBar: ProgressBar
    private var rewardInterstitialVideoAd: RewardInterstitialVideoAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewardvideo)
        progressBar = findViewById(R.id.loading_bar)
        btnRewardVideoShow = findViewById(R.id.btn_reward_video_show)

        val params: MutableMap<String, String> = HashMap()
        params["use_id"] = "nas"
        params["name"] = "choi"
        params["phone"] = "010-1111-1111"

        val adInfo =
            AdInfo.Builder(Application.ADUNIT_ID_REWARD_INTERSTITIAL_VIDEO) // AdMixer 플랫폼에서 발급받은 리워드 전면 비디오 ADUNIT_ID
                .interstitialTimeout(0) // 초단위로 전면 광고 타임아웃 설정 (기본값 : 0, 0 이면 서버지정 시간으로 처리, 서버지정 시간 : 20s)
                .maxRetryCountInSlot(-1) // 리로드 시간 내에 반복 횟수(-1 : 무한, 0 : 반복 없음, n : n번 반복)
                .setIsUseMediation(true) // 미디에이션 사용 여부 (true - 기본값, false - 미사용)
                .setCustomParams(params) // Reward Callback 커스텀데이터 Map형태로 추가 (선택사항)
                .build()
        rewardInterstitialVideoAd = RewardInterstitialVideoAd(this)
        // 이 때 설정하신 RewardInterstitialVideoAd 의 부모 activity 는 원활한 광고 제공을 위해 hardwareAccelerated 가 true 설정되오니 참고 부탁드립니다.
        rewardInterstitialVideoAd!!.setAdInfo(adInfo)
        rewardInterstitialVideoAd!!.setListener(object : AdListener {

            override fun onReceivedAd(p0: String?, p1: Any?) {
                // 광고 수신 성공
                Toast.makeText(
                    this@RewardInterstitialVideoActivity,
                    "onReceivedAd",
                    Toast.LENGTH_SHORT
                ).show()
                btnRewardVideoShow.setText("리워드 전면 비디오 광고보기")
                progressBar.setVisibility(View.GONE)
            }

            override fun onFailedToReceiveAd(p0: Any?, p1: String?, p2: Int, p3: String?) {
                // 광고 수신 실패
                Toast.makeText(this@RewardInterstitialVideoActivity, p3, Toast.LENGTH_SHORT).show()
            }

            override fun onEventAd(o: Any, adEvent: AdEvent) {
                // 기타 이벤트
                when (adEvent) {
                    AdEvent.CLOSE, AdEvent.SKIPPED -> { // 광고 창이 닫혔을 때
                        rewardInterstitialVideoAd!!.closeRewardVideoAd()
                        btnRewardVideoShow.setText("리워드 전면 비디오 광고요청")
                    }

                    AdEvent.DISPLAYED -> {} // 광고 노출
                    else -> {}
                }
            }
        })

        // 광고 요청 후 광고를 받은 뒤, 원하는 시점에 노출
        btnRewardVideoShow.setOnClickListener(View.OnClickListener { v: View? ->
            if (rewardInterstitialVideoAd!!.hasInterstitial) rewardInterstitialVideoAd!!.showRewardVideoAd() // 광고를 노출한다.
            else {
                rewardInterstitialVideoAd!!.loadRewardVideoAd() // 광고를 미리 로드한다.
                progressBar.setVisibility(View.VISIBLE)
            }
        })
    }

    // 생명주기에 따라 아래 설정이 반드시 필요합니다.
    override fun onDestroy() {
        if (rewardInterstitialVideoAd != null) {
            rewardInterstitialVideoAd!!.stopRewardVideoAd()
            rewardInterstitialVideoAd = null
        }
        super.onDestroy()
    }
}