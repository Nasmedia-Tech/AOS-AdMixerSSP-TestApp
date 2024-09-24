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
import com.nasmedia.admixerssp.ads.InterstitialVideoAd;

public class InterstitialVideoActivity extends AppCompatActivity {

    private Button btnInterstitialVideoShow;
    private ProgressBar progressBar;
    private InterstitialVideoAd interstitialVideoAdView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitialvideo);

        progressBar = findViewById(R.id.loading_bar);
        btnInterstitialVideoShow = findViewById(R.id.btn_interstitial_video_show);

        AdInfo adInfo = new AdInfo.Builder(Application.ADUNIT_ID_INTERSTITIAL_VIDEO) // AdMixer 플랫폼에서 발급받은 전면 비디오 ADUNIT_ID
                .interstitialTimeout(0) // 초단위로 전면 광고 타임아웃 설정 (기본값 : 0, 0 이면 서버지정 시간으로 처리, 서버지정 시간 : 20s)
                .maxRetryCountInSlot(-1) // 리로드 시간 내에 반복 횟수(-1 : 무한, 0 : 반복 없음, n : n번 반복)
                .setIsUseMediation(true) // 미디에이션 사용 여부 (true - 기본값, false - 미사용)
                .build();

        interstitialVideoAdView = new InterstitialVideoAd(this);
        // 이 때 설정하신 Interstitial Video 의 부모 activity 는 원활한 광고 제공을 위해 hardwareAccelerated 가 true 설정되오니 참고 부탁드립니다.
        interstitialVideoAdView.setAdInfo(adInfo);
        interstitialVideoAdView.setListener(new AdListener() {
            @Override
            public void onReceivedAd(String s, Object o) {
                // 광고 수신 성공
                Toast.makeText(InterstitialVideoActivity.this, "onReceivedAd", Toast.LENGTH_SHORT).show();
                btnInterstitialVideoShow.setText("전면 비디오 광고보기");
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailedToReceiveAd(Object o, String s, int i, String s1) {
                // 광고 수신 실패
                Toast.makeText(InterstitialVideoActivity.this, s, Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onEventAd(Object o, AdEvent adEvent) {
                // 기타 이벤트
                switch (adEvent) {
                    case CLOSE:   // 광고 창이 닫혔을 때
                    case SKIPPED: // 동영상 SKIP 버튼 클릭 시
                        interstitialVideoAdView.closeInterstitialVideoAd();
                        btnInterstitialVideoShow.setText("전면 비디오 광고요청");
                        break;
                    case COMPLETION: // 동영상 재생 완료 시
                    case CLICK: // 더보기 버튼 클릭 시
                        break;
                }
            }

        });

        // 광고 요청 후 광고를 받은 뒤, 원하는 시점에 노출
        btnInterstitialVideoShow.setOnClickListener(v -> {
            if (interstitialVideoAdView.hasInterstitial)
                interstitialVideoAdView.showInterstitialVideoAd(); // 광고를 노출한다.
            else {

                interstitialVideoAdView.loadInterstitialVideoAd(); // 광고를 미리 로드한다.
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    // 생명주기에 따라 아래 설정이 반드시 필요합니다.
    @Override
    protected void onDestroy() {
        if (interstitialVideoAdView != null) {
            interstitialVideoAdView.stopInterstitialVideoAd();
            interstitialVideoAdView = null;
        }
        super.onDestroy();
    }
}
