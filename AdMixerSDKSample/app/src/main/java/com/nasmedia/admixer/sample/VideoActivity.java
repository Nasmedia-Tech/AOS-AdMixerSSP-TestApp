package com.nasmedia.admixer.sample;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nasmedia.admixerssp.ads.AdEvent;
import com.nasmedia.admixerssp.ads.AdInfo;
import com.nasmedia.admixerssp.ads.AdListener;
import com.nasmedia.admixerssp.ads.VideoAdView;

public class VideoActivity extends AppCompatActivity {

    private VideoAdView videoAdView;
    private RelativeLayout container;
    private TextView tvComplete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        container = findViewById(R.id.container_video);
        tvComplete = findViewById(R.id.tv_complete);

        AdInfo adInfo = new AdInfo.Builder(Application.ADUNIT_ID_VIDEO) // AdMixer 플랫폼에서 발급받은 배너 ADUNIT_ID
                .build();

        videoAdView = new VideoAdView(this);
        // 이 때 설정하신 video 의 부모 activity 는 원활한 광고 제공을 위해 hardwareAccelerated 가 true 설정되오니 참고 부탁드립니다.
        videoAdView.setAdInfo(adInfo);
        videoAdView.setAdViewListener(new AdListener() {
            @Override
            public void onReceivedAd(String s, Object o) {
                // 광고 수신 성공
                Toast.makeText(VideoActivity.this, "onReceivedAd", Toast.LENGTH_SHORT).show();
                // 광고를 노출한다.
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
                container.removeView(videoAdView);
                container.addView(videoAdView, params); // 레이아웃에 Video 광고 뷰를 추가 [ 광고 노출 ]
            }

            @Override
            public void onFailedToReceiveAd(Object o, String s, int i, String s1) {
                // 광고 수신 실패
                Toast.makeText(VideoActivity.this, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onEventAd(Object o, AdEvent adEvent) {
                // 기타 이벤트
                Toast.makeText(VideoActivity.this, adEvent.toString(), Toast.LENGTH_SHORT).show();
                switch (adEvent) {
                    case COMPLETION: // 동영상 재생 완료 시
                    case SKIPPED:    // 동영상 SKIP 버튼 클릭 시
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(videoAdView.getWidth(), videoAdView.getHeight());
                        params.addRule(RelativeLayout.CENTER_IN_PARENT);
                        tvComplete.setLayoutParams(params);
                        tvComplete.setVisibility(View.VISIBLE);
                        break;
                    case CLICK: // 더보기 버튼 클릭 시
                        break;
                }
            }

        });

        // 광고 요청 후 광고를 받은 뒤, 원하는 시점에 노출
        videoAdView.loadAd(); // 광고를 미리 로드한다.
    }

    // 생명주기에 따라 아래 설정이 반드시 필요합니다.
    @Override
    protected void onResume() {
        if (videoAdView != null) {
            videoAdView.onResume();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (videoAdView != null) {
            videoAdView.onPause();
        }

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (videoAdView != null) {
            videoAdView.onDestroy();
            videoAdView = null;
        }

        super.onDestroy();
    }
}
