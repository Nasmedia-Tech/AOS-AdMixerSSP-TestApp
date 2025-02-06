package com.nasmedia.admixer.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nasmedia.admixerssp.ads.AdEvent;
import com.nasmedia.admixerssp.ads.AdInfo;
import com.nasmedia.admixerssp.ads.AdListener;
import com.nasmedia.admixerssp.ads.RewardInterstitialVideoAd;

import java.util.HashMap;
import java.util.Map;

public class RewardInterstitialVideoActivity extends AppCompatActivity {

    private Button btnRewardVideoShow;
    private ProgressBar progressBar;
    private RewardInterstitialVideoAd rewardInterstitialVideoAd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewardvideo);

        progressBar = findViewById(R.id.loading_bar);
        btnRewardVideoShow = findViewById(R.id.btn_reward_video_show);

        Map<String, String> params = new HashMap<>();
        params.put("use_id", "nas");
        params.put("name", "choi");
        params.put("phone", "010-1111-1111");

        AdInfo adInfo = new AdInfo.Builder(Application.ADUNIT_ID_REWARD_INTERSTITIAL_VIDEO) // AdMixer 플랫폼에서 발급받은 리워드 전면 비디오 ADUNIT_ID
                .interstitialTimeout(0) // 초단위로 전면 광고 타임아웃 설정 (기본값 : 0, 0 이면 서버지정 시간으로 처리, 서버지정 시간 : 20s)
                .maxRetryCountInSlot(-1) // 리로드 시간 내에 반복 횟수(-1 : 무한, 0 : 반복 없음, n : n번 반복)
                .setCustomParams(params) // Reward Callback 커스텀데이터 Map형태로 추가 (선택사항)
                .setIsUseMediation(true) // 미디에이션 사용 여부 (true - 기본값, false - 미사용)
                .build();

        rewardInterstitialVideoAd = new RewardInterstitialVideoAd(this);
        // 이 때 설정하신 RewardInterstitialVideoAd 의 부모 activity 는 원활한 광고 제공을 위해 hardwareAccelerated 가 true 설정되오니 참고 부탁드립니다.
        rewardInterstitialVideoAd.setAdInfo(adInfo);
        rewardInterstitialVideoAd.setListener(new AdListener() {


            @Override
            public void onReceivedAd(String s, Object o) {
                // 광고 수신 성공
                Toast.makeText(RewardInterstitialVideoActivity.this, "onReceivedAd", Toast.LENGTH_SHORT).show();
                btnRewardVideoShow.setText("리워드 전면 비디오 광고보기");
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailedToReceiveAd(Object o, String s, int i, String s1) {
                // 광고 수신 실패
                Toast.makeText(RewardInterstitialVideoActivity.this, "onFailedToReceiveAd", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onEventAd(Object o, AdEvent adEvent) {
                // 기타 이벤트
                switch (adEvent) {
                    case CLOSE:   // 광고 창이 닫혔을 때
                    case SKIPPED: // 동영상 SKIP 버튼 클릭 시
                        rewardInterstitialVideoAd.closeRewardVideoAd();
                        btnRewardVideoShow.setText("리워드 전면 비디오 광고요청");
                        break;
                    case COMPLETION: // 동영상 재생 완료 시
                    case CLICK: // 더보기 버튼 클릭 시
                        break;
                }
            }


        });

        // 광고 요청 후 광고를 받은 뒤, 원하는 시점에 노출
        btnRewardVideoShow.setOnClickListener(v -> {
            if (rewardInterstitialVideoAd.hasInterstitial)
                rewardInterstitialVideoAd.showRewardVideoAd(); // 광고를 노출한다.
            else {

                rewardInterstitialVideoAd.loadRewardVideoAd(); // 광고를 미리 로드한다.
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    // 생명주기에 따라 아래 설정이 반드시 필요합니다.
    @Override
    protected void onDestroy() {
        if (rewardInterstitialVideoAd != null) {
            rewardInterstitialVideoAd.stopRewardVideoAd();
            rewardInterstitialVideoAd = null;
        }
        super.onDestroy();
    }
}
